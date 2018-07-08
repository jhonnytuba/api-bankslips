[![Build Status](https://travis-ci.org/jhonnytuba/api-bankslips.svg?branch=master)](https://travis-ci.org/jhonnytuba/api-bankslips)

# api-bankslips

## Desafio
 O objetivo do desafio é construir uma API REST para geração de boletos que será consumido por
 um módulo de um sistema de gestão financeira de microempresas.
 No final do desafio vamos ter os seguintes endpoints para:
 
    * Criar boleto
    * Listar boletos
    * Ver detalhes
    * Pagar um boleto
    * Cancelar um boleto
 
## Tecnologias
    * Spring boot
    * H2
    * ModelMapper
    * Junit
    * Swagger
    
## Como rodar?
    mvn spring-boot:run
    
    * Para ver a documentação da Api gerado pelo swagger assim que iniciar o serviço, acesse:
        http://localhost:8080/swagger-ui.html