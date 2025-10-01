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

@DisplayName("Tests de Validación - Entidad Modelo")
class ModeloValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar que Modelo es correcto sin errores")
    void deberiValidarModleoCorrectoSinErrores() {
        // Given
        Modelo modelo = new Modelo();
        modelo.setNombreModelo("Ford Bronco Sport");

        // When
        Set<ConstraintViolation<Modelo>> violaciones = validator.validate(modelo);

        // Then
        assertTrue(violaciones.isEmpty(),
            "No debería haber violaciones para un Modelo válido");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        // Given
        Modelo modelo = new Modelo();
        modelo.setNombreModelo(null);

        // When
        Set<ConstraintViolation<Modelo>> violaciones = validator.validate(modelo);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreModelo")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("no puede estar vacío")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        // Given
        Modelo modelo = new Modelo();
        modelo.setNombreModelo("");

        // When
        Set<ConstraintViolation<Modelo>> violaciones = validator.validate(modelo);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreModelo")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        // Given
        Modelo modelo = new Modelo();
        modelo.setNombreModelo("   ");

        // When
        Set<ConstraintViolation<Modelo>> violaciones = validator.validate(modelo);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreModelo")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Ford Bronco Sport", "Volkswagen Virtus", "Ford Mustang", "Peugeot 3008"
    })
    @DisplayName("Debería aceptar nombres de colores comunes")
    void deberiaAceptarNombresDeColoresComunes(String nombreValido) {
        // Given
        Modelo modelo = new Modelo();
        modelo.setNombreModelo(nombreValido);

        // When
        Set<ConstraintViolation<Modelo>> violaciones = validator.validate(modelo);

        // Then
        assertTrue(violaciones.isEmpty(),
            "El nombre '" + nombreValido + "' debería ser válido");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy corto")
    void deberiaFallarValidacionConNombreCorto() {
        Modelo modelo = new Modelo();
        modelo.setNombreModelo("F");

        Set<ConstraintViolation<Modelo>> violaciones = validator.validate(modelo);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreModelo")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreLargo() {
        Modelo modelo = new Modelo();
        modelo.setNombreModelo("F".repeat(101));

        Set<ConstraintViolation<Modelo>> violaciones = validator.validate(modelo);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreModelo")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido")
    void deberiaAceptarLimitesConSize() {
        Modelo m1 = new Modelo();
        m1.setNombreModelo("FO"); // 2
        Modelo m2 = new Modelo();
        m2.setNombreModelo("F".repeat(100)); // 100

        assertTrue(validator.validate(m1).isEmpty());
        assertTrue(validator.validate(m2).isEmpty());
    }
    
}
