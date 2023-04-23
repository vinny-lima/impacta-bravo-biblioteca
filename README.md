## Sobre
O projeto consiste em um sistema simples para catalogação de livros, onde parte das opções para realizar os cadastros, tais como Autores, Editoras e Gêneros Literários, são gerenciados pelo próprio usuário.

## Tecnologias
### Front-End
* jQuery
* jQuery UI
* jQuery Validation
* jQuery Mask
* Bootstrap
* Font Awesome
* Select2
* DataTables

### Back-End
* Java 17
* Maven
* Spring Boot
* Spring Data JPA
* Spring Doc
* Hibernate ORM
* Hibernate Validation

### Banco de Dados
* MySQL

### Infra
* Docker

<a target="_blank" href="https://github.com/vinny-lima/impacta-bravo-biblioteca/wiki">Mais detalhes</a>

## Requisitos
* Docker Compose
* Java 17

## Executando
É importante que o docker sempre seja iniciado antes da aplicação java.

```bash
# Clone o projeto
$ git clone https://github.com/vinny-lima/impacta-bravo-biblioteca.git
# Acesse o diretório baixado
$ cd impacta-bravo-biblioteca
# Execute o docker-compose
$ docker compose up -d
# Execute a aplicação back-end java
$ java -jar build-back-end/impacta-bravo-biblioteca-0.0.1-SNAPSHOT.jar
# O servidor principal iniciará em <http://localhost>
# A documentação da API será exposta em <http://localhost:8080/swagger-ui/index.html>
```
