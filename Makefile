traffic:
	while true; do \
		http POST http://localhost:9903/api/trade/order \
          accept:*/\* \
          access-token:c96cf9ae-a0ce-40ba-91b8-8a636a38a87d \
          Content-Type:application/json \
          instrument=AAPL \
          quantity:=20 \
          userId=1 \
          type=BUY; \
		sleep 0.5; \
		http GET http://localhost:9902/api/instrument/stocks \
          accept:application/json \
          access-token:c96cf9ae-a0ce-40ba-91b8-8a636a38a87d; \
		sleep 0.5; \
		http GET http://localhost:9903/api/trade/portfolio/1 \
		  accept:application/json \
		  access-token:c96cf9ae-a0ce-40ba-91b8-8a636a38a87d; \
	done \

start-docker:
	docker-compose -f ./deployment/compose/docker-compose.yaml up --remove-orphans;

stop-docker:
	docker-compose -f ./deployment/compose/docker-compose.yaml down -v; \
    docker-compose -f ./deployment/compose/docker-compose.yaml rm -fsv;

start-apps:
	mvn -f modules/spring-admin/pom.xml spring-boot:run & \
	mvn -f modules/service-registry/pom.xml spring-boot:run & \
	sleep 20; \
	mvn -f modules/auth-service/pom.xml spring-boot:run & \
	mvn -f modules/order-service/pom.xml spring-boot:run & \
	mvn -f modules/ticker-service/pom.xml spring-boot:run & \
	mvn -f modules/producer-service/pom.xml spring-boot:run & \
	mvn -f modules/consumer/pom.xml spring-boot:run &

stop-apps:
	jps | grep ConsumerCLIRunner | cut -d' ' -f1 | xargs kill -9; \
	jps | grep ProducerServiceApplication | cut -d' ' -f1 | xargs kill -9; \
	jps | grep TickerServiceApplication | cut -d' ' -f1 | xargs kill -9; \
	jps | grep OrderServiceApplication | cut -d' ' -f1 | xargs kill -9; \
	jps | grep EurekaServerApplication | cut -d' ' -f1 | xargs kill -9; \
	jps | grep SpringAdminApplication | cut -d' ' -f1 | xargs kill -9; \
	jps | grep AuthServiceApplication | cut -d' ' -f1 | xargs kill -9;