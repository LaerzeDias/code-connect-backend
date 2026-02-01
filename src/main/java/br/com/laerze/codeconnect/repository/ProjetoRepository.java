package br.com.laerze.codeconnect.repository;

import br.com.laerze.codeconnect.model.Projeto;
import br.com.laerze.codeconnect.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    @Query("""
    SELECT DISTINCT p FROM Projeto p
    LEFT JOIN p.tags t
    WHERE (:inputText IS NULL OR :inputText = ''
           OR (LOWER(p.titulo) LIKE LOWER(CONCAT('%', :inputText, '%'))
           OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :inputText, '%'))))
      AND (COALESCE(:tagNomes, NULL) IS NULL OR t.nome IN :tagNomes)
    """)
    Page<Projeto> buscarProjetosPaginados(
            Pageable pagina,
            @Param("inputText") String inputText,
            @Param("tagNomes") List<Tag> tagNomes
    );
}
