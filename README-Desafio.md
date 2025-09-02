# README-Desafio

## Sumário
- [Visão Geral](#visão-geral)
- [Decisões de Arquitetura](#decisões-de-arquitetura)
- [Diferenciais Implementados](#diferenciais-implementados)
- [Validação e Tratamento de Erros](#validação-e-tratamento-de-erros)
- [Testes e Cobertura](#testes-e-cobertura)
- [Documentação Swagger](#documentação-swagger)
- [Logs Estruturados](#logs-estruturados)
- [Docker e Containerização](#docker-e-containerização)
- [Como rodar com Docker](#como-rodar-com-docker)
- [Referências](#referências)

---

## Visão Geral
Este documento detalha as decisões técnicas e diferenciais implementados no desafio Java Backend, focando em boas práticas, arquitetura, testes, documentação e facilidade de uso.

## Decisões de Arquitetura
  - `controller` (entrada/rest)
  - `service` (regra de negócio)
  - `repository` (persistência)
  - `domain` (entidades e enums)
  - `web` (tratamento de exceções)
  - `exception` (exceções customizadas)

  - Os IDs de Projeto e Tarefa são gerados de forma sequencial automática pelo banco (H2/JPA), facilitando integração e testes.

## Diferenciais Implementados
- **Swagger/OpenAPI**: Documentação automática dos endpoints via springdoc-openapi.
 - **Logs estruturados**: Implementado como exemplo nos serviços principais (`ProjetoService`, `TarefaService`) usando SLF4J (`LoggerFactory`) para registrar operações importantes (ex: criação de projeto/tarefa). Pode ser expandido para outros pontos conforme necessidade.
- **Dockerfile**: Permite rodar a aplicação facilmente em container.
- **Cobertura de testes**: Testes unitários e de integração com JUnit/Mockito, cobertura >= 70% (Jacoco).

## Validação e Tratamento de Erros
- Bean Validation nas entidades (campos obrigatórios, limites de tamanho).
- Exceções customizadas e handler global (`RestExceptionHandler`) para respostas HTTP claras e padronizadas.

## Testes e Cobertura
- Testes unitários para regras de negócio (services).
- Testes de integração para endpoints REST (controllers).
- Jacoco configurado para medir cobertura.

## Documentação Swagger
- Endpoints documentados automaticamente.
- Acesse: `http://localhost:8080/swagger-ui.html`

## Logs Estruturados
- SLF4J utilizado nos serviços principais:
  - Exemplo: `logger.info("Criando projeto: {}", projeto.getNome());`
- Permite rastrear operações relevantes no backend.

## Docker e Containerização
 - Dockerfile incluso na raiz do projeto.
 - Utiliza a imagem base `openjdk:17-jdk-slim`, recomendada para compatibilidade em ambientes Mac/Linux. (A versão `alpine` pode apresentar problemas de compatibilidade.)
 - Permite build e execução em qualquer ambiente com Docker instalado.

**Importante:**
O Dockerfile foi ajustado para copiar o JAR gerado pelo Spring Boot (`java-senior-test-0.0.1-SNAPSHOT.jar`). Certifique-se de rodar `mvn clean package spring-boot:repackage` antes do build do Docker.

## Como rodar com Docker
1. Build da imagem:
   ```bash
   docker build -t bip-desafio-java .
   ```
2. Rodar o container:
   ```bash
   docker run -p 8080:8080 bip-desafio-java
   ```
3. Acesse a API em `http://localhost:8080`.

### Exemplos de requisições

**Criar Projeto**
```http
POST /api/projects
Content-Type: application/json

{
  "nome": "Projeto Exemplo",
  "descricao": "Descrição do projeto"
}
```

**Listar Projetos**
```http
GET /api/projects
```

**Criar Tarefa (vinculada a um projeto)**
```http
POST /api/tasks/project/1
Content-Type: application/json

{
  "titulo": "Tarefa Exemplo",
  "descricao": "Descrição da tarefa",
  "status": "PENDENTE",
  "prioridade": "MEDIA"
}
```

**Listar Tarefas de um Projeto**
```http
GET /api/tasks/project/1
```

**Filtrar Tarefas por Status, Prioridade e Projeto**
```http
GET /api/tasks?status=CONCLUIDA&prioridade=ALTA&projetoId=1
```

**Observação:**
O Dockerfile foi testado e funciona corretamente com a imagem `openjdk:17-jdk-slim`. Caso utilize outra imagem (ex: `alpine`), pode haver falhas de build em algumas plataformas.

## Referências
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Springdoc OpenAPI](https://springdoc.org/)
- [SLF4J](http://www.slf4j.org/)
- [Jacoco](https://www.jacoco.org/jacoco/)
- [Docker](https://www.docker.com/)

---