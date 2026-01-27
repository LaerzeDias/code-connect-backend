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

    @ElementCollection(targetClass = Tag.class)
    @CollectionTable(name = "PROJETO_TAGS", joinColumns = @JoinColumn(name = "projeto_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tag_nome")
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    private Integer curtidas;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();
    private LocalDateTime dataCriacao;

    public Projeto() {}

    public Projeto(DadosCadastroProjetoDTO dadosCadastroProjetoDTO) {
        this.titulo = dadosCadastroProjetoDTO.titulo();
        this.descricao = dadosCadastroProjetoDTO.descricao();
        this.tags = dadosCadastroProjetoDTO.tags();
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }
}
