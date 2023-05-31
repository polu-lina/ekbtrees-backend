build-backend:
	@./mvnw -T 1C clean install -pl backend/core,backend/auth -am -DskipTests && \
	 ./mvnw -T 1C clean spring-boot:build-image -pl backend/api -DskipTests

build-rest-tests:
	@docker build -f backend/rest-tests/Dockerfile -t docker.io/donkeyhott/ekbtrees-rest-tests:latest backend/rest-tests/

start:
	@cd deployment && docker-compose up --build -d

stop:
	@cd deployment && docker-compose stop

clear:
	@cd deployment && docker-compose down

full-clear:
	@cd deployment && docker-compose down -v --remove-orphans

logs:
	@cd deployment && docker-compose logs --tail 10 -f $s

rest-tests:
	@cd deployment && docker-compose -f docker-compose-tests.yml up --build --abort-on-container-exit
