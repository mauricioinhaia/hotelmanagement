# Usar a imagem base do OpenJDK 21
FROM openjdk:21-jdk-slim

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo pom.xml e o script mvnw para o container
COPY pom.xml /app/
COPY mvnw /app/
COPY .mvn /app/.mvn

# Copiar o restante dos arquivos do projeto para o container
COPY src /app/src/

# Garantir que o mvnw tenha permissões de execução
RUN chmod +x ./mvnw

# Resolver as dependências do Maven
RUN ./mvnw dependency:resolve

# Comando para rodar a aplicação
CMD ["./mvnw", "spring-boot:run"]