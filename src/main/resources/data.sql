INSERT INTO PROJETO (id, nome, descricao, data_criacao) VALUES (1, 'Projeto Alpha', 'Projeto inicial', CURRENT_TIMESTAMP());
INSERT INTO TAREFA (id, titulo, descricao, status, prioridade, projeto_id) VALUES
(1, 'Configurar projeto', 'Setup inicial', 'PENDENTE', 'ALTA', 1);
