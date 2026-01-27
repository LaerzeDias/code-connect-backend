package br.com.laerze.codeconnect.service;

import br.com.laerze.codeconnect.DTO.ProjetoDetalhesDTO;
import br.com.laerze.codeconnect.DTO.ProjetoMinimoDTO;
import br.com.laerze.codeconnect.DTO.request.DadosCadastroProjetoDTO;
import br.com.laerze.codeconnect.Infra.DTO.CampoInvalidoDTO;
import br.com.laerze.codeconnect.Infra.expcetion.CampoInvalidoException;
import br.com.laerze.codeconnect.model.Projeto;
import br.com.laerze.codeconnect.model.Usuario;
import br.com.laerze.codeconnect.repository.ProjetoRepository;
import br.com.laerze.codeconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public Page<ProjetoMinimoDTO> buscarProjetos(Pageable pagina) {
        return projetoRepository.findAll(pagina).map(ProjetoMinimoDTO::new);
    }

    @Transactional
    public ProjetoDetalhesDTO cadastrarProjeto(DadosCadastroProjetoDTO dados) {

        // Verifica se o usuário informado nos dados recebidos existe
        Usuario usuario = usuarioRepository.findById(dados.usuarioId()).orElseThrow(() -> {
            List<CampoInvalidoDTO> camposInvalidos = List.of(new CampoInvalidoDTO(
                    "usuarioId", String.valueOf(dados.usuarioId()),
                    "Não existe um usuário para o id fornecido."));
            return new CampoInvalidoException("Não foi possível concluir o cadastro do projeto " +
                    "devido ao(s) motivo(s) abaixo:", camposInvalidos);
        });

        Projeto novoProjeto = new Projeto(dados);
        usuario.atribuirProjeto(novoProjeto); // Atribui o projeto ao usuário
        
        projetoRepository.save(novoProjeto); // Persiste o projeto no banco

        return new ProjetoDetalhesDTO(novoProjeto);
    }
}
