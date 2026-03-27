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
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;

@DataJpaTest
@DisplayName("Tests de Integración - ColorRepository")
class ColorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ColorRepository colorRepository;

    private Color color1;
    private Color color2;
    private Color color3;

    @BeforeEach
    void setUp() {

        // Crear marcas de prueba
        color1 = new Color();
        color1.setNombre("Verde selva");
        color1 = entityManager.persistAndFlush(color1);

        color2 = new Color();
        color2.setNombre("Celeste cielo");
        color2 = entityManager.persistAndFlush(color2);

        color3 = new Color();
        color3.setNombre("Amarillo abeja");
        color3 = entityManager.persistAndFlush(color3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar colores por nombre conteniendo texto (case insensitive)")
    void deberiaEncontrarColorPorNombreContaining() {
        // When - Buscar colores que sean Verde Selva
        List<Color> coloresEncontrados =
                colorRepository.findByNombreContainingIgnoreCase("Verde selva");

        // Then
        assertNotNull(coloresEncontrados);
        assertEquals(1, coloresEncontrados.size());

        List<String> nombresColores =
                coloresEncontrados.stream().map(Color::getNombre).toList();
        assertTrue(nombresColores.contains("Verde selva"));
    }

    @Test
    @DisplayName("Debería encontrar colores con búsqueda case insensitive")
    void deberiaEncontrarColorCaseInsensitive() {
        // When - Buscar con diferentes casos
        List<Color> resultadoMinuscula =
                colorRepository.findByNombreContainingIgnoreCase("VERDE SELVA");
        List<Color> resultadoMayuscula =
                colorRepository.findByNombreContainingIgnoreCase("verde selva");
        List<Color> resultadoMixto =
                colorRepository.findByNombreContainingIgnoreCase("VeRDe SelVa");

        // Then - Todos deberían dar el mismo resultado
        assertEquals(1, resultadoMinuscula.size());
        assertEquals(1, resultadoMayuscula.size());
        assertEquals(1, resultadoMixto.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no encuentra coincidencias")
    void deberiaRetornarListaVaciaSinCoincidencias() {
        // When
        List<Color> coloresEncontrados =
                colorRepository.findByNombreContainingIgnoreCase("Rosa pastel");

        // Then
        assertNotNull(coloresEncontrados);
        assertTrue(coloresEncontrados.isEmpty());
    }

    @Test
    @DisplayName("Debería encontrar colores con búsqueda parcial")
    void deberiaEncontrarColoresConBusquedaParcial() {
        // When - Buscar solo parte del nombre
        List<Color> primerResultado =
                colorRepository.findByNombreContainingIgnoreCase("Ve");
        List<Color> segundoResultado =
                colorRepository.findByNombreContainingIgnoreCase("Verd");

        // Then
        assertEquals(1, primerResultado.size());
        assertEquals("Verde selva", primerResultado.get(0).getNombre());

        assertEquals(1, segundoResultado.size());
        assertEquals("Verde selva", segundoResultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Debería guardar y recuperar marca correctamente")
    void deberiaGuardarYRecuperarColor() {
        // Given
        Color nuevoColor = new Color();
        nuevoColor.setNombre("Azul marino");

        // When
        Color colorGuardado = colorRepository.save(nuevoColor);
        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotNull(colorGuardado.getId());

        Optional<Color> colorRecuperado =
                colorRepository.findById(colorGuardado.getId());

        assertTrue(colorRecuperado.isPresent());
        assertEquals("Azul marino", colorRecuperado.get().getNombre());
    }

    @Test
    @DisplayName("Debería actualizar un color existente")
    void deberiaActualizarColorExistente() {
        // Given
        String nuevoNombreColor = "Negro noche";

        // When
        Optional<Color> colorOptional =
                colorRepository.findById(color1.getId());
        assertTrue(colorOptional.isPresent());

        Color color = colorOptional.get();
        color.setNombre(nuevoNombreColor);

        Color colorActualizado = colorRepository.save(color);
        entityManager.flush();

        // Then
        assertEquals(nuevoNombreColor, colorActualizado.getNombre());

        // Verificar persistencia
        entityManager.clear();
        Optional<Color> verificarColor =
                colorRepository.findById(color1.getId());
        assertTrue(verificarColor.isPresent());
        assertEquals(nuevoNombreColor, verificarColor.get().getNombre());
    }

    @Test
    @DisplayName("Debería eliminar un color correctamente")
    void deberiaEliminarColor() {
        // Given
        Long colorId = color1.getId();
        assertTrue(colorRepository.existsById(colorId));

        // When
        colorRepository.deleteById(colorId);
        entityManager.flush();

        // Then
        assertFalse(colorRepository.existsById(colorId));
        Optional<Color> colorEliminado = colorRepository.findById(colorId);
        assertFalse(colorEliminado.isPresent());
    }

    @Test
    @DisplayName("Debería encontrar todos los colores")
    void deberiaEncontrarTodosLosColores() {
        // When
        List<Color> todosLosColores = colorRepository.findAll();

        // Then
        assertNotNull(todosLosColores);
        assertEquals(3, todosLosColores.size());

        List<String> nombresColoresEncontrados = todosLosColores.stream().map(Color::getNombre).toList();
        assertTrue(nombresColoresEncontrados.contains("Verde selva"));
        assertTrue(nombresColoresEncontrados.contains("Amarillo abeja"));
        assertTrue(nombresColoresEncontrados.contains("Celeste cielo"));
    }

    @Test
    @DisplayName("Debería contar colores correctamente")
    void deberiaContarColores() {
        // When
        long cantidadColores = colorRepository.count();

        // Then
        assertEquals(3, cantidadColores);

        // Agregar un ingrediente más y verificar
        Color nuevoColor = new Color();
        nuevoColor.setNombre("Blanco perlado");
        entityManager.persistAndFlush(nuevoColor);

        assertEquals(4, colorRepository.count());
    }

    @Test
    @DisplayName("Debería validar restricciones de la entidad")
    void deberiaValidarRestricciones() {
        // Given - Crear ingrediente con nombre vacío
        Color colorInvalido = new Color();
        colorInvalido.setNombre(""); // Viola @NotBlank

        // When & Then
        assertThrows(Exception.class, () -> {
            entityManager.persistAndFlush(colorInvalido);
        });
    }

    @Test
    @DisplayName("Debería manejar nombres con espacios en la búsqueda")
    void deberiaManejarNombresConEspacios() {
        // When - Buscar parte del nombre que incluye espacios
        List<Color> resultadoColor =
                colorRepository.findByNombreContainingIgnoreCase("Verd");

        // Then
        assertEquals(1, resultadoColor.size());
        assertEquals("Verde selva", resultadoColor.get(0).getNombre());
    }
}
