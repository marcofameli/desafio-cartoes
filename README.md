# 💳 API de Desafio Cartões

## 📝 Introdução

Esta API REST desenvolvida em **Kotlin** com **Spring Boot** processa solicitações de concessão de cartões de crédito, avaliando a elegibilidade dos clientes com base em critérios como renda, idade e localização.

## 🚀 Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **Framework:** Spring Boot
- **Banco de Dados:** H2 (em memória)
- **Gerenciamento de Dependências:** Maven
- **Containerização:** Docker

## 🔧 Pré-requisitos

- Docker
- Git
- Maven

## 📦 Instalação

### Clonar o Repositório

```sh
git clone https://github.com/marcofameli/desafio-cartoes
cd desafio-cartoes
```

### Construir e Rodar com Docker

```sh
# Construir a imagem
docker build -t tecnico-app .

# Executar o container
docker run -p 8080:8080 --name aplicacao_cartoes tecnico-app
```

## 🌐 Endpoints da API

### Criar Solicitação de Cartão de Crédito

- **Método:** POST
- **Endpoint:** `/cartoes`

#### Exemplo de Requisição

```json
{
  "cliente": {
    "nome": "Cliente Teste",
    "cpf": "123.456.789-10",
    "idade": 25,
    "data_nascimento": "2000-01-01",
    "uf": "SP",
    "renda_mensal": 4000.00,
    "email": "cliente@teste.com",
    "telefone_whatsapp": "11999992020"
  }
}
```

#### Possíveis Respostas

- **200 OK:** Cartões aprovados para o cliente
- **204 No Content:** Nenhum cartão disponível
- **400 Bad Request:** Dados inválidos
- **422 Unprocessable Entity:** Cliente não atende aos critérios de elegibilidade

## 💾 Banco de Dados

A aplicação utiliza o banco de dados H2 em memória. Acesse o console em:

- **URL:** `http://localhost:8080/api/h2-console/login.jsp`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuário:** `sa`
- **Senha:** *(vazio)*

## 🧪 Testes

Execute os testes unitários com Maven:

```sh
mvn test
```

## 📍 Documentação da API

Swagger UI para testar a aplicação disponível em:
`http://localhost:8080/api/swagger-ui/index.html#/`


## 🚀 Considerações Finais

Projeto desenvolvido seguindo boas práticas de desenvolvimento, princípios SOLID.
