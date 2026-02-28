package br.com.laerze.codeconnect.controller;

import br.com.laerze.codeconnect.DTO.ProjetoDetalhesDTO;
import br.com.laerze.codeconnect.DTO.ProjetoMinimoDTO;
import br.com.laerze.codeconnect.DTO.request.DadosCadastroProjetoDTO;
import br.com.laerze.codeconnect.DTO.request.DadosConsultaProjetoDTO;
import br.com.laerze.codeconnect.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public ResponseEntity<Page<ProjetoMinimoDTO>> buscarProjetos(Pageable pagina,
                                                                 DadosConsultaProjetoDTO dadosConsulta) {
        return ResponseEntity.ok(projetoService.buscarProjetos(pagina, dadosConsulta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDetalhesDTO> buscarProjeto(@PathVariable Long id) {
        ProjetoDetalhesDTO projetoDetalhesDTO = projetoService.buscarProjeto(id);

        return ResponseEntity.ok(projetoDetalhesDTO);
    }

    @PostMapping
    public ResponseEntity<ProjetoDetalhesDTO> cadastrarProjeto(@ModelAttribute @Valid DadosCadastroProjetoDTO dados) {

        ProjetoDetalhesDTO projetoDetalhesDTO = projetoService.cadastrarProjeto(dados);

        URI novoProjeto = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(projetoDetalhesDTO.id())
                .toUri();

        return ResponseEntity.created(novoProjeto).body(projetoDetalhesDTO);
    }
}
