package br.com.laerze.codeconnect.Infra.expcetion;

import br.com.laerze.codeconnect.Infra.DTO.CampoInvalidoDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class CampoInvalidoException extends RuntimeException {

    private final List<CampoInvalidoDTO> camposInvalidos;

    public CampoInvalidoException(String message, List<CampoInvalidoDTO> camposInvalidos) {
        super(message);
        this.camposInvalidos = camposInvalidos;
    }
}
