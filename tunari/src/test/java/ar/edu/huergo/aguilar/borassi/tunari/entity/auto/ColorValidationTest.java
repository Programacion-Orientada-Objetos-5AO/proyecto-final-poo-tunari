package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Color")
class ColorValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar que Color correcto sin errores")
    void deberiValidarColorCorrectoSinErrores() {
        // Given
        Color color = new Color();
        color.setNombre("Verde selva");

        // When
        Set<ConstraintViolation<Color>> violaciones = validator.validate(color);

        // Then
        assertTrue(violaciones.isEmpty(),
            "No debería haber violaciones para un Color válido");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        // Given
        Color color = new Color();
        color.setNombre(null);

        // When
        Set<ConstraintViolation<Color>> violaciones = validator.validate(color);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreColor")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("no puede estar vacío")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        // Given
        Color color = new Color();
        color.setNombre("");

        // When
        Set<ConstraintViolation<Color>> violaciones = validator.validate(color);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreColor")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        // Given
        Color color = new Color();
        color.setNombre("   ");

        // When
        Set<ConstraintViolation<Color>> violaciones = validator.validate(color);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreColor")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Verde selva", "Celeste cielo", "Amarillo abeja", "Gris elefante"
    })
    @DisplayName("Debería aceptar nombres de colores comunes")
    void deberiaAceptarNombresDeColoresComunes(String nombreValido) {
        // Given
        Color color = new Color();
        color.setNombre(nombreValido);

        // When
        Set<ConstraintViolation<Color>> violaciones = validator.validate(color);

        // Then
        assertTrue(violaciones.isEmpty(),
            "El nombre '" + nombreValido + "' debería ser válido");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy corto")
    void deberiaFallarValidacionConNombreCorto() {
        Color color = new Color();
        color.setNombre("V");

        Set<ConstraintViolation<Color>> violaciones = validator.validate(color);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreColor")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreLargo() {
        Color color = new Color();
        color.setNombre("V".repeat(101));

        Set<ConstraintViolation<Color>> violaciones = validator.validate(color);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreColor")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido")
    void deberiaAceptarLimitesConSize() {
        Color c1 = new Color();
        c1.setNombre("VE"); // 2
        Color c2 = new Color();
        c2.setNombre("V".repeat(100)); // 100

        assertTrue(validator.validate(c1).isEmpty());
        assertTrue(validator.validate(c2).isEmpty());
    }
    
}
