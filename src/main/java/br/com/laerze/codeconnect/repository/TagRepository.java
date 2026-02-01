package br.com.laerze.codeconnect.repository;

import br.com.laerze.codeconnect.model.Tag;
import br.com.laerze.codeconnect.model.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    Optional<TagEntity> findByNome(Tag tag);
}
