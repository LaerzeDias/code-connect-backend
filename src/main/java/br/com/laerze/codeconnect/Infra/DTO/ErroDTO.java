package br.com.laerze.codeconnect.Infra.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroDTO(

        String dataHora,
        Integer status,
        String erro,
        String mensagem,
        List<CampoInvalidoDTO> erros,
        String caminho
) {
}
