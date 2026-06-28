# =============================================================
# TUS-2381 - Deliver academic system with Docker
# Multi-stage build: compila com Maven e executa com Java puro
# =============================================================

# ---- Stage 1: Build ----
FROM maven:3.9-eclipse-temurin-23 AS builder

WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia o restante do código-fonte e compila
COPY src ./src
RUN mvn package -DskipTests -q

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copia apenas o JAR gerado no stage de build
COPY --from=builder /app/target/academic-system-*.jar app.jar

# Diretório para arquivos de dados persistidos pelo sistema
VOLUME ["/app/data"]

# Ponto de entrada: executa o sistema via CLI (Main.java)
ENTRYPOINT ["java", "-jar", "app.jar"]
