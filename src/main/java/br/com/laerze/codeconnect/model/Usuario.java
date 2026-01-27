package br.com.laerze.codeconnect.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Projeto> projetos = new ArrayList<>();;

    public Usuario() {}

    public void atribuirProjeto(Projeto projeto) {
        projetos.add(projeto);
        projeto.setUsuario(this);
    }
}
