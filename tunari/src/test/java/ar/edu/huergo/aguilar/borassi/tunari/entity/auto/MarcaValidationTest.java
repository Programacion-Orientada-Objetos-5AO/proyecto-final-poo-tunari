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

@DisplayName("Tests de Validación - Entidad Marca")
class MarcaValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debería validar Marca correcta sin errores")
    void deberiaValidarMarcaCorrectaSinErrores() {
        // Given
        Marca marca = new Marca();
        marca.setNombreMarca("Toyota");

        // When
        Set<ConstraintViolation<Marca>> violaciones = validator.validate(marca);

        // Then
        assertTrue(violaciones.isEmpty(),
            "No debería haber violaciones para una Marca válida");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarValidacionConNombreNull() {
        // Given
        Marca marca = new Marca();
        marca.setNombreMarca(null);

        // When
        Set<ConstraintViolation<Marca>> violaciones = validator.validate(marca);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreMarca")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("no puede estar vacío")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarValidacionConNombreVacio() {
        // Given
        Marca marca = new Marca();
        marca.setNombreMarca("");

        // When
        Set<ConstraintViolation<Marca>> violaciones = validator.validate(marca);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreMarca")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarValidacionConNombreSoloEspacios() {
        // Given
        Marca marca = new Marca();
        marca.setNombreMarca("   ");

        // When
        Set<ConstraintViolation<Marca>> violaciones = validator.validate(marca);

        // Then
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreMarca")));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Ford", "Honda", "Chevrolet", "Mercedes-Benz", "Peugeot", "Renault", "Volkswagen"
    })
    @DisplayName("Debería aceptar nombres de marcas comunes")
    void deberiaAceptarNombresDeMarcasComunes(String nombreValido) {
        // Given
        Marca marca = new Marca();
        marca.setNombreMarca(nombreValido);

        // When
        Set<ConstraintViolation<Marca>> violaciones = validator.validate(marca);

        // Then
        assertTrue(violaciones.isEmpty(),
            "El nombre '" + nombreValido + "' debería ser válido");
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy corto")
    void deberiaFallarValidacionConNombreCorto() {
        Marca marca = new Marca();
        marca.setNombreMarca("A");

        Set<ConstraintViolation<Marca>> violaciones = validator.validate(marca);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreMarca")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo")
    void deberiaFallarValidacionConNombreLargo() {
        Marca marca = new Marca();
        marca.setNombreMarca("A".repeat(101));

        Set<ConstraintViolation<Marca>> violaciones = validator.validate(marca);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("nombreMarca")));
        assertTrue(violaciones.stream()
            .anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido")
    void deberiaAceptarLimitesConSize() {
        Marca m1 = new Marca();
        m1.setNombreMarca("AB"); // 2
        Marca m2 = new Marca();
        m2.setNombreMarca("A".repeat(100)); // 100

        assertTrue(validator.validate(m1).isEmpty());
        assertTrue(validator.validate(m2).isEmpty());
    }
    
}
