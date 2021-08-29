
build-api:
	@cd backend/api && ./mvnw clean spring-boot:build-image -DskipTests

build-auth:
	@cd backend/auth && ./mvnw clean spring-boot:build-image -DskipTests

build-rest-tests:
	@cd backend/rest-tests && docker build -t docker.io/donkeyhott/ekbtrees-rest-tests:latest .

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
