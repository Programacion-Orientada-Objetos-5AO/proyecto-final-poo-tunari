package ar.edu.huergo.aguilar.borassi.tunari.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.aguilar.borassi.tunari.repository.security.UsuarioRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http.csrf(csrf -> csrf
                // Habilitamos CSRF solo para vistas web (Thymeleaf), lo ignoramos en la API
                .csrfTokenRepository(org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/**"))

            .sessionManagement(session -> session
                // API usa JWT (stateless), web usa sesiones normales
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false))

            .authorizeHttpRequests(auth -> auth
                // --- RUTAS PÚBLICAS ---
                .requestMatchers(
                    "/web/", 
                    "/web/inicio", 
                    "/web/login", 
                    "/web/registro", 
                    "/web/acerca",
                    "/css/**", 
                    "/script/**", 
                    "/images/**", 
                    "/favicon.ico"
                ).permitAll()

                // --- RUTAS PROTEGIDAS ---
                .requestMatchers("/web/dashboard/**", "/web/perfil/**").authenticated()

                // --- API pública ---
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll()

                // --- API protegida ---
                .requestMatchers("/api/**").authenticated()

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated())

            // --- LOGIN WEB PERSONALIZADO ---
            .formLogin(form -> form
                .loginPage("/web/login")             // Página de login Thymeleaf
                .loginProcessingUrl("/web/login")    // Endpoint POST que procesa el login
                .defaultSuccessUrl("/web/inicio", true) // Redirige al iniciar sesión correctamente
                .failureUrl("/web/login?error")      // Redirige si falla el login
                .permitAll())

            // --- LOGOUT PERSONALIZADO ---
            .logout(logout -> logout
                .logoutUrl("/web/logout")
                .logoutSuccessUrl("/web/login?logout")
                .permitAll())

            // --- MANEJO DE ERRORES ---
            .exceptionHandling(exceptions -> exceptions
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint()))

            // --- FILTRO JWT PARA API ---
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // --- ENCODER DE CONTRASEÑAS ---
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- HANDLER: Acceso denegado ---
    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(403);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(java.util.Map.of(
                "type", "https://http.dev/problems/access-denied",
                "title", "Acceso denegado",
                "status", 403,
                "detail", "No tenés permisos para acceder a este recurso"
            ));

            response.getWriter().write(jsonResponse);
        };
    }

    // --- HANDLER: Usuario no autenticado ---
    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(java.util.Map.of(
                "type", "https://http.dev/problems/unauthorized",
                "title", "No autorizado",
                "status", 401,
                "detail", "Credenciales inválidas o faltantes"
            ));

            response.getWriter().write(jsonResponse);
        };
    }

    // --- CARGA DE USUARIO DESDE BASE ---
    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> usuarioRepository.findByUsername(username)
                .map(usuario -> org.springframework.security.core.userdetails.User
                        .withUsername(usuario.getUsername())
                        .password(usuario.getPassword())
                        .roles(usuario.getRoles().stream()
                            .map(r -> r.getNombre())
                            .toArray(String[]::new))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    // --- AUTENTICACIÓN LOCAL ---
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // --- AUTH MANAGER ---
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}
