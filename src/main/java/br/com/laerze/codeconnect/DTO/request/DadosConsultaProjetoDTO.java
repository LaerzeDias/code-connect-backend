package br.com.laerze.codeconnect.DTO.request;

import java.util.List;

public record DadosConsultaProjetoDTO(

        String inputText,
        List<String> listaTags
) {
}
