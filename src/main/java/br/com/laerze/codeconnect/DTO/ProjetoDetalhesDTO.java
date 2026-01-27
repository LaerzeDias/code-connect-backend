package br.com.laerze.codeconnect.DTO;

import br.com.laerze.codeconnect.model.Comentario;
import br.com.laerze.codeconnect.model.Projeto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjetoDetalhesDTO(

        Long id,
        String titulo,
        String descricao,
        String codigo,
        String nomeUsuario,
        Integer numCurtidas,
        Integer numComentarios,
        List<Comentario> comentarios,
        LocalDateTime dataCriacao
) {
    public ProjetoDetalhesDTO(Projeto projeto) {
        this(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getDescricao(),
                projeto.getCodigo(),
                projeto.getUsuario().getNome(),
                projeto.getCurtidas(),
                projeto.getComentarios().size(),
                projeto.getComentarios(),
                projeto.getDataCriacao()
        );
    }
}
