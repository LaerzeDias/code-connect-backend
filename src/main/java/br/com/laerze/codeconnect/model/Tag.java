package br.com.laerze.codeconnect.model;

import br.com.laerze.codeconnect.Infra.DTO.CampoInvalidoDTO;
import br.com.laerze.codeconnect.Infra.expcetion.CampoInvalidoException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.List;

@Getter
public enum Tag {

    JAVA("Java"),
    JAVASCRIPT("Javascript"),
    PYTHON("Python"),
    C("C"),
    CS("C#"),
    CPP("C++"),
    KOTLIN("Kotlin"),
    TYPESCRIPT("Typescript"),
    FULLSTACK("Fullstack"),
    BACKEND("Back-end"),
    FRONTEND("Front-end"),
    MOBILE("Mobile"),
    DESIGN("Design"),
    DEVOPS("DevOps"),
    DATASCIENCE("Data Science"),
    UX("UX"),
    UI("UI"),
    PHOTOSHOP("Photoshop"),
    CANVA("Canva"),
    RESTAPI("Rest API"),
    FIGMA("Figma"),
    REACT("React"),
    ANGULAR("Angular"),
    VUE("Vue"),
    HTML("HTML"),
    CSS("CSS");

    private final String nomeAmigavel;

    Tag(String nomeAmigavel) {
        this.nomeAmigavel = nomeAmigavel;
    }

    @JsonCreator
    public static Tag fromString(String tagString) {

        if (tagString == null || tagString.isBlank()) {
            lancarErro(tagString, "A tag inserida não deve ser vazia.");
        }

        for (Tag tag : Tag.values()) {
            if (tag.nomeAmigavel.equalsIgnoreCase(tagString)) {
                return tag;
            }
        }

        lancarErro(tagString, "A tag inserida não corresponde a nenhuma tag válida.");
        return null;
    }

    private static void lancarErro(String tagString, String motivo) {

        String mensagemErro = "Não foi possível concluir a operação desejada devido ao(s) motivo(s) abaixo:";
        List<CampoInvalidoDTO> camposInvalidos = List.of(new CampoInvalidoDTO("listaTags", tagString, motivo));
        throw new CampoInvalidoException(mensagemErro, camposInvalidos);
    }
}