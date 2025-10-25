package ar.edu.huergo.aguilar.borassi.tunari.entity.pickup;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.*;

class PickUpValidatorTest {

    @Test
    @DisplayName("PickUp debería heredar correctamente de Vehiculo")
    void deberiaHeredarDeVehiculo() {
        PickUp pickUp = new PickUp();
        pickUp.setPesoBaul(450);

        // Herencia
        assertTrue(pickUp instanceof Vehiculo, "PickUp debe ser una subclase de Vehiculo");

        // Campo propio
        assertEquals(450, pickUp.getPesoBaul(), "El peso del baúl debe coincidir");
    }

    @Test
    @DisplayName("PickUp debería poder configurarse igual que un Vehiculo")
    void deberiaComportarseComoVehiculo() {
        Marca marca = new Marca();
        marca.setId(1L);
        marca.setNombre("Ford");

        Modelo modelo = new Modelo();
        modelo.setId(2L);
        modelo.setNombre("Ranger");
        modelo.setMarca(marca);

        Color color = new Color();
        color.setId(3L);
        color.setNombre("Negro");
        color.setMarca(marca);

        Version version = new Version();
        version.setId(4L);
        version.setNombre("XLT 3.2");
        version.setMarca(marca);

        PickUp pickUp = new PickUp();
        pickUp.setMarca(marca);
        pickUp.setModelo(modelo);
        pickUp.setColor(color);
        pickUp.setVersion(version);
        pickUp.setPesoBaul(500);

        // Verificación general
        assertEquals("Ford", pickUp.getMarca().getNombre());
        assertEquals("Ranger", pickUp.getModelo().getNombre());
        assertEquals("Negro", pickUp.getColor().getNombre());
        assertEquals("XLT 3.2", pickUp.getVersion().getNombre());
        assertEquals(500, pickUp.getPesoBaul());
    }

    @Test
    @DisplayName("PickUp debería ser distinta si el peso del baúl cambia")
    void deberiaSerDistintaSiPesoBaulCambia() {
        PickUp p1 = new PickUp();
        p1.setPesoBaul(400);

        PickUp p2 = new PickUp();
        p2.setPesoBaul(600);

        assertNotEquals(p1.getPesoBaul(), p2.getPesoBaul(),
                "Dos PickUp con distinto peso de baúl deben diferir en valor");
    }
}
