package com.company.test.web;

import com.company.test.domain.*;
import com.company.test.service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjetoController {
    private final ProjetoService service;
    public ProjetoController(ProjetoService service) { this.service = service; }

    @GetMapping public List<Projeto> list() { return service.list(); }
    @GetMapping("/{id}") public Projeto get(@PathVariable Long id) { return service.get(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Projeto create(@Valid @RequestBody Projeto p) { return service.create(p); }
    @PutMapping("/{id}") public Projeto update(@PathVariable Long id, @Valid @RequestBody Projeto p) { return service.update(id, p); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}

@RestController
@RequestMapping("/api/tasks")
class TarefaController {
    private final TarefaService service;
    public TarefaController(TarefaService service) { this.service = service; }

    @GetMapping public List<Tarefa> filter(@RequestParam(required = false) Status status,
                                           @RequestParam(required = false) Prioridade prioridade,
                                           @RequestParam(required = false) Long projetoId) {
        return service.filtrar(status, prioridade, projetoId);
    }

    @GetMapping("/project/{projetoId}") public List<Tarefa> listByProjeto(@PathVariable Long projetoId) { return service.listByProjeto(projetoId); }

    @GetMapping("/{id}") public Tarefa get(@PathVariable Long id) { return service.get(id); }

    @PostMapping("/project/{projetoId}") @ResponseStatus(HttpStatus.CREATED)
    public Tarefa create(@PathVariable Long projetoId, @Valid @RequestBody Tarefa t) { return service.create(projetoId, t); }

    @PutMapping("/{id}") public Tarefa update(@PathVariable Long id, @Valid @RequestBody Tarefa t) { return service.update(id, t); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
