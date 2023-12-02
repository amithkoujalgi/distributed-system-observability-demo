# Demo of Distributed Processing with Observability

## [Work in progress]

![Python](https://img.shields.io/badge/Python-3.8%2B-blue.svg)
![Java](https://img.shields.io/badge/Java-20-green.svg)
![Kafka](https://img.shields.io/badge/Confluent--Kafka-7.3.2%2B-red.svg)

## Table of Contents

- [Introduction](#introduction)
- [Design](#)
- [Prerequisites](#prerequisites)
- [Run](#run)
- [References](#references)

## Introduction

## Prerequisites

- [Python 3.8+](https://www.python.org/downloads/release/python-380/)
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Docker Compose](https://docs.docker.com/compose/)
- [httpie](https://httpie.io/)

## Build

```shell
mvn clean install
```

## Run

Start docker containers:
```shell
docker-compose -f deployment/compose/docker-compose.yaml up --remove-orphans
```

Start Java apps:
```shell
mvn -f modules/spring-admin/pom.xml spring-boot:run
mvn -f modules/service-registry/pom.xml spring-boot:run
mvn -f modules/auth-service/pom.xml spring-boot:run
mvn -f modules/order-service/pom.xml spring-boot:run
mvn -f modules/ticker-service/pom.xml spring-boot:run
mvn -f modules/producer/pom.xml spring-boot:run
mvn -f modules/consumer/pom.xml spring-boot:run
```

With all services up, access:

| Description             | Port/Link                  | Additional Info                          |
|-------------------------|----------------------------|------------------------------------------|
| Postgres                | 5432                       |                                          |
| Postgres UI             | http://localhost:5050      | U: `pgadmin4@pgadmin.org`<br/>P: `admin` |
| Kafka UI                | http://localhost:8080      |                                          |
| Redis UI                | http://localhost:8050      |                                          |
| Grafana UI              | http://localhost:3000      |                                          |
| Eureka Service Registry | http://localhost:9900      |                                          |
| Spring Boot Admin       | http://localhost:9800      |                                          |
| Auth Service            | http://localhost:9901/docs |                                          |
| Ticker Service          | http://localhost:9902/docs |                                          |
| Order Service           | http://localhost:9903/docs |                                          |

Generate traffic:

```shell
make traffic
```

### Images

Spring Admin:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/spring-admin.png"/>

Service Registry:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/eureka.png"/>

Service Registry:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/eureka.png"/>

Service Details via Service Registry:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/spring-admin-service.png"/>

Service Logs via Service Registry:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/spring-admin-service-logs.png"/>

Central Dashboard - Service Logs:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/logs-dash.png"/>

Central Dashboard - Stats of HTTP Requests:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/http-dash.png"/>

Sample Exemplar:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/exemplar.png"/>

Sample Trace:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/trace.png"/>

Service Graph for a Trace:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/trace-node-graph.png"/>

Full Service Graph:
<img src="https://raw.githubusercontent.com/amithkoujalgi/distributed-processing-demo/main/images/services-graph.png"/>


### Improvements:

- [ ] Filter by URLs of a selected service/application on `HTTP Stats` Grafana Dashboard
- [ ] Instrumentation of Redis APIs
- [ ] Instrumentation of consumer

### References:

- https://www.youtube.com/watch?v=fh3VbrPvAjg&ab_channel=SpringI%2FO
- https://spring.io/guides/tutorials/metrics-and-tracing/
- https://stackoverflow.com/questions/76418005/not-able-to-trace-database-requests-with-spring-boot-3-and-micrometer
- https://github.com/micrometer-metrics/micrometer-samples/blob/main/micrometer-samples-boot3-database/src/main/java/io/micrometer/boot3/samples/db/SampleController.java
- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html