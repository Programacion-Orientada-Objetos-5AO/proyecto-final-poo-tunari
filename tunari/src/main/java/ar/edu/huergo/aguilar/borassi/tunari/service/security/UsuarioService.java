package ar.edu.huergo.aguilar.borassi.tunari.service.security;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Rol;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.repository.security.RolRepository;
import ar.edu.huergo.aguilar.borassi.tunari.repository.security.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    // private final JwtTokenService jwtTokenService;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario registrar(Usuario usuario, String password, String verificacionPassword) {
        //  Validación de contraseñas
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        //  Validación de usuario repetido
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // Encriptar la contraseña
        usuario.setPassword(passwordEncoder.encode(password));

        // Asignar el rol CLIENTE por defecto
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
            .orElseThrow(() -> new IllegalArgumentException("Rol 'CLIENTE' no encontrado"));

        usuario.setRoles(Set.of(rolCliente));

        //  Guardar usuario
        return usuarioRepository.save(usuario);
    }

    /*
    public Usuario resolverUsuario(String token) {
        token = token.replace("Bearer ", "");
        String username = this.jwtTokenService.extraerUsername(token);
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
    */
}