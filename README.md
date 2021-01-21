# Ecommerce Monolito

Aplicação que simula um back-end de um ecommerce. Projeto inicialmente desenvolvido para colocar em prática o aprendizado do Framework Spring Boot. Atualmente é usado como uma base de código onde coloco em prática diversos assuntos que estudo sobre desenvolvimento de software.

## Tecnologias

No projeto é usado as seguintes tecnologias:

- Java (JDK 11)
- Spring boot (Versão 2.1.7.RELEASE)
- Maven
- Docker
- Swagger
- Mysql (Versão 5.7)
- H2 (em ambiente local para teste)

## Como rodar aplicação localmente

Para rodar a aplicação localmente, pode-se usar o profile test ou dev. No profile de test, é usado o banco de dados em memória H2, nesse caso, é só rodar a aplicação normalmente na sua IDE.
Caso queira fazer um teste no profile de dev, que é mais próximo do ambiente de prod, é necessário levar o container com o Mysql. Para isso, o seguinte comando:

``` bash
docker-compose up -d
```

Após criar o container, é só rodar a aplicação normalmente na IDE.

## Documentação

A documentação a API dessa aplicação está no swagger em [http://localhost:8080](http://localhost:8080).

## Maintainer

| nome | linkedin |
|------|-------|
| Janaina Paula | www.linkedin.com/in/janainadepaula |
