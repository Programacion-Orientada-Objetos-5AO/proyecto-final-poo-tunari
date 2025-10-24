package ar.edu.huergo.aguilar.borassi.tunari.repository.agencia;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;

@DataJpaTest
@DisplayName("Tests de Integración - AgenciaRepository")
class AgenciaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AgenciaRepository agenciaRepository;

    private Agencia agencia1;
    private Agencia agencia2;

    @BeforeEach
    void setUp() {
        Marca marca = new Marca();
        marca.setNombre("Ford");
        marca = entityManager.persistAndFlush(marca);

        agencia1 = new Agencia();
        agencia1.setNombre("Tunari Motors");
        agencia1.setUbicacion("Av. Siempre Viva 123");
        agencia1.setMarca(marca);
        entityManager.persistAndFlush(agencia1);

        agencia2 = new Agencia();
        agencia2.setNombre("Tunari Center");
        agencia2.setUbicacion("Av. Rivadavia 456");
        agencia2.setMarca(marca);
        entityManager.persistAndFlush(agencia2);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería guardar y recuperar una agencia correctamente")
    void deberiaGuardarYRecuperarAgencia() {
        // Given
        Marca marca = new Marca();
        marca.setNombre("Peugeot");
        marca = entityManager.persistAndFlush(marca);

        Agencia nueva = new Agencia();
        nueva.setNombre("Tunari Norte");
        nueva.setUbicacion("Av. Libertador 999");
        nueva.setMarca(marca); // ✅ asignar marca antes de guardar

        // When
        Agencia guardada = agenciaRepository.save(nueva);
        entityManager.flush();
        entityManager.clear();

        // Then
        Optional<Agencia> recuperada = agenciaRepository.findById(guardada.getId());
        assertTrue(recuperada.isPresent());
        assertEquals("Tunari Norte", recuperada.get().getNombre());
        assertEquals("Av. Libertador 999", recuperada.get().getUbicacion());
        assertEquals("Peugeot", recuperada.get().getMarca().getNombre()); // opcional, valida la relación
    }

    @Test
    @DisplayName("Debería encontrar agencias por nombre (case insensitive)")
    void deberiaEncontrarPorNombre() {
        List<Agencia> resultado = agenciaRepository.findByNombreContainingIgnoreCase("motors");
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tunari Motors", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debería actualizar una agencia existente")
    void deberiaActualizarAgencia() {
        Agencia ag = agenciaRepository.findById(agencia1.getId()).orElseThrow();
        ag.setUbicacion("Av. San Martín 321");
        agenciaRepository.save(ag);
        entityManager.flush();

        entityManager.clear();
        Agencia actualizada = agenciaRepository.findById(agencia1.getId()).orElseThrow();
        assertEquals("Av. San Martín 321", actualizada.getUbicacion());
    }

    @Test
    @DisplayName("Debería eliminar una agencia correctamente")
    void deberiaEliminarAgencia() {
        Long id = agencia2.getId();
        agenciaRepository.deleteById(id);
        entityManager.flush();

        assertFalse(agenciaRepository.existsById(id));
    }

    @Test
    @DisplayName("Debería validar restricciones (nombre vacío)")
    void deberiaValidarRestricciones() {
        Agencia invalida = new Agencia();
        invalida.setNombre("");
        invalida.setUbicacion("Calle falsa 123");

    }
}
