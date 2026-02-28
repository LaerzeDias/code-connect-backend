package br.com.laerze.codeconnect.DTO;

import br.com.laerze.codeconnect.model.Usuario;

public record UsuarioDetalhesDTO(
        Long id,
        String nome
) {
    public UsuarioDetalhesDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome()
        );
    }
}
