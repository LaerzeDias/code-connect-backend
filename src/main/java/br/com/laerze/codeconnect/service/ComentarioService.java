package br.com.laerze.codeconnect.service;

import br.com.laerze.codeconnect.DTO.ComentarioDetalhesDTO;
import br.com.laerze.codeconnect.DTO.request.DadosCadastroComentario;
import br.com.laerze.codeconnect.Infra.DTO.CampoInvalidoDTO;
import br.com.laerze.codeconnect.Infra.expcetion.CampoInvalidoException;
import br.com.laerze.codeconnect.model.Comentario;
import br.com.laerze.codeconnect.model.Projeto;
import br.com.laerze.codeconnect.model.Usuario;
import br.com.laerze.codeconnect.repository.ComentarioRepository;
import br.com.laerze.codeconnect.repository.ProjetoRepository;
import br.com.laerze.codeconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ComentarioDetalhesDTO publicarComentario(DadosCadastroComentario dados) {

        List<CampoInvalidoDTO> camposInvalidos = new ArrayList<>();
        String mensagem = "Não foi possível concluir a publicação do comentário devido ao(s) motivo(s) abaixo:";

        Projeto projeto = null;
        Optional<Projeto> projetoOptional = projetoRepository.findById(dados.projetoId());
        if (projetoOptional.isEmpty()) {
            camposInvalidos.add(new CampoInvalidoDTO("projetoId", dados.projetoId(),
                    "Não existe um projeto para o id informado."));
        } else {
            projeto = projetoOptional.get();
        }

        Usuario usuario = null;
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(dados.usuarioId());
        if (usuarioOptional.isEmpty()) {
            camposInvalidos.add(new CampoInvalidoDTO("usuarioId", dados.usuarioId(),
                    "Não existe um usuário para o id informado."));
        } else {
            usuario = usuarioOptional.get();
        }

        Comentario comentarioPai = null;
        if (dados.comentarioPaiId() != null) {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(dados.comentarioPaiId());
            if (comentarioOptional.isEmpty()) {
                camposInvalidos.add(new CampoInvalidoDTO("comentarioPaiId", dados.comentarioPaiId(),
                        "Não existe um comentário pai para o id informado."));
            } else {
                comentarioPai = comentarioOptional.get();
                if (!comentarioPai.getProjeto().getId().equals(dados.projetoId())) {
                    camposInvalidos.add(new CampoInvalidoDTO("comentarioPaiId", dados.comentarioPaiId(),
                            "O comentário pai não pertence ao projeto informado."));
                }
            }
        }

        if (!camposInvalidos.isEmpty()) {
            throw new CampoInvalidoException(mensagem, camposInvalidos);
        }

        Comentario novoComentario = new Comentario(projeto, comentarioPai, usuario, dados.conteudo());
        projeto.incluirComentario(novoComentario);
        comentarioRepository.save(novoComentario);

        return new ComentarioDetalhesDTO(novoComentario);
    }
}
