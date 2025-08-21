package com.company.test.repository;

import com.company.test.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {}

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("select t from Tarefa t where (:status is null or t.status = :status) and (:prioridade is null or t.prioridade = :prioridade) and (:projetoId is null or t.projeto.id = :projetoId)")
    List<Tarefa> filtrar(@Param("status") Status status, @Param("prioridade") Prioridade prioridade, @Param("projetoId") Long projetoId);
}
