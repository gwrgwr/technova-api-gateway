# Estágio 1: Build com Maven
FROM maven:3.8.6-openjdk-11 AS build
WORKDIR /app

# Copie apenas o POM primeiro (para aproveitar o cache de dependências)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie o restante do código e construa o projeto
COPY src ./src
RUN mvn package -DskipTests

# Estágio 2: Imagem final leve
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/technova-api-gateway.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]