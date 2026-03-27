package ar.edu.huergo.aguilar.borassi.tunari.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Rol;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.repository.security.RolRepository;
import ar.edu.huergo.aguilar.borassi.tunari.repository.security.UsuarioRepository;
import ar.edu.huergo.aguilar.borassi.tunari.util.PasswordValidator;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return args -> {
            Rol admin = rolRepository.findByNombre("ADMIN").orElseGet(() -> rolRepository.save(new Rol("ADMIN")));
            Rol cliente = rolRepository.findByNombre("CLIENTE").orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));
            Rol marca = rolRepository.findByNombre("MARCA").orElseGet(() -> rolRepository.save(new Rol("MARCA")));
            
            if (usuarioRepository.findByUsername("admin@tunari.aguilar.borassi.edu.ar").isEmpty()) {
                String adminPassword = "AdminSuperSegura@123";
                PasswordValidator.validate(adminPassword);
                Usuario u = new Usuario("admin@tunari.aguilar.borassi.edu.ar", encoder.encode(adminPassword));
                u.setRoles(Set.of(admin));
                usuarioRepository.save(u);
            }

            if (usuarioRepository.findByUsername("cliente@tunari.aguilar.borassi.edu.ar").isEmpty()) {
                String clientePassword = "ClienteSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Usuario u = new Usuario("cliente@tunari.aguilar.borassi.edu.ar", encoder.encode(clientePassword));
                u.setRoles(Set.of(cliente));
                usuarioRepository.save(u);
            }

            if (usuarioRepository.findByUsername("marca@tunari.aguilar.borassi.edu.ar").isEmpty()) {
                String marcaPassword = "MarcaSuperSegura@123";
                PasswordValidator.validate(marcaPassword);
                Usuario u = new Usuario("marca@tunari.aguilar.borassi.edu.ar", encoder.encode(marcaPassword));
                u.setRoles(Set.of(marca));
                usuarioRepository.save(u);
            }
        };
    }
}






