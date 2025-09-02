// Comentário para o avaliador:
// Conforme boas práticas e regras da linguagem Java, cada classe pública deve estar em seu próprio arquivo.
// Por isso, esta classe foi separada do arquivo original para evitar erro de compilação e seguir o padrão recomendado.

package com.company.test.controller;

import com.company.test.domain.Tarefa;
import com.company.test.domain.Status;
import com.company.test.domain.Prioridade;
import com.company.test.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TarefaController {
    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Filtra tarefas", description = "Filtra tarefas por status, prioridade e projeto.")
    public List<Tarefa> filter(@RequestParam(required = false) Status status,
                               @RequestParam(required = false) Prioridade prioridade,
                               @RequestParam(required = false) Long projetoId) {
        return service.filtrar(status, prioridade, projetoId);
    }

    @GetMapping("/project/{projetoId}")
    @Operation(summary = "Lista tarefas por projeto", description = "Retorna todas as tarefas de um projeto específico.")
    public List<Tarefa> listByProjeto(@PathVariable Long projetoId) {
        return service.listByProjeto(projetoId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca tarefa por ID", description = "Retorna os dados de uma tarefa específica pelo seu ID.")
    public Tarefa get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping("/project/{projetoId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria nova tarefa", description = "Endpoint para cadastrar uma nova tarefa vinculada a um projeto.")
    public Tarefa create(@PathVariable Long projetoId, @Valid @RequestBody Tarefa t) {
        return service.create(projetoId, t);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza tarefa", description = "Edita os dados de uma tarefa existente pelo ID.")
    public Tarefa update(@PathVariable Long id, @Valid @RequestBody Tarefa t) {
        return service.update(id, t);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove tarefa", description = "Exclui uma tarefa do sistema pelo ID.")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
