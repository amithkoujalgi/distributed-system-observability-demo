// noinspection JSJQueryEfficiency,JSUnresolvedReference
function kcAuth() {
    const keycloak = new Keycloak({
        "auth-server-url": 'http://localhost:9000/auth',
        url: 'http://localhost:9000/',
        realm: 'myapp',
        clientId: 'myapp-client',
        "enable-cors": true,
        "public-client": true,
    });
    keycloak.init({onLoad: 'login-required', promiseType: 'native'}).then(authenticated => {
        console.log("Authenticated.");
        console.log(keycloak);
        window.keycloak = keycloak;
        setInterval(get_ticker_data, 1000);
        this.setState({keycloak: keycloak, authenticated: authenticated});
    });
}

function dateToTimeLabel(dateObj) {
    return `${dateObj.getMinutes()}:${String(dateObj.getSeconds()).padStart(2, '0')}`;
}


function generate_init_chart_data_points(initial_data_point) {
    let data = []
    let labels = [];
    let maxPoints = 20;

    let min = initial_data_point.lastTradedPrice - 500;
    let max = initial_data_point.lastTradedPrice + 500;

    let currentTime = new Date(Date.now());
    for (let i = maxPoints; i > 0; i--) {
        let dt = new Date(currentTime - (1000 * i));
        labels.push(dateToTimeLabel(dt));
    }
    for (let i = 0; i < maxPoints; i++) {
        data.push(Math.random() * (max - min) + min);
    }
    return [data, labels];
}

let index_charts = {}

function create_market_action_chart(chartLabel, chart_data_point) {
    let symbolCleaned = chartLabel.replace(/[^\w\s]/gi, '').replaceAll(' ', '');
    let chartId = `chart-${symbolCleaned}`
    if ($(`#${chartId}`).length === 0) {
        $('.market-action-container').append(`<div class="chart-container">
            <p>${chartLabel}</p>
            <canvas class="market-action-chart" id="${chartId}"></canvas>
        </div>`);
        const ctx = document.getElementById(chartId);
        let init_data = generate_init_chart_data_points(chart_data_point)
        let data_arr = init_data[0];
        let labels_arr = init_data[1];

        let chart = new Chart(ctx, {
            type: 'line', data: {
                labels: labels_arr, datasets: [{
                    label: 'LTP', data: data_arr, borderWidth: 1
                }]
            },
            options: {
                animation: {
                    duration: 0
                },
                plugins: {
                    legend: {
                        display: false
                    }
                }, scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    }, y: {
                        beginAtZero: true, grid: {
                            display: false
                        }
                    }
                }
            }
        });
        index_charts[chartId] = chart;
    } else {
        let next_data = chart_data_point.lastTradedPrice;
        let next_label = dateToTimeLabel(new Date(parseInt(chart_data_point.timestamp * 1000)));
        let chart = index_charts[chartId];
        chart.data.labels.push(next_label);
        chart.data.datasets.forEach((dataset) => {
            dataset.data.push(next_data);
        });
        chart.update();

        chart.data.labels.shift();
        chart.data.datasets[0].data.shift();
    }
}

function checkLogin(response) {
    if (response.status === 401) {
        console.log(response)
        // window.location.href = "/login"; // Adjust the login page URL
    } else if (response.ok) {
        return response.json();
    } else {
        throw new Error(`Request failed with status: ${response.status}`);
    }
}

function get_ticker_data() {
    let url = "/api/instrument/stocks";
    fetch(url, {
        "headers": {
            "Authorization": `Bearer ${window.keycloak.token}`,
            "accept": "*/*", "sec-fetch-site": "same-origin", "sec-fetch-dest": "empty", "sec-gpc": "1",
        },
        "body": null,
        "method": "GET",
        "mode": "cors",
        "credentials": "omit",
        "referrerPolicy": "strict-origin-when-cross-origin",
    }).then(response => {
        return checkLogin(response);
    }).then(json => {
        $('.ticker-items').html('');
        $('.stock-cards').html('');

        for (const [key, value] of Object.entries(json)) {
            let ticker_symbol = value.name
            let ticker_value = parseFloat(value.lastTradedPrice).toFixed(2);
            let ticker_trend = value.trend.toLowerCase();
            $('.ticker-items').append(`<li id="stock-${ticker_symbol}" class="nav-item menu-item"><a href="#" class="nav-link ticker-trend-${ticker_trend}">${ticker_symbol}: ${ticker_value}</a><li>`)
        }
    }).catch(err => {
        console.log('Request Failed')
        console.log(err)
    });
}

function get_indices_data() {
    let url = "/api/instrument/indices";
    fetch(url, {
        "headers": {
            "accept": "*/*", "sec-fetch-site": "same-origin", "sec-fetch-dest": "empty", "sec-gpc": "1",
        },
        "body": null,
        "method": "GET",
        "mode": "cors",
        "credentials": "omit",
        "referrerPolicy": "strict-origin-when-cross-origin",
    }).then(response => {
        let res = response.json();
        return res;
    })
        .then(json => {
            for (const [key, value] of Object.entries(json)) {
                create_market_action_chart(value.name, value);
            }
        })
        .catch(err => console.log('Request Failed', err));
}