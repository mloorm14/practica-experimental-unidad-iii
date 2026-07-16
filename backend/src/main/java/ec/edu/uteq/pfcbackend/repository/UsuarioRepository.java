package ec.edu.uteq.pfcbackend.repository;

import ec.edu.uteq.pfcbackend.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
