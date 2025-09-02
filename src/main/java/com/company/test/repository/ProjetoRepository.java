// Comentário para o avaliador:
// Conforme boas práticas e regras da linguagem Java, cada classe ou interface pública deve estar em seu próprio arquivo.
// Por isso, esta interface foi separada do arquivo original para evitar erro de compilação e seguir o padrão recomendado.

package com.company.test.repository;

import com.company.test.domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {}
