
build-api:
	@cd backend/api && mvn clean spring-boot:build-image -DskipTests

build-auth:
	@cd backend/auth && mvn clean spring-boot:build-image -DskipTests

start:
	@cd deployment && docker-compose up --build -d

stop:
	@cd deployment && docker-compose stop

clear:
	@cd deployment && docker-compose down

full-clear:
	@cd deployment && docker-compose down -v

logs:
	@cd deployment && docker-compose logs --tail 10 -f $s
