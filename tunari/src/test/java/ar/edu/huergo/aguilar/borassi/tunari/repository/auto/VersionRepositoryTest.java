package ar.edu.huergo.aguilar.borassi.tunari.repository.auto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;

@DataJpaTest
@DisplayName("Tests de Integración - VersionRepository")
class VersionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VersionRepository versionRepository;

    private Version version1;
    private Version version2;
    private Version version3;

    @BeforeEach
    void setUp() {

        // Crear marcas de prueba
        version1 = new Version();
        version1.setNombreVersion("Titanium");
        version1 = entityManager.persistAndFlush(version1);

        version2 = new Version();
        version2.setNombreVersion("Badlands");
        version2 = entityManager.persistAndFlush(version2);

        version3 = new Version();
        version3.setNombreVersion("Exclusive");
        version3 = entityManager.persistAndFlush(version3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar marcas por nombre conteniendo texto (case insensitive)")
    void deberiaEncontrarVersionesPorNombreContaining() {
        // When - Buscar marcas que sean Ford
        List<Version> versionesEncontradas =
                versionRepository.findByNombreVersionContainingIgnoreCase("Titanium");

        // Then
        assertNotNull(versionesEncontradas);
        assertEquals(1, versionesEncontradas.size());

        List<String> nombresVersiones =
                versionesEncontradas.stream().map(Version::getNombreVersion).toList();
        assertTrue(nombresVersiones.contains("Titanium"));
    }

    @Test
    @DisplayName("Debería encontrar versiones con búsqueda case insensitive")
    void deberiaEncontrarVersionCaseInsensitive() {
        // When - Buscar con diferentes casos
        List<Version> resultadoMinuscula =
                versionRepository.findByNombreVersionContainingIgnoreCase("TITANIUM");
        List<Version> resultadoMayuscula =
                versionRepository.findByNombreVersionContainingIgnoreCase("titanium");
        List<Version> resultadoMixto =
                versionRepository.findByNombreVersionContainingIgnoreCase("TiTaniuM");

        // Then - Todos deberían dar el mismo resultado
        assertEquals(1, resultadoMinuscula.size());
        assertEquals(1, resultadoMayuscula.size());
        assertEquals(1, resultadoMixto.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no encuentra coincidencias")
    void deberiaRetornarListaVaciaSinCoincidencias() {
        // When
        List<Version> versionesEncontradas =
                versionRepository.findByNombreVersionContainingIgnoreCase("GT Line");

        // Then
        assertNotNull(versionesEncontradas);
        assertTrue(versionesEncontradas.isEmpty());
    }

    @Test
    @DisplayName("Debería encontrar versiones con búsqueda parcial")
    void deberiaEncontrarVersionConBusquedaParcial() {
        // When - Buscar solo parte del nombre
        List<Version> primerResultado =
                versionRepository.findByNombreVersionContainingIgnoreCase("um");
        List<Version> segundoResultado =
                versionRepository.findByNombreVersionContainingIgnoreCase("Ti");

        // Then
        assertEquals(1, primerResultado.size());
        assertEquals("Titanium", primerResultado.get(0).getNombreVersion());

        assertEquals(1, segundoResultado.size());
        assertEquals("Titanium", segundoResultado.get(0).getNombreVersion());
    }

    @Test
    @DisplayName("Debería guardar y recuperar version correctamente")
    void deberiaGuardarYRecuperarVersion() {
        // Given
        Version nuevaVersion = new Version();
        nuevaVersion.setNombreVersion("Highline");

        // When
        Version versionGuardada = versionRepository.save(nuevaVersion);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(versionGuardada.getId());

        Optional<Version> versionRecuperada =
                versionRepository.findById(versionGuardada.getId());

        assertTrue(versionRecuperada.isPresent());
        assertEquals("Highline", versionRecuperada.get().getNombreVersion());
    }

    @Test
    @DisplayName("Debería actualizar version existente")
    void deberiaActualizarVersionExistente() {
        // Given
        String nuevoNombreVersion = "SEL";

        // When
        Optional<Version> versionOptional =
                versionRepository.findById(version1.getId());
        assertTrue(versionOptional.isPresent());

        Version version = versionOptional.get();
        version.setNombreVersion(nuevoNombreVersion);

        Version versionActualizada = versionRepository.save(version);
        entityManager.flush();

        // Then
        assertEquals(nuevoNombreVersion, versionActualizada.getNombreVersion());

        // Verificar persistencia
        entityManager.clear();
        Optional<Version> verificarVersion =
                versionRepository.findById(version1.getId());
        assertTrue(verificarVersion.isPresent());
        assertEquals(nuevoNombreVersion, verificarVersion.get().getNombreVersion());
    }

    @Test
    @DisplayName("Debería eliminar version correctamente")
    void deberiaEliminarVersion() {
        // Given
        Long versionId = version1.getId();
        assertTrue(versionRepository.existsById(versionId));

        // When
        versionRepository.deleteById(versionId);
        entityManager.flush();

        // Then
        assertFalse(versionRepository.existsById(versionId));
        Optional<Version> versionEliminada = versionRepository.findById(versionId);
        assertFalse(versionEliminada.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todas las versiones")
    void deberiaEncontrarTodasLasVersiones() {
        // When
        List<Version> todasLasVersiones = versionRepository.findAll();

        // Then
        assertNotNull(todasLasVersiones);
        assertEquals(3, todasLasVersiones.size());

        List<String> nombresMarcasEncontradas = todasLasVersiones.stream().map(Version::getNombreVersion).toList();
        assertTrue(nombresMarcasEncontradas.contains("Titanium"));
        assertTrue(nombresMarcasEncontradas.contains("Badlands"));
        assertTrue(nombresMarcasEncontradas.contains("Exclusive"));
    }

    @Test
    @DisplayName("Debería contar versiones correctamente")
    void deberiaContarVersiones() {
        // When
        long cantidadVersiones = versionRepository.count();

        // Then
        assertEquals(3, cantidadVersiones);

        // Agregar un ingrediente más y verificar
        Version nuevaVersion = new Version();
        nuevaVersion.setNombreVersion("LT");
        entityManager.persistAndFlush(nuevaVersion);

        assertEquals(4, versionRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear ingrediente con nombre vacío
        Version versionInvalida = new Version();
        versionInvalida.setNombreVersion(""); // Viola @NotBlank

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(versionInvalida);
        });
    }

    @Test
    @DisplayName("Debería manejar nombres con espacios en la búsqueda")
    void deberiaManejarNombresConEspacios() {
        // When - Buscar parte del nombre que incluye espacios
        List<Version> resultadoVersion =
                versionRepository.findByNombreVersionContainingIgnoreCase("Titan ");

        // Then
        assertEquals(1, resultadoVersion.size());
        assertEquals("Titanium", resultadoVersion.get(0).getNombreVersion());
    }
}
