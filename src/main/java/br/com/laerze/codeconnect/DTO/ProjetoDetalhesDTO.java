package br.com.laerze.codeconnect.DTO;

import br.com.laerze.codeconnect.model.Projeto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjetoDetalhesDTO(

        Long id,
        String titulo,
        String descricao,
        String codigo,
        String nomeUsuario,
        List<String> tags,
        String imagemUrl,
        Integer numContribuicoes,
        Integer numCompartilhamentos,
        Integer numComentarios,
        List<ComentarioDetalhesDTO> comentarios,
        LocalDateTime dataCriacao
) {
    public ProjetoDetalhesDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getCodigo(),
                projeto.getUsuario().getNome(),
                projeto.getTags().stream().map(tagEntity -> tagEntity.getNome().getNomeAmigavel()).toList(),
                "/imagens/" + projeto.getImagemNome(),
                projeto.getNumContribuicoes(),
                projeto.getNumCompartilhamentos(),
                projeto.getNumComentarios(),
                projeto.getComentarios().stream()
                        .filter(c -> c.getComentarioPai() == null)
                        .map(ComentarioDetalhesDTO::new).toList(),
                projeto.getDataCriacao()
        );
    }
}
