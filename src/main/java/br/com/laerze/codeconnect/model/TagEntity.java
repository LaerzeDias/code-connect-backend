package br.com.laerze.codeconnect.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TAGS")
@Getter
@Setter
@EqualsAndHashCode
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Tag nome;

    public TagEntity() {}

    public TagEntity(Tag nome) {
        this.nome = nome;
    }
}
