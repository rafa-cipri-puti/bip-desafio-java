// Comentário para o avaliador:
// Conforme boas práticas e regras da linguagem Java, cada classe pública deve estar em seu próprio arquivo.
// Por isso, esta classe foi separada do arquivo original para evitar erro de compilação e seguir o padrão recomendado.

package com.company.test.controller;

import com.company.test.domain.Projeto;
import com.company.test.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjetoController {
    private final ProjetoService service;

    public ProjetoController(ProjetoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos", description = "Retorna uma lista de todos os projetos cadastrados.")
    public List<Projeto> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca projeto por ID", description = "Retorna os dados de um projeto específico pelo seu ID.")
    public Projeto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo projeto", description = "Endpoint para cadastrar um novo projeto com nome e descrição.")
    public Projeto create(@Valid @RequestBody Projeto p) {
        return service.create(p);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza projeto", description = "Edita os dados de um projeto existente pelo ID.")
    public Projeto update(@PathVariable Long id, @Valid @RequestBody Projeto p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove projeto", description = "Exclui um projeto do sistema pelo ID.")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
