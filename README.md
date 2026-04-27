# BjjTrack - MicroSaaS para Gestão de Academias de Jiu-Jitsu

O **BjjTrack** é uma API REST robusta desenvolvida para simplificar a administração de academias de artes marciais. O sistema permite o gerenciamento de usuários, o controle de presença de alunos e o acompanhamento detalhado da progressão de faixas e graus (stripes).

## 🚀 Tecnologias Utilizadas

O projeto utiliza tecnologias modernas do ecossistema Java para garantir escalabilidade e manutenibilidade:

* **Java 17**: Linguagem base do projeto.
* **Spring Boot 4.0.1**: Framework principal para construção da aplicação.
* **Spring Data JPA**: Para abstração da camada de persistência.
* **PostgreSQL**: Banco de dados relacional para armazenamento de dados.
* **Flyway**: Gerenciamento de migrações de banco de dados.
* **Spring Security & Crypto**: Utilizado para criptografia de senhas com BCrypt.
* **Lombok**: Para redução de código boilerplate.
* **Docker & Docker Compose**: Para containerização do banco de dados em ambientes de teste e produção.
* **Testcontainers**: Utilizado para testes de integração com instâncias reais de banco de dados.

## 🏗️ Arquitetura do Projeto

O sistema segue padrões de arquitetura em camadas e princípios **S.O.L.I.D.**, garantindo um código limpo e testável:

1.  **Controller**: Gerencia as requisições HTTP e as respostas da API.
2.  **Service**: Contém as regras de negócio complexas, como a lógica de promoção de faixas.
3.  **Repository**: Interface de comunicação com o banco de dados PostgreSQL via JPA.
4.  **Entity**: Representação das tabelas do banco de dados (`Academy`, `Student`, `User`, `Attendance`).
5.  **DTO (Data Transfer Objects)**: Camada de transporte de dados para evitar a exposição direta das entidades.
6.  **Mapper**: Responsável pela conversão entre Entidades e DTOs.

## 📋 Funcionalidades Principais

### Gestão de Academias e Cadastro (Signup)
* Cadastro simultâneo de uma nova academia e do seu administrador principal.
* Suporte a **slugs únicos** para identificação de academias na URL.

### Controle de Alunos e Graduação
* Registro de novos alunos vinculados a usuários existentes.
* **Gestão de Graus (Stripes)**: Incrementa graus até o limite de 4 por faixa.
* **Promoção de Faixa**: Permite a mudança de cor da faixa (Branca, Azul, Roxa, Marrom, Preta, etc.) com validação de requisitos mínimos.

### Gestão de Presenças
* Registro de presença diário para cada aluno.
* Prevenção de duplicados: o aluno não pode marcar duas presenças no mesmo dia.

## 🛣️ Principais Endpoints

### Alunos (`/students`)
* `POST /students`: Cria um novo aluno.
* `PATCH /students/{id}/stripe`: Adiciona um grau ao aluno.
* `PATCH /students/{id}/promote-belt`: Promove o aluno para a próxima faixa.

### Presenças (`/attendances`)
* `POST /attendances`: Registra uma nova presença.
* `DELETE /attendances/{id}`: Remove um registro de presença.

### Academias (`/academy`)
* `GET /academy/slug/{slug}`: Busca detalhes de uma academia pelo slug.
* `GET /academy/{academyId}/users`: Lista todos os usuários vinculados a uma academia.

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Docker e Docker Compose instalados.
* JDK 17 ou superior.

### Passo a Passo
1.  **Subir o banco de dados**:
    ```bash
    docker-compose up -d
    ```

2.  **Configurar variáveis**: Verifique o arquivo `src/main/resources/application-test.yaml` ou `application-prod.yaml` para as credenciais do banco de dados.

3.  **Executar a aplicação**:
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **Executar Testes**:
    ```bash
    ./mvnw test
    ```

---
*Projeto desenvolvido para otimização da gestão técnica e administrativa de equipes de Jiu-Jitsu.*