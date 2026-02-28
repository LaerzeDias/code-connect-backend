package br.com.laerze.codeconnect.controller;

import br.com.laerze.codeconnect.DTO.ComentarioDetalhesDTO;
import br.com.laerze.codeconnect.DTO.request.DadosCadastroComentario;
import br.com.laerze.codeconnect.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<ComentarioDetalhesDTO> publicarComentario(@RequestBody DadosCadastroComentario dados) {
        ComentarioDetalhesDTO comentarioDetalhesDTO = comentarioService.publicarComentario(dados);

        URI novoComentario = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comentarioDetalhesDTO.id())
                .toUri();

        return ResponseEntity.created(novoComentario).body(comentarioDetalhesDTO);
    }
}
