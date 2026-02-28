package br.com.laerze.codeconnect.DTO;

import br.com.laerze.codeconnect.model.Comentario;

import java.time.LocalDateTime;
import java.util.List;

public record ComentarioDetalhesDTO(

        Long id,
        Long projetoId,
        Long comentarioPaiId,
        UsuarioDetalhesDTO usuario,
        String conteudo,
        List<ComentarioDetalhesDTO> comentarios,
        LocalDateTime dataCriacao
) {
    public ComentarioDetalhesDTO(Comentario comentario) {
        this(
                comentario.getId(),
                comentario.getProjeto().getId(),
                comentario.getComentarioPai() != null ? comentario.getComentarioPai().getId() : null,
                new UsuarioDetalhesDTO(comentario.getUsuario()),
                comentario.getConteudo(),
                comentario.getProjeto().getComentarios().stream()
                        .filter(c ->
                                c.getComentarioPai() != null && c.getComentarioPai().getId().equals(comentario.getId()))
                        .map(ComentarioDetalhesDTO::new)
                        .toList(),
                comentario.getDataCriacao()
        );
    }
}
