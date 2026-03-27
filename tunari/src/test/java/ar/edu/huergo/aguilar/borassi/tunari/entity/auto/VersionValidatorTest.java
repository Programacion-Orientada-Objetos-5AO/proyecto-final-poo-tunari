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

@DisplayName("Tests de Validación - Entidad Version")
class VersionValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar que Version es correcto sin errores")
    void deberiValidarVersionCorrectoSinErrores() {
        // Given
        Version version = new Version();
        version.setNombre("Titanium");

        // When
        Set<ConstraintViolation<Version>> violaciones = validator.validate(version);

        // Then
        assertTrue(violaciones.isEmpty(),
            "No debería haber violaciones para una Versión válida");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        // Given
        Version version = new Version();
        version.setNombre(null);

        // When
        Set<ConstraintViolation<Version>> violaciones = validator.validate(version);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreVersion")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("no puede estar vacío")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        // Given
        Version version = new Version();
        version.setNombre("");

        // When
        Set<ConstraintViolation<Version>> violaciones = validator.validate(version);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreVersion")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        // Given
        Version version = new Version();
        version.setNombre("   ");

        // When
        Set<ConstraintViolation<Version>> violaciones = validator.validate(version);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreVersion")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Titanium", "Badlands", "LTZ", "Exclusive"
    })
    @DisplayName("Debería aceptar nombres de versiones comunes")
    void deberiaAceptarNombresDeVersionesComunes(String nombreValido) {
        // Given
        Version version = new Version();
        version.setNombre(nombreValido);

        // When
        Set<ConstraintViolation<Version>> violaciones = validator.validate(version);

        // Then
        assertTrue(violaciones.isEmpty(),
            "El nombre '" + nombreValido + "' debería ser válido");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy corto")
    void deberiaFallarValidacionConNombreCorto() {
        Version version = new Version();
        version.setNombre("T");

        Set<ConstraintViolation<Version>> violaciones = validator.validate(version);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreVersion")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreLargo() {
        Version version = new Version();
        version.setNombre("T".repeat(101));

        Set<ConstraintViolation<Version>> violaciones = validator.validate(version);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreVersion")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido")
    void deberiaAceptarLimitesConSize() {
        Version v1 = new Version();
        v1.setNombre("Ti"); // 2
        Version v2 = new Version();
        v2.setNombre("T".repeat(100)); // 100

        assertTrue(validator.validate(v1).isEmpty());
        assertTrue(validator.validate(v2).isEmpty());
    }
    
}
