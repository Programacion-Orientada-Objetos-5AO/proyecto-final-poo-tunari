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
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;

@DataJpaTest
@DisplayName("Tests de Integración - ModeloRepository")
class ModeloRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ModeloRepository modeloRepository;

    private Modelo modelo1;
    private Modelo modelo2;
    private Modelo modelo3;

    @BeforeEach
    void setUp() {

        // Crear modelos de prueba
        modelo1 = new Modelo();
        modelo1.setNombre("Ford Bronco Sport");
        modelo1 = entityManager.persistAndFlush(modelo1);

        modelo2 = new Modelo();
        modelo2.setNombre("Volkswagen Virtus");
        modelo2 = entityManager.persistAndFlush(modelo2);

        modelo3 = new Modelo();
        modelo3.setNombre("Volkswagen Taos");
        modelo3 = entityManager.persistAndFlush(modelo3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar modelos por nombre conteniendo texto (case insensitive)")
    void deberiaEncontrarModelosPorNombreContaining() {
        // When - Buscar modelos que sean Ford Bronco Sport
        List<Modelo> modelosEncontrados =
                modeloRepository.findByNombreContainingIgnoreCase("Ford Bronco Sport");

        // Then
        assertNotNull(modelosEncontrados);
        assertEquals(1, modelosEncontrados.size());

        List<String> nombresModelos =
                modelosEncontrados.stream().map(Modelo::getNombre).toList();
        assertTrue(nombresModelos.contains("Ford Bronco Sport"));
    }

    @Test
    @DisplayName("Debería encontrar modelos con búsqueda case insensitive")
    void deberiaEncontrarModelosCaseInsensitive() {
        // When - Buscar con diferentes casos
        List<Modelo> resultadoMinuscula =
                modeloRepository.findByNombreContainingIgnoreCase("FORD BRONCO SPORT");
        List<Modelo> resultadoMayuscula =
                modeloRepository.findByNombreContainingIgnoreCase("ford bronco sport");
        List<Modelo> resultadoMixto =
                modeloRepository.findByNombreContainingIgnoreCase("FoRD BrONco SPOrt");

        // Then - Todos deberían dar el mismo resultado
        assertEquals(1, resultadoMinuscula.size());
        assertEquals(1, resultadoMayuscula.size());
        assertEquals(1, resultadoMixto.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no encuentra coincidencias")
    void deberiaRetornarListaVaciaSinCoincidencias() {
        // When
        List<Modelo> modelosEncontrados =
                modeloRepository.findByNombreContainingIgnoreCase("Chevrolet Cruze");

        // Then
        assertNotNull(modelosEncontrados);
        assertTrue(modelosEncontrados.isEmpty());
    }

    @Test
    @DisplayName("Debería encontrar modelo con búsqueda parcial")
    void deberiaEncontrarModelosConBusquedaParcial() {
        // When - Buscar solo parte del nombre
        List<Modelo> primerResultado =
                modeloRepository.findByNombreContainingIgnoreCase("tr");
        List<Modelo> segundoResultado =
                modeloRepository.findByNombreContainingIgnoreCase("Vo");

        // Then
        assertEquals(1, primerResultado.size());
        assertEquals("Ford Bronco Sport", primerResultado.get(0).getNombre());

        assertEquals(1, segundoResultado.size());
        assertEquals("Volskwagen Virtus", segundoResultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debería guardar y recuperar modelo correctamente")
    void deberiaGuardarYRecuperarModelo() {
        // Given
        Modelo nuevoModelo = new Modelo();
        nuevoModelo.setNombre("Audi S3");

        // When
        Modelo modeloGuardado = modeloRepository.save(nuevoModelo);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(modeloGuardado.getId());

        Optional<Modelo> modeloRecuperado =
                modeloRepository.findById(modeloGuardado.getId());

        assertTrue(modeloRecuperado.isPresent());
        assertEquals("Audi S3", modeloRecuperado.get().getNombre());
    }

    @Test
    @DisplayName("Debería actualizar modelo existente")
    void deberiaActualizarModeloExistente() {
        // Given
        String nuevoNombreModelo = "Ford Territory";

        // When
        Optional<Modelo> modeloOptional =
                modeloRepository.findById(modelo1.getId());
        assertTrue(modeloOptional.isPresent());

        Modelo modelo = modeloOptional.get();
        modelo.setNombre(nuevoNombreModelo);

        Modelo modeloActualizado = modeloRepository.save(modelo);
        entityManager.flush();

        // Then
        assertEquals(nuevoNombreModelo, modeloActualizado.getNombre());

        // Verificar persistencia
        entityManager.clear();
        Optional<Modelo> verificarModelo =
                modeloRepository.findById(modelo1.getId());
        assertTrue(verificarModelo.isPresent());
        assertEquals(nuevoNombreModelo, verificarModelo.get().getNombre());
    }

    @Test
    @DisplayName("Debería eliminar modelo correctamente")
    void deberiaEliminarModelo() {
        // Given
        Long modeloId = modelo1.getId();
        assertTrue(modeloRepository.existsById(modeloId));

        // When
        modeloRepository.deleteById(modeloId);
        entityManager.flush();

        // Then
        assertFalse(modeloRepository.existsById(modeloId));
        Optional<Modelo> modeloEliminado = modeloRepository.findById(modeloId);
        assertFalse(modeloEliminado.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todos los modelos")
    void deberiaEncontrarTodosLosModelos() {
        // When
        List<Modelo> todosLosModelos = modeloRepository.findAll();

        // Then
        assertNotNull(todosLosModelos);
        assertEquals(3, todosLosModelos.size());

        List<String> nombresModelosEncotrados = todosLosModelos.stream().map(Modelo::getNombre).toList();
        assertTrue(nombresModelosEncotrados.contains("Ford Bronco Sport"));
        assertTrue(nombresModelosEncotrados.contains("Volkswagen Virtus"));
        assertTrue(nombresModelosEncotrados.contains("Audi S3"));
    }

    @Test
    @DisplayName("Debería contar modelos correctamente")
    void deberiaContarModelos() {
        // When
        long cantidadModelos = modeloRepository.count();

        // Then
        assertEquals(3, cantidadModelos);

        // Agregar un ingrediente más y verificar
        Modelo nuevoModelo = new Modelo();
        nuevoModelo.setNombre("BMW M3");
        entityManager.persistAndFlush(nuevoModelo);

        assertEquals(4, modeloRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear ingrediente con nombre vacío
        Modelo modeloInvalido = new Modelo();
        modeloInvalido.setNombre(""); // Viola @NotBlank

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(modeloInvalido);
        });
    }

    @Test
    @DisplayName("Debería manejar nombres con espacios en la búsqueda")
    void deberiaManejarNombresConEspacios() {
        // When - Buscar parte del nombre que incluye espacios
        List<Modelo> resultadoModelo =
                modeloRepository.findByNombreContainingIgnoreCase("Ford B");

        // Then
        assertEquals(1, resultadoModelo.size());
        assertEquals("Ford Bronco Sport", resultadoModelo.get(0).getNombre());
    }
}
