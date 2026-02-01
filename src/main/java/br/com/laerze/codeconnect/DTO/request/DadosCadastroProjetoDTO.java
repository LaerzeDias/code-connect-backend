package br.com.laerze.codeconnect.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record DadosCadastroProjetoDTO(

        @NotNull
        Long usuarioId,

        @NotBlank
        @Pattern(regexp = "^.{5,}$", message = "deve conter no mínimo 5 caracteres")
        String titulo,

        @NotBlank
        @Pattern(regexp = "^.{50,}$", message = "deve conter no mínimo 50 caracteres")
        String descricao,

        @NotNull
        MultipartFile imagem,

        List<String> tags
) {
}
