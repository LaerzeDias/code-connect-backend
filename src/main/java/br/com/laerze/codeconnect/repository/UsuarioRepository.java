package br.com.laerze.codeconnect.repository;

import br.com.laerze.codeconnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
