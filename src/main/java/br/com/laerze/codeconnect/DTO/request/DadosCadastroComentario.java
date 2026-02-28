package br.com.laerze.codeconnect.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroComentario(

        @NotNull
        Long usuarioId,

        @NotNull
        Long projetoId,

        Long comentarioPaiId,

        @NotBlank
        @Pattern(regexp = "^.{10,}$", message = "deve conter no mínimo 10 caracteres")
        String conteudo
) {
}
