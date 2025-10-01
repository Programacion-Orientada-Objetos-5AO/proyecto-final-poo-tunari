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
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;

@DataJpaTest
@DisplayName("Tests de Integración - MarcaRepository")
class MarcaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MarcaRepository marcaRepository;

    private Marca marca1;
    private Marca marca2;
    private Marca marca3;

    @BeforeEach
    void setUp() {

        // Crear marcas de prueba
        marca1 = new Marca();
        marca1.setNombreMarca("Ford");
        marca1 = entityManager.persistAndFlush(marca1);

        marca2 = new Marca();
        marca2.setNombreMarca("Chevrolet");
        marca2 = entityManager.persistAndFlush(marca2);

        marca3 = new Marca();
        marca3.setNombreMarca("Volkswagen");
        marca3 = entityManager.persistAndFlush(marca3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar marcas por nombre conteniendo texto (case insensitive)")
    void deberiaEncontrarMarcasPorNombreContaining() {
        // When - Buscar marcas que sean Ford
        List<Marca> marcasEncontradas =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("Ford");

        // Then
        assertNotNull(marcasEncontradas);
        assertEquals(1, marcasEncontradas.size());

        List<String> nombresMarcas =
                marcasEncontradas.stream().map(Marca::getNombreMarca).toList();
        assertTrue(nombresMarcas.contains("Ford"));
    }

    @Test
    @DisplayName("Debería encontrar marcas con búsqueda case insensitive")
    void deberiaEncontrarMarcasCaseInsensitive() {
        // When - Buscar con diferentes casos
        List<Marca> resultadoMinuscula =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("FORD");
        List<Marca> resultadoMayuscula =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("ford");
        List<Marca> resultadoMixto =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("FoRD");

        // Then - Todos deberían dar el mismo resultado
        assertEquals(1, resultadoMinuscula.size());
        assertEquals(1, resultadoMayuscula.size());
        assertEquals(1, resultadoMixto.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no encuentra coincidencias")
    void deberiaRetornarListaVaciaSinCoincidencias() {
        // When
        List<Marca> marcasEncontradas =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("Audi");

        // Then
        assertNotNull(marcasEncontradas);
        assertTrue(marcasEncontradas.isEmpty());
    }

    @Test
    @DisplayName("Debería encontrar marcas con búsqueda parcial")
    void deberiaEncontrarMarcasConBusquedaParcial() {
        // When - Buscar solo parte del nombre
        List<Marca> primerResultado =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("rd");
        List<Marca> segundoResultado =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("Fo");

        // Then
        assertEquals(1, primerResultado.size());
        assertEquals("Ford", primerResultado.get(0).getNombreMarca());

        assertEquals(1, segundoResultado.size());
        assertEquals("Ford", segundoResultado.get(0).getNombreMarca());
    }

    @Test
    @DisplayName("Debería guardar y recuperar marca correctamente")
    void deberiaGuardarYRecuperarMarca() {
        // Given
        Marca nuevaMarca = new Marca();
        nuevaMarca.setNombreMarca("Bentley");

        // When
        Marca marcaGuardada = marcaRepository.save(nuevaMarca);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(marcaGuardada.getId());

        Optional<Marca> marcaRecuperada =
                marcaRepository.findById(marcaGuardada.getId());

        assertTrue(marcaRecuperada.isPresent());
        assertEquals("Bentley", marcaRecuperada.get().getNombreMarca());
    }

    @Test
    @DisplayName("Debería actualizar marca existente")
    void deberiaActualizarMarcaExistente() {
        // Given
        String nuevoNombreMarca = "Mustang";

        // When
        Optional<Marca> marcaOptional =
                marcaRepository.findById(marca1.getId());
        assertTrue(marcaOptional.isPresent());

        Marca marca = marcaOptional.get();
        marca.setNombreMarca(nuevoNombreMarca);

        Marca marcaActualizada = marcaRepository.save(marca);
        entityManager.flush();

        // Then
        assertEquals(nuevoNombreMarca, marcaActualizada.getNombreMarca());

        // Verificar persistencia
        entityManager.clear();
        Optional<Marca> verificarMarca =
                marcaRepository.findById(marca1.getId());
        assertTrue(verificarMarca.isPresent());
        assertEquals(nuevoNombreMarca, verificarMarca.get().getNombreMarca());
    }

    @Test
    @DisplayName("Debería eliminar marca correctamente")
    void deberiaEliminarMarca() {
        // Given
        Long marcaId = marca1.getId();
        assertTrue(marcaRepository.existsById(marcaId));

        // When
        marcaRepository.deleteById(marcaId);
        entityManager.flush();

        // Then
        assertFalse(marcaRepository.existsById(marcaId));
        Optional<Marca> marcaElimianda = marcaRepository.findById(marcaId);
        assertFalse(marcaElimianda.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todas las marcas")
    void deberiaEncontrarTodasLasMarcas() {
        // When
        List<Marca> todasLasMarcas = marcaRepository.findAll();

        // Then
        assertNotNull(todasLasMarcas);
        assertEquals(3, todasLasMarcas.size());

        List<String> nombresMarcasEncontradas = todasLasMarcas.stream().map(Marca::getNombreMarca).toList();
        assertTrue(nombresMarcasEncontradas.contains("Mustang"));
        assertTrue(nombresMarcasEncontradas.contains("Chevrolet"));
        assertTrue(nombresMarcasEncontradas.contains("Bentley"));
    }

    @Test
    @DisplayName("Debería contar marcas correctamente")
    void deberiaContarMarcas() {
        // When
        long cantidadMarcas = marcaRepository.count();

        // Then
        assertEquals(3, cantidadMarcas);

        // Agregar un ingrediente más y verificar
        Marca nuevaMarca = new Marca();
        nuevaMarca.setNombreMarca("Ferrari");
        entityManager.persistAndFlush(nuevaMarca);

        assertEquals(4, marcaRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear ingrediente con nombre vacío
        Marca marcaInvalida = new Marca();
        marcaInvalida.setNombreMarca(""); // Viola @NotBlank

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(marcaInvalida);
        });
    }

    @Test
    @DisplayName("Debería manejar nombres con espacios en la búsqueda")
    void deberiaManejarNombresConEspacios() {
        // When - Buscar parte del nombre que incluye espacios
        List<Marca> resultadoMarca =
                marcaRepository.findByNombreMarcaContainingIgnoreCase("Chevro");

        // Then
        assertEquals(1, resultadoMarca.size());
        assertEquals("Chevrolet", resultadoMarca.get(0).getNombreMarca());
    }
}
