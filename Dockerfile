# Etapa 1: build com Maven
FROM maven:3.9.6-openjdk-17 AS build

WORKDIR /app

# Copia arquivos do projeto
COPY pom.xml .
COPY src ./src

# Compila a aplicação
RUN mvn clean install -DskipTests

# Etapa 2: imagem final com apenas o JAR
FROM openjdk:17-slim

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
