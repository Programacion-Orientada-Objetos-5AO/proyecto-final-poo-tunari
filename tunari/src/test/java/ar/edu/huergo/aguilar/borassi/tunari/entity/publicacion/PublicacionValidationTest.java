package ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Publicacion")
class PublicacionValidationTest {

    private Validator validator;
    private Modelo modeloBase;
    private Usuario usuarioBase;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        modeloBase = new Modelo();
        modeloBase.setNombre("Ranger 2025");

        usuarioBase = new Usuario();
        usuarioBase.setUsername("usuario@tunari.com");
        usuarioBase.setPassword("123456");
    }

    @Test
    @DisplayName("Debería validar una publicación válida")
    void deberiaValidarPublicacionValida() {
        Publicacion publicacion = new Publicacion();
        publicacion.setModelo(modeloBase);
        publicacion.setUsuario(usuarioBase);

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);

        assertTrue(violaciones.isEmpty(), "No debería haber violaciones en una publicación válida");
    }

    @Test
    @DisplayName("Debería fallar si el modelo es nulo")
    void deberiaFallarSiModeloEsNulo() {
        Publicacion publicacion = new Publicacion();
        publicacion.setUsuario(usuarioBase);

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("modelo")));
    }

    @Test
    @DisplayName("Debería fallar si el usuario es nulo")
    void deberiaFallarSiUsuarioEsNulo() {
        Publicacion publicacion = new Publicacion();
        publicacion.setModelo(modeloBase);

        Set<ConstraintViolation<Publicacion>> violaciones = validator.validate(publicacion);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("usuario")));
    }
}
