package br.com.laerze.codeconnect.repository;

import br.com.laerze.codeconnect.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
