package ar.edu.huergo.aguilar.borassi.tunari.repository.vehiculo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.*;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.*;

@DataJpaTest
@DisplayName("Tests de Integración - VehiculoRepository (con herencia PickUp)")
class VehiculoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    private Marca marca;
    private Modelo modelo;
    private Color color;
    private Version version;

    @BeforeEach
    void setUp() {
        marca = new Marca();
        marca.setNombre("Ford");
        marca = entityManager.persistAndFlush(marca);

        modelo = new Modelo();
        modelo.setNombre("Ranger");
        modelo.setMarca(marca);
        modelo = entityManager.persistAndFlush(modelo);

        color = new Color();
        color.setNombre("Negro");
        color.setMarca(marca); 
        color = entityManager.persistAndFlush(color);

        version = new Version();
        version.setNombre("XLT 3.2");
        version.setMarca(marca);
        version = entityManager.persistAndFlush(version);

        entityManager.clear();

    }

    @Test
    @DisplayName("Debería guardar y recuperar un Vehiculo base correctamente")
    void deberiaGuardarYRecuperarVehiculo() {

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setColor(color);
        vehiculo.setVersion(version);

        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        entityManager.flush();
        entityManager.clear();

        Optional<Vehiculo> recuperado = vehiculoRepository.findById(guardado.getId());
        assertTrue(recuperado.isPresent());
        assertEquals("Ranger", recuperado.get().getModelo().getNombre());
        assertTrue(recuperado.get() instanceof Vehiculo);
    }

    @Test
    @DisplayName("Debería guardar y recuperar una PickUp correctamente")
    void deberiaGuardarYRecuperarPickUp() {
        PickUp pickUp = new PickUp();
        pickUp.setMarca(marca);
        pickUp.setModelo(modelo);
        pickUp.setColor(color);
        pickUp.setVersion(version);
        pickUp.setPesoBaul(450);

        PickUp guardada = entityManager.persistAndFlush(pickUp);
        entityManager.clear();

        Vehiculo recuperado = entityManager.find(Vehiculo.class, guardada.getId());
        assertNotNull(recuperado);
        assertTrue(recuperado instanceof PickUp);
        assertEquals(450, ((PickUp) recuperado).getPesoBaul());
    }

    @Test
    @DisplayName("Debería listar todos los vehículos (Vehiculo + PickUp)")
    void deberiaListarTodosLosVehiculos() {
        Vehiculo autoBase = new Vehiculo();
        autoBase.setMarca(marca);
        autoBase.setModelo(modelo);
        autoBase.setColor(color);
        autoBase.setVersion(version);
        PickUp pickUp = new PickUp();
        pickUp.setMarca(marca);
        pickUp.setModelo(modelo);
        pickUp.setColor(color);
        pickUp.setVersion(version);
        pickUp.setPesoBaul(500);

        entityManager.persist(autoBase);
        entityManager.persist(pickUp);
        entityManager.flush();
        entityManager.clear();

        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        assertEquals(2, vehiculos.size());
        assertTrue(vehiculos.stream().anyMatch(v -> v instanceof PickUp));
        assertTrue(vehiculos.stream().anyMatch(v -> !(v instanceof PickUp)));
    }

    @Test
    @DisplayName("Debería lanzar excepción al guardar Vehiculo sin marca")
    void deberiaFallarSiFaltaMarca() {
        Vehiculo invalido = new Vehiculo();
        invalido.setModelo(modelo);
        invalido.setColor(color);
        invalido.setVersion(version);

        assertThrows(Exception.class, () -> entityManager.persistAndFlush(invalido));
    }
}
