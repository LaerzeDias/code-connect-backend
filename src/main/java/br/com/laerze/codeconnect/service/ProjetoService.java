package br.com.laerze.codeconnect.service;

import br.com.laerze.codeconnect.DTO.ProjetoDetalhesDTO;
import br.com.laerze.codeconnect.DTO.ProjetoMinimoDTO;
import br.com.laerze.codeconnect.DTO.request.DadosCadastroProjetoDTO;
import br.com.laerze.codeconnect.DTO.request.DadosConsultaProjetoDTO;
import br.com.laerze.codeconnect.Infra.DTO.CampoInvalidoDTO;
import br.com.laerze.codeconnect.Infra.expcetion.CampoInvalidoException;
import br.com.laerze.codeconnect.model.Projeto;
import br.com.laerze.codeconnect.model.Tag;
import br.com.laerze.codeconnect.model.TagEntity;
import br.com.laerze.codeconnect.model.Usuario;
import br.com.laerze.codeconnect.repository.ProjetoRepository;
import br.com.laerze.codeconnect.repository.TagRepository;
import br.com.laerze.codeconnect.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TagRepository tagRepository;

    @Value("${upload.diretorio-projetos}")
    private String diretorioUpload;

    @Transactional(readOnly = true)
    public Page<ProjetoMinimoDTO> buscarProjetos(Pageable pagina, DadosConsultaProjetoDTO dadosConsulta) {

        List<Tag> tags = null;
        if (dadosConsulta.inputTags() != null && !dadosConsulta.inputTags().isEmpty()) {
            tags = dadosConsulta.inputTags().stream().map(Tag::fromString).toList();
        }

        System.out.println(tags);
        System.out.println(dadosConsulta.inputTags());

        String inputText = dadosConsulta.inputText() != null ? dadosConsulta.inputText() : null;

        return projetoRepository.buscarProjetosPaginados(pagina, inputText, tags).map(ProjetoMinimoDTO::new);
    }

    @Transactional(readOnly = true)
    public ProjetoDetalhesDTO buscarProjeto(Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId).orElseThrow(
                () -> new CampoInvalidoException(
                        "Não foi possível carregar as informações do projeto, devido ao(s) motivo(s) abaixo:",
                        List.of(new CampoInvalidoDTO("projetoId", projetoId,
                                "Não existe um projeto para o id informado"))));

        return new ProjetoDetalhesDTO(projeto);
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

        List<TagEntity> tags = dados.tags() != null ? mapearEntidadeTags(dados.tags()) : new ArrayList<>();
        String nomeImagem = salvarImagem(dados.imagem());
        Projeto novoProjeto = criarProjeto(dados, tags, usuario, nomeImagem);
        projetoRepository.save(novoProjeto); // Persiste o projeto no banco

        return new ProjetoDetalhesDTO(novoProjeto);
    }

    private Projeto criarProjeto(DadosCadastroProjetoDTO dados, List<TagEntity> tags,
                                 Usuario usuario, String nomeImagem) {
        Projeto novoProjeto = new Projeto(dados);
        novoProjeto.setTags(tags);
        novoProjeto.setImagemNome(nomeImagem);
        novoProjeto.setImagemUrl("/uploads/imagens/" + nomeImagem);
        usuario.atribuirProjeto(novoProjeto); // Atribui o projeto ao usuário

        return novoProjeto;
    }

    private String salvarImagem(MultipartFile imagem) {
        try {
            // 1. Criar o diretório se não existir
            Path caminhoDiretorio = Paths.get(diretorioUpload);
            if (!Files.exists(caminhoDiretorio)) {
                Files.createDirectories(caminhoDiretorio);
            }

            // 2. Gerar um nome único (UUID + nome original)
            String nomeArquivo = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
            Path caminhoCompleto = caminhoDiretorio.resolve(nomeArquivo);

            // 3. Salvar o arquivo no disco
            Files.copy(imagem.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            // 4. Retornar o nome/caminho para gravar no banco
            return nomeArquivo;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    private List<TagEntity> mapearEntidadeTags(List<String> tagsString) {

        return tagsString.stream()
                .distinct()
                .map(tag -> tagRepository.findByNome(Tag.fromString(tag))
                        .orElseThrow(() -> new RuntimeException("Tag não encontrada.")))
                .toList();
    }
}
