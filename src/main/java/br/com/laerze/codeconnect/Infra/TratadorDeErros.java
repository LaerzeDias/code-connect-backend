package br.com.laerze.codeconnect.Infra;

import br.com.laerze.codeconnect.Infra.DTO.CampoInvalidoDTO;
import br.com.laerze.codeconnect.Infra.DTO.ErroDTO;
import br.com.laerze.codeconnect.Infra.expcetion.CampoInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(CampoInvalidoException.class)
    public ResponseEntity<ErroDTO> tratarCampoInvalidoException(CampoInvalidoException ex,
                                                                 WebRequest webRequest) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErroDTO erro = new ErroDTO(
                Instant.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                ex.getCamposInvalidos(),
                webRequest.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(status.value()).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> tratarArgumentoInvalido(MethodArgumentNotValidException ex,
                                                           WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<CampoInvalidoDTO> camposInvalidos = ex.getFieldErrors().stream()
                .map(CampoInvalidoDTO::new)
                .toList();

        ErroDTO erro = new ErroDTO(
                Instant.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                "Não foi possível concluir a operação desejada devido ao(s) motivo(s) abaixo:",
                camposInvalidos,
                webRequest.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(status.value()).body(erro);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErroDTO> tratarErroTamanhoMaximoAtingido(MaxUploadSizeExceededException ex,
                                                                   WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<CampoInvalidoDTO> camposInvalidos = List.of(new CampoInvalidoDTO("imagem", ex.getLocalizedMessage(),
                String.format("O tamanho do arquivo ultrapassa o limite de %d MBs máximos", ex.getMaxUploadSize())));

        ErroDTO erro = new ErroDTO(
                Instant.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                "Não foi possível concluir a operação desejada devido ao(s) motivo(s) abaixo:",
                camposInvalidos,
                webRequest.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(status.value()).body(erro);
    }
}
