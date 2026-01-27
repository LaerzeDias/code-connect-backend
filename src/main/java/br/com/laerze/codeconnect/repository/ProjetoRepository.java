package br.com.laerze.codeconnect.repository;

import br.com.laerze.codeconnect.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
