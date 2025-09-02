INSERT INTO PROJETO (nome, descricao, data_criacao) VALUES ('Projeto Alpha', 'Projeto inicial', CURRENT_TIMESTAMP());
INSERT INTO TAREFA (titulo, descricao, status, prioridade, projeto_id) VALUES
('Configurar projeto', 'Setup inicial', 'PENDENTE', 'ALTA', 1);
