# Distributed Processing Demo

## [Work in progress]

![Python](https://img.shields.io/badge/Python-3.8%2B-blue.svg)
![Java](https://img.shields.io/badge/Java-20-green.svg)
![Kafka](https://img.shields.io/badge/Confluent--Kafka-7.3.2%2B-red.svg)

This project implements a

## Table of Contents

- [Introduction](#introduction)
- [Design](#)
- [Prerequisites](#prerequisites)
- [Run](#run)

## Introduction

## Prerequisites

- [Python 3.8+](https://www.python.org/downloads/release/python-380/)
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Docker Compose](https://docs.docker.com/compose/)

## Run

With all services up, access:

| Description             | Port/Link                  | Additional Info                          |
|-------------------------|----------------------------|------------------------------------------|
| Postgres                | 5432                       |                                          |
| Postgres UI             | http://localhost:5050      | U: `pgadmin4@pgadmin.org`<br/>P: `admin` |
| Kafka UI                | http://localhost:8080      |                                          |
| Redis UI                | http://localhost:8050      |                                          |
| Zipkin UI               | http://localhost:9411      |                                          |
| Eureka Service Registry | http://localhost:9900      |                                          |
| Spring Boot Admin       | http://localhost:9900      |                                          |
| Auth Service            | http://localhost:9901/docs |                                          |
| Ticker Service          | http://localhost:9902/docs |                                          |
| Order Service           | http://localhost:9903/docs |                                          |

References:

https://spring.io/guides/tutorials/metrics-and-tracing/
https://stackoverflow.com/questions/76418005/not-able-to-trace-database-requests-with-spring-boot-3-and-micrometer
https://github.com/micrometer-metrics/micrometer-samples/blob/main/micrometer-samples-boot3-database/src/main/java/io/micrometer/boot3/samples/db/SampleController.java
https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html