# Etapa de build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /build

# 1. Copia e instala a lib technova-common
COPY services/technova-common /tmp/technova-common
RUN cd /tmp/technova-common && mvn clean install -DskipTests

# 2. Prepara o diretório da app principal
WORKDIR /app

# Copia pom.xml e src da aplicação principal (por exemplo: api-gateway)
COPY services/technova-api-gateway/pom.xml .
COPY services/technova-api-gateway/src ./src

# 3. Vai buscar as dependências offline (usa cache de dependência)
RUN mvn dependency:go-offline

# 4. Compila o projeto
RUN mvn clean package -DskipTests

# Etapa final de execução
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]