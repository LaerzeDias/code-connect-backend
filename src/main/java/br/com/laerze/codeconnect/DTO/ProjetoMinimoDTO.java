package br.com.laerze.codeconnect.DTO;

import br.com.laerze.codeconnect.model.Projeto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjetoMinimoDTO(

    Long id,
    String titulo,
    String descricao,
    String nomeUsuario,
    List<String> tags,
    String imagemUrl,
    Integer numContribuicoes,
    Integer numCompartilhamentos,
    Integer numComentarios,
    LocalDateTime dataCriacao
) {
    public ProjetoMinimoDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getUsuario().getNome(),
                projeto.getTags().stream().map(tagEntity -> tagEntity.getNome().getNomeAmigavel()).toList(),
                "/imagens/" + projeto.getImagemNome(),
                projeto.getNumContribuicoes(),
                projeto.getNumCompartilhamentos(),
                projeto.getNumComentarios(),
                projeto.getDataCriacao()
        );
    }
}
