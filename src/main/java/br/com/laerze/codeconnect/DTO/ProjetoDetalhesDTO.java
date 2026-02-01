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
        String imagemUrl,
        Integer numContribuicoes,
        Integer numCompartilhamentos,
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
                "/imagens/" + projeto.getImagemNome(),
                projeto.getNumContribuicoes(),
                projeto.getNumCompartilhamentos(),
                projeto.getComentarios().size(),
                projeto.getComentarios(),
                projeto.getDataCriacao()
        );
    }
}
