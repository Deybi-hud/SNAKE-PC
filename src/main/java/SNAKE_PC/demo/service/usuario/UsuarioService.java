package SNAKE_PC.demo.service.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import SNAKE_PC.demo.model.usuario.Usuario;
import SNAKE_PC.demo.repository.usuario.ContactoRepository;
import SNAKE_PC.demo.repository.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private ContactoRepository contactoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    UsuarioService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        if(usuarioRepository.existsByUsername(usuario.getNombreUsuario())){
            throw new RuntimeException("El usuario con ID " + usuario.getId() + " ya existe.");
        }
        if(usuarioRepository.existsByEmail(usuario.getCorreo())){
            throw new RuntimeException("El correo " + usuario.getCorreo() + " ya está en uso.");
        }
        if(!usuario.getContrasena().equals(usuario.getContrasena())){
            throw new RuntimeException("Las contraseñas no coinciden.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(usuario.getNombreUsuario());
        nuevoUsuario.setCorreo(usuario.getCorreo());
        nuevoUsuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioRepository.save(nuevoUsuario);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> findByNombreUsuario(Long id) {
        return usuarioRepository.findByNombreUsuario(id);
    }


    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    public Usuario ActualizarContrasena(Long id, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));

        if(usuario.getContrasena().equals(nuevaContrasena)){
            throw new RuntimeException("La nueva contraseña no puede ser igual a la anterior.");
        }
        if(nuevaContrasena.length() < 8){
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres.");
        }
        if(!nuevaContrasena.matches(".*[A-Z].*")){
            throw new RuntimeException("La contraseña debe contener al menos una letra mayúscula.");
        }
        if(!nuevaContrasena.matches(".*[a-z].*")){
            throw new RuntimeException("La contraseña debe contener al menos una letra minúscula.");
        }
        if(!nuevaContrasena.matches(".*\\d.*")){
            throw new RuntimeException("La contraseña debe contener al menos un número.");
        }
        if(!nuevaContrasena.matches(".*[!@#$%^&*()].*")){
            throw new RuntimeException("La contraseña debe contener al menos un carácter especial.");
        }
        if(nuevaContrasena.contains(" ")){
            throw new RuntimeException("La contraseña no puede contener espacios.");
        }
        if(nuevaContrasena.equalsIgnoreCase(usuario.getNombreUsuario())){
            throw new RuntimeException("La contraseña no puede ser igual al nombre de usuario.");
        }
        if(nuevaContrasena.equalsIgnoreCase(usuario.getCorreo())){
            throw new RuntimeException("La contraseña no puede ser igual al correo.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findById(usuarioActualizado.getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioActualizado.getId()));
        if(usuarioRepository.existsByUsername(usuarioActualizado.getNombreUsuario()) &&!usuario.getNombreUsuario().equals(usuarioActualizado.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario " + usuarioActualizado.getNombreUsuario() + " ya está en uso.");
        }
        if(usuarioRepository.existsByEmail(usuarioActualizado.getCorreo()) &&!usuario.getCorreo().equals(usuarioActualizado.getCorreo())) {
            throw new RuntimeException("El correo " + usuarioActualizado.getCorreo() + " ya está en uso."); 
        }

        usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        return usuarioRepository.save(usuario);
    }


    public void eliminarUsuario(Long id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            contactoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }

}
