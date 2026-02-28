package br.com.laerze.codeconnect.model;

import br.com.laerze.codeconnect.DTO.request.DadosCadastroComentario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "COMENTARIOS")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJETO_ID", nullable = false)
    private Projeto projeto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMENTARIO_PAI_ID")
    private Comentario comentarioPai;

    @NotNull
    private LocalDateTime dataCriacao;

    public Comentario() {}

    public Comentario(Projeto projeto, Comentario comentarioPai, Usuario usuario, String conteudo) {
        this.projeto = projeto;
        this.comentarioPai = comentarioPai;
        this.usuario = usuario;
        this.conteudo = conteudo;
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }
}
