# Desafio Técnico - Java Backend (Senior)

Bem-vindo(a)! Este é o desafio técnico para candidatos a vaga **Pessoa Desenvolvedora - Full stack - Java e Angular**.
O objetivo é avaliar sua capacidade de **desenhar, implementar e testar APIs REST** com boas práticas de engenharia.

---

## 🎯 Objetivo do Teste

Crie uma API REST para gerenciamento de **projetos e tarefas**, com as seguintes regras:

### Entidades
- **Projeto**
  - id
  - nome
  - descricao
  - dataCriacao

- **Tarefa**
  - id
  - titulo
  - descricao
  - status (PENDENTE, EM_ANDAMENTO, CONCLUIDA)
  - prioridade (BAIXA, MEDIA, ALTA)
  - projetoId (relacionamento com Projeto)

### Requisitos funcionais
1. CRUD de Projetos (listar, buscar por id, criar, editar, remover).
2. CRUD de Tarefas (listar por projeto, buscar por id, criar, editar, remover).
3. Endpoint para **filtrar tarefas por status e prioridade**.
4. Validações com Bean Validation (ex.: campos obrigatórios, limites de tamanho).
5. Persistência em banco relacional (use **H2**).
6. Tratamento de erros (HTTP codes corretos, mensagens claras).
7. **Testes unitários e de integração** com JUnit/Mockito.

---

## 🧑‍💻 Diferenciais (não obrigatórios, mas valorizados)

- Documentação com **Swagger/OpenAPI** (springdoc-openapi).
- Spring Security (auth básica ou JWT).
- Logs estruturados (SLF4J, Logback).
- Dockerfile para rodar a aplicação.
- Arquitetura hexagonal/clean architecture.
- Cobertura de testes >= 70%.

---

## ✅ O que será avaliado

- Organização da arquitetura (camadas controller/service/repository).
- Uso idiomático do Spring Boot e boas práticas.
- Qualidade dos testes e cobertura.
- Clareza de código e commits.
- Documentação (README explicando setup e decisões).

---

## 🚀 Instruções

1. Faça um fork deste repositório.
2. Crie uma branch com seu nome:
   ```bash
   git checkout -b feature/seu-nome
   ```
3. Implemente sua solução.
4. Abra um Pull Request.

---

## 🛠️ Como rodar

### Via Maven
```bash
mvn clean spring-boot:run
```

### Rodar testes
```bash
mvn test
```

H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, user: `sa`, sem senha).

Boa sorte 🚀
