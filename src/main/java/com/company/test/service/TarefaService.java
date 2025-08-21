package com.company.test.service;

import com.company.test.domain.*;
import com.company.test.repository.*;
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
        return tarefas.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public Tarefa create(Long projetoId, Tarefa t) {
        Projeto p = projetos.findById(projetoId).orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        t.setProjeto(p);
        return tarefas.save(t);
    }

    public Tarefa update(Long id, Tarefa t) {
        Tarefa existing = get(id);
        existing.setTitulo(t.getTitulo());
        existing.setDescricao(t.getDescricao());
        existing.setStatus(t.getStatus());
        existing.setPrioridade(t.getPrioridade());
        return existing;
    }

    public void delete(Long id) {
        tarefas.deleteById(id);
    }
}
