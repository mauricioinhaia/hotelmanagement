<h1 align="center"> Hotel Management </h1>

# 💻 Sobre o Projeto
<p align="justify">
Refere-se a uma API para gerenciamento de hospedagens, permitindo o cadastro de hóspedes e o controle de suas reservas.
</p>

## 🚀 Tecnologias
<p align="justify">
Esse projeto foi desenvolvido com as seguintes tecnologias:
</p>

- Java 21
- Spring Boot para a construção de APIs RESTful
- PostgreSQL como banco de dados
- Spring Data JPA para a persistência de dados
- JUnit 5 e Mockito para testes unitários
- Docker para containerização da aplicação

## ⚠️ Diferenciais Implementados
<p align="justify">
Além dos requisitos solicitados, implementei alguns diferenciais para demonstrar minhas habilidades e minha atenção à qualidade do projeto:

- Testes Unitários: Incluí exemplos de testes para alguns serviços, demonstrando meu conhecimento em escrita de testes e validação de regras de negócio. A cobertura não está completa, pois o objetivo era apenas ilustrar o domínio dessa prática.
- Docker: Adicionei um Dockerfile e um docker-compose.yml para facilitar a execução do projeto em ambiente isolado e padronizado.
</p>

## 📝 Padrões de Arquitetura

- DDD (Domain-Driven Design): A modelagem de entidades como HospedeDomain, ReservaDomain segue os princípios de DDD para separar claramente as responsabilidades e promover um design de código limpo e coeso.
- SOLID: Aplicação dos princípios SOLID para garantir um código modular, escalável e de fácil manutenção.
- MVC (Model-View-Controller): A implementação utiliza o padrão MVC para separar as camadas de apresentação, lógica de negócios e dados.

## 🖧 EndPoints
Podem ser importados no Postman através do arquivo HotelManagement.postman_collection.json disponibilizado nesse projeto.

### Hospedes
#### GET Listar hóspedes
```
http://localhost:8080/hospedes?page=1
```
#### GET Buscar por Id
```
http://localhost:8080/hospedes/1
```
#### GET Buscar hóspedes por nome, documento, email ou telefone
```
http://localhost:8080/hospedes/buscar?documento=65498621315&nome=mauricio&email=teste.inhaia1@email.com&telefone=(49) 91234-5678
```
#### POST Criar hóspede
```
http://localhost:8080/hospedes

body:
{
  "nome": "mauricio inhaia",
  "documento": "65498621315",
  "email": "teste.inhaia1@email.com",
  "telefone": "(49) 91234-5678"
}
```
#### PATCH Atualizar hóspede
```
http://localhost:8080/hospedes

body:
{
  "nome": "Teste Update",
  "email": "mauricio.inhaia112@email.com",
  "telefone": "(49) 91234-5678"
}
```
#### DELETE Deletar hóspede por Id
```
http://localhost:8080/hospedes/1
```

### Reservas
#### GET Buscar por Id
```
http://localhost:8080/reservas/1
```
#### GET Listar reservas em aberto com hóspedes
```
http://localhost:8080/reservas/reservas-em-aberto-com-hospedes?page=1
```
#### GET Listar reservas finalizadas com hóspedes
```
http://localhost:8080/reservas/reservas-finalizadas-com-hospedes?page=0
```
#### POST Criar reserva
```
http://localhost:8080/reservas

body:
{
  "hospede": {
    "id": 1,
    "nome": "João Silva",
    "documento": "12345678901",
    "email": "joao.silva@email.com",
    "telefone": "(47) 99999-1111"
  },
  "checkIn": "2025-01-26T23:00:00",
  "checkOut": "2025-01-27T14:00:00",
  "estacionamento": true
}
```
#### PATCH Atualizar reserva
```
http://localhost:8080/reservas

body:
{
    "hospede": {
        "id": 1,
        "nome": "João Silva",
        "documento": "12345678901",
        "email": "joao.silva@email.com",
        "telefone": "(47) 99999-1111"
    },
    "checkIn": "2025-01-23T14:00:00",
    "checkOut": "2025-01-27T11:00:00",
    "estacionamento": false,
    "status": "A",
    "valorTotal": 1000
}
```
#### DELETE Deletar por Id
```
http://localhost:8080/reservas/1
```

## ▶️ Como Executar no Container
<p align="justify">
Para executar o projeto utilizando o Docker (sem necessidade de configurar o ambiente), siga os passos abaixo:
</p>

#### Requisitos:
- Docker
- Git
#### Processo:
- Pelo terminal acesse uma pasta de sua preferência:
```
cd /home/SEUNOME/Documents/git
```
- Clone o projeto:
```
git clone https://github.com/mauricioinhaia/hotelmanagement.git
```
- Acesse a pasta do projeto:
```
cd hotelmanagement
```
- Execute o comando do docker:
```
docker-compose up
```
- Agora basta utilizar os endpoints no Postman.

## ▶️ Como Executar no Ambiente Local
<p align="justify">
Para executar o projeto utilizando em seu ambiente local, é necessário configurar manualmente.
</p>

#### Requisitos:
- Java 21 (Pode executar no terminal java -version para ver a versão e alterar se necessário)
- PostgreSQL (Criar um DB hotel_management e configurar conforme application.yml)
- Git
#### Processo:
- Pelo terminal acesse uma pasta de sua preferência:
```
cd /home/SEUNOME/Documents/git
```
- Clone o projeto:
```
git clone https://github.com/mauricioinhaia/hotelmanagement.git
```
- Acesse a pasta do projeto:
```
cd hotelmanagement
```
- Execute o comando:
```
./mvnw spring-boot:run
```
- Agora basta utilizar os endpoints no Postman.