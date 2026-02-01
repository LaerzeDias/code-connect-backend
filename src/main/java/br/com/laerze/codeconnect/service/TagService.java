package br.com.laerze.codeconnect.service;

import br.com.laerze.codeconnect.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<String> buscarTags() {
        return tagRepository.findAll().stream()
                .map(tagEntity -> tagEntity.getNome().getNomeAmigavel())
                .toList();
    }
}
