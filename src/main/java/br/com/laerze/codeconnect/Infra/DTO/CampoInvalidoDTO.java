package br.com.laerze.codeconnect.Infra.DTO;

import org.springframework.validation.FieldError;

public record CampoInvalidoDTO(

        String campo,
        Object valorRejeitado,
        String motivo
) {
    public CampoInvalidoDTO(FieldError error) {
        this(
                error.getField(),
                error.getRejectedValue(),
                String.format("Campo %s %s.", error.getField(), error.getDefaultMessage())
        );
    }
}
