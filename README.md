# Demo of Distributed System with Observability

## [Work in progress]

This project implements a simplified stock market simulation. The simulation involves
generating random stock price data and simulating trader's actions based on the received stock prices.

![Java](https://img.shields.io/badge/Java-17-green.svg)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-green.svg)
![Postgres](https://img.shields.io/badge/Postgres-blue.svg)
![Kafka](https://img.shields.io/badge/Confluent--Kafka-7.3.2%2B-red.svg)
![Redis](https://img.shields.io/badge/Redis-6.0.20%2B-blue.svg)

## Table of Contents

- [Introduction](#introduction)
- [Design](#)
- [Prerequisites](#prerequisites)
- [Run](#run)
- [References](#references)

## Introduction

The goal of this project is to demonstrate a basic stock market simulation using distributed systems and a message broker for
communication between the components of the system. The simulation consists of producers generating simulated stock price
data, and consumers (traders) making buying and selling decisions based on their trading strategies.

_Please note that this simulation is a simplified version and might not represent real-world trading conditions or all
aspects of a complex stock market._

## Prerequisites

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
make start-docker
```

Start Java apps:

```shell
make start-apps
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

Stop apps:

```shell
make stop-apps
```

Stop docker containers:

```shell
make stop-docker
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

Central Dashboard - Stats of HTTP Requests (Latency/Throughput):
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