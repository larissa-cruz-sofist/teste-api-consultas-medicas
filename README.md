# Projeto de Testes Integrados - API Consultas Médicas com Banco de Dados H2
O presente projeto visa testar a API (https://github.com/larissa-cruz-sofist/api-spring-boot-consultas-medicas). Este projeto foi criado de forma autônoma à API Consultas Médicas desenvolvida por mim em treinamento utlizando a linguagem Java e Rest Assured.

Neste projeto são realizados testes de integração com banco de dados em memória, simulando cadastro e alteração de médicos e pacientes, bem como, agendamento de consultas e exclusão lógica de médicos, pacientes e consultas.

## Getting Started
1. Editar as variáveis de ambiente indicando o Java JDK 17
2. Clonar o projeto em sua máquina
3. Importar a pasta do projeto no Visual Studio Code
4. Dar Start na API conforme indicado neste README: https://github.com/larissa-cruz-sofist/api-spring-boot-consultas-medicas

### Project Structure
* src/main: Arquivos Models e Utils do projeto.
* src/test: Arquivos de Testes.
* src/test/resources: Arquivos de configuração.

#### Contributing
* Console do banco de dados H2: http://www.localhost:8080/h2-console
* Swagger: http://www.localhost:8080/swagger-ui/index.html
* Para gerar o relatório de testes allure report é necessário rodar os seguintes comandos dentro da pasta do projeto no terminal: **npm install -g allure-commandline** após, **allure serve allure-results**
