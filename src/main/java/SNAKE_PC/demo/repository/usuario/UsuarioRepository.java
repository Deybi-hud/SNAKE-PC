package SNAKE_PC.demo.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import SNAKE_PC.demo.model.usuario.Usuario;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    
    Optional<Usuario> findByNombreUsuario(Long id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    
}
