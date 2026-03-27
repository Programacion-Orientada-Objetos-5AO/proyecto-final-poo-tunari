package ar.edu.huergo.aguilar.borassi.tunari.entity.agencia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("Tests de Validación - Entidad Agencia")
class AgenciaValidationTest {

    private Validator validator;
    private Marca marcaBase;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        marcaBase = new Marca();
        marcaBase.setNombre("Ford");
    }

    @Test
    @DisplayName("Debería validar correctamente una agencia válida")
    void deberiaValidarAgenciaValida() {
        Agencia agencia = new Agencia("Tunari Motors", "Av. Siempre Viva 123", marcaBase);

        Set<ConstraintViolation<Agencia>> violaciones = validator.validate(agencia);

        assertTrue(violaciones.isEmpty(), "Una agencia válida no debe tener errores de validación");
    }

    @Test
    @DisplayName("Debería fallar si el nombre es nulo")
    void deberiaFallarSiNombreEsNulo() {
        Agencia agencia = new Agencia(null, "Av. Siempre Viva 123", marcaBase);

        Set<ConstraintViolation<Agencia>> violaciones = validator.validate(agencia);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar si la ubicación es nula")
    void deberiaFallarSiUbicacionEsNula() {
        Agencia agencia = new Agencia("Tunari Motors", null, marcaBase);

        Set<ConstraintViolation<Agencia>> violaciones = validator.validate(agencia);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("ubicacion")));
    }

    @Test
    @DisplayName("Debería fallar si la marca es nula")
    void deberiaFallarSiMarcaEsNula() {
        Agencia agencia = new Agencia("Tunari Motors", "Av. Siempre Viva 123", null);

        Set<ConstraintViolation<Agencia>> violaciones = validator.validate(agencia);

        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("marca")));
    }
}
