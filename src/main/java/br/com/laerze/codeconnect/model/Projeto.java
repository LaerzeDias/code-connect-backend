package br.com.laerze.codeconnect.model;

import br.com.laerze.codeconnect.DTO.request.DadosCadastroProjetoDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PROJETOS")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String codigo;

    @ManyToMany
    @JoinTable(
            name = "PROJETOS_TAGS",
            joinColumns = @JoinColumn(name = "projeto_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    private Integer numContribuicoes = 0;
    private Integer numCompartilhamentos = 0;
    private Integer numComentarios = 0;
    private String imagemNome;
    private String imagemUrl;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();
    private LocalDateTime dataCriacao;

    public Projeto() {}

    public Projeto(DadosCadastroProjetoDTO dadosCadastroProjetoDTO) {
        this.titulo = dadosCadastroProjetoDTO.titulo();
        this.descricao = dadosCadastroProjetoDTO.descricao();
    }

    public void incluirComentario (Comentario comentario) {
        this.comentarios.add(comentario);
        this.numComentarios ++;
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }
}
