package com.company.test.service;

import com.company.test.domain.*;
import com.company.test.repository.*;
import com.company.test.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TarefaService {
    private final TarefaRepository tarefas;
    private final ProjetoRepository projetos;

    public TarefaService(TarefaRepository tarefas, ProjetoRepository projetos) {
        this.tarefas = tarefas;
        this.projetos = projetos;
    }

    public List<Tarefa> listByProjeto(Long projetoId) {
        return tarefas.filtrar(null, null, projetoId);
    }

    public List<Tarefa> filtrar(Status status, Prioridade prioridade, Long projetoId) {
        return tarefas.filtrar(status, prioridade, projetoId);
    }

    public Tarefa get(Long id) {
        return tarefas.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

        private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TarefaService.class);
    public Tarefa create(Long projetoId, Tarefa tarefa) {
        logger.info("Criando tarefa: {} para projetoId: {}", tarefa.getTitulo(), projetoId);
        if (tarefa.getTitulo() != null && tarefa.getTitulo().length() > 50) {
            throw new ValidationException("O título da tarefa não pode exceder 50 caracteres.");
        }
        if (tarefa.getDescricao() != null && tarefa.getDescricao().length() > 255) {
            throw new ValidationException("A descrição da tarefa não pode exceder 255 caracteres.");
        }
        Projeto projeto = projetos.findById(projetoId)
            .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        tarefa.setProjeto(projeto);
        return tarefas.save(tarefa);
    }

    public Tarefa update(Long id, Tarefa tarefa) {
        if (tarefa.getTitulo() != null && tarefa.getTitulo().length() > 50) {
            throw new ValidationException("O título da tarefa não pode exceder 50 caracteres.");
        }
        if (tarefa.getDescricao() != null && tarefa.getDescricao().length() > 255) {
            throw new ValidationException("A descrição da tarefa não pode exceder 255 caracteres.");
        }
        if (!tarefas.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        Tarefa tarefaExistente = get(id);
        tarefaExistente.setTitulo(tarefa.getTitulo());
        tarefaExistente.setDescricao(tarefa.getDescricao());
        tarefaExistente.setStatus(tarefa.getStatus());
        tarefaExistente.setPrioridade(tarefa.getPrioridade());
        return tarefaExistente;
    }

    public void delete(Long id) {
        if (!tarefas.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada");
        }
        tarefas.deleteById(id);
    }
}
