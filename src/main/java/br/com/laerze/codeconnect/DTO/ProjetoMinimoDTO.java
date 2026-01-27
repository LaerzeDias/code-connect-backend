package br.com.laerze.codeconnect.DTO;

import br.com.laerze.codeconnect.model.Projeto;
import br.com.laerze.codeconnect.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

public record ProjetoMinimoDTO(

    Long id,
    String titulo,
    String descricao,
    String nomeUsuario,
    List<String> tags,
    Integer numCurtidas,
    Integer numComentarios,
    LocalDateTime dataCriacao
) {
    public ProjetoMinimoDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getUsuario().getNome(),
                projeto.getTags().stream().map(Tag::getNomeAmigavel).toList(),
                projeto.getCurtidas(),
                projeto.getComentarios().size(),
                projeto.getDataCriacao()
        );
    }
}
