package SNAKE_PC.demo.repository.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SNAKE_PC.demo.model.usuario.RolUsuario;

public interface RolRepository extends JpaRepository<RolUsuario, Long> {

    
    Optional<RolUsuario> findByNombreRol(String nombreRol);
    
}
