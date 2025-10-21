package ar.edu.huergo.aguilar.borassi.tunari.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            // Autorizaciones
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", 
                    "/index", 
                    "/auth/**", 
                    "/css/**", 
                    "/js/**", 
                    "/script/**", 
                    "/img/**",
                    "favicon.*"
                ).permitAll()
                .anyRequest().authenticated()
            )

            // Login personalizado
            .formLogin(form -> form
                .loginPage("/auth/iniciar-sesion")       // Página de login personalizada
                .loginProcessingUrl("/auth/iniciar-sesion") // URL que procesa el POST del formulario
                .defaultSuccessUrl("/", true)            // Si el login es correcto, redirige al index
                .failureUrl("/auth/iniciar-sesion?error=true") // Si falla, muestra mensaje de error
                .permitAll()
            )

            // Logout personalizado
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")       // Redirige al index con mensaje de logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    // PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager (para AuthController si lo necesitás)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}