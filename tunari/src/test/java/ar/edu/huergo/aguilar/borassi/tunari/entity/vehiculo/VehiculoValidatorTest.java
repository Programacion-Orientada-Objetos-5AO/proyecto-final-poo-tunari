package ar.edu.huergo.aguilar.borassi.tunari.entity.vehiculo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.*;

class VehiculoValidatorTest {

    @Test
    @DisplayName("Dos vehículos con mismo modelo, versión y color deben ser iguales")
    void deberianSerIgualesSiTienenMismoModeloVersionColor() {
        // Arrange
        Modelo modelo = new Modelo();
        modelo.setId(1L);

        Version version = new Version();
        version.setId(1L);

        Color color = new Color();
        color.setId(1L);

        Vehiculo v1 = new Vehiculo();
        v1.setModelo(modelo);
        v1.setVersion(version);
        v1.setColor(color);

        Vehiculo v2 = new Vehiculo();
        v2.setModelo(modelo);
        v2.setVersion(version);
        v2.setColor(color);

        // Act & Assert
        assertEquals(v1, v2, "Deberían considerarse iguales");
    }

    @Test
    @DisplayName("Dos vehículos con distinto color no deben ser iguales")
    void noDebenSerIgualesSiColorDifiere() {
        Modelo modelo = new Modelo();
        modelo.setId(1L);

        Version version = new Version();
        version.setId(1L);

        Color color1 = new Color();
        color1.setId(1L);

        Color color2 = new Color();
        color2.setId(2L);

        Vehiculo v1 = new Vehiculo();
        v1.setModelo(modelo);
        v1.setVersion(version);
        v1.setColor(color1);

        Vehiculo v2 = new Vehiculo();
        v2.setModelo(modelo);
        v2.setVersion(version);
        v2.setColor(color2);

        assertNotEquals(v1, v2, "Vehículos con distinto color no deben ser iguales");
    }

    @Test
    @DisplayName("PickUp debe heredar correctamente de Vehiculo")
    void pickupDebeExtenderVehiculo() {
        PickUp pickUp = new PickUp();
        pickUp.setPesoBaul(500);

        assertTrue(pickUp instanceof Vehiculo, "PickUp debe ser instancia de Vehiculo");
        assertEquals(500, pickUp.getPesoBaul());
    }

    @Test
    @DisplayName("Equals debe devolver false si se compara con null o con otro tipo")
    void equalsDebeManejarTiposInvalidos() {
        Vehiculo v1 = new Vehiculo();
        assertNotEquals(v1, null, "Vehiculo no debe ser igual a null");
        assertNotEquals(v1, "string", "Vehiculo no debe ser igual a otro tipo de objeto");
    }
}
