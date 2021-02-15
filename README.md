# Desafio Backend Stoom

* [Teste de Qualificação Backend STOOM](https://gist.github.com/pedroits/9a42411f44ba9d75a70bfb7122c6f642)

## Tecnologias Utilizadas

- Java 11
- Spring Boot
- Lombok
- JUnit 5
- H2
- Docker
- Swagger

## Executar projeto local


1 - Clone o projeto em: ```git clone https://github.com/BSTK/desafio-backend-stoom.git```

2 - Vá para pasta do projeto clonado: ```cd ./desafio-backend-stoom/stoom-api``` 

3 - A) Execute com o java: 
  - ```mv clean install```
  - ```java -jar target/*.jar```

4 - A) Execute com Docker
  - ```mvn clean install```
  - ```mvn docker:build```
  - ```docker run -it -p 8080:8080 stoom-api```

5 - Link Swagger 
    [Stoom Api Swagger UI](http://localhost:8080/stoom/swagger-ui.html#/)
  

