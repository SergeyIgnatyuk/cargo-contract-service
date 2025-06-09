FROM openjdk:17-slim as builder
RUN apt-get update && apt-get install -y --no-install-recommends wget ca-certificates && rm -rf /var/lib/apt/lists/*
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz -P /tmp && tar -xzf /tmp/apache-maven-3.9.6-bin.tar.gz -C /opt  \
    && ln -s /opt/apache-maven-3.9.6/bin/mvn /usr/bin/mvn
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar cargo_contract_service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "cargo_contract_service.jar"]