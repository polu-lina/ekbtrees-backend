FROM maven:3.6.3-adoptopenjdk-11 as cache

WORKDIR /build

COPY pom.xml pom.xml

RUN mvn dependency:go-offline

FROM maven:3.6.3-adoptopenjdk-11

WORKDIR /app

COPY --from=cache /root/.m2 /root/.m2
COPY --from=cache /build/pom.xml pom.xml

COPY src ./src

CMD ["mvn", "test"]
