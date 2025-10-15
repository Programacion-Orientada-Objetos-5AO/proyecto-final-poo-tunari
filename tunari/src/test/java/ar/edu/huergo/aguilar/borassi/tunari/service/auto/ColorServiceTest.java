package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.ColorRepository;
import jakarta.persistence.EntityNotFoundException;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - ColorService")
class ColorServiceTest {

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private MarcaService marcaService;

    @InjectMocks
    private ColorService colorService;

    private Color colorEjemplo;

    @BeforeEach
    void setUp() {
        colorEjemplo = new Color();
        colorEjemplo.setId(1L);
        colorEjemplo.setNombre("Gris titanio");
    }

    @Test
    @DisplayName("Debería obtener todos los colores")
    void deberiaObtenerTodosLosColores() {
        // Given
        List<Color> coloresEsperados = Arrays.asList(colorEjemplo);
        when(colorRepository.findAll()).thenReturn(coloresEsperados);

        // When
        List<Color> resultado = colorService.obtenerTodosLosColores();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Gris titanio", resultado.get(0).getNombre());
        verify(colorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener color por ID cuando existe")
    void deberiaObtenerColorPorId() {
        // Given
        Long id = 1L;
        when(colorRepository.findById(id)).thenReturn(Optional.of(colorEjemplo));

        // When
        Color resultado = colorService.obtenerColorPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Gris titanio", resultado.getNombre());
        verify(colorRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando el color no existe")
    void deberiaLanzarExcepcionCuandoNoExiste() {
        // Given
        Long idInexistente = 999L;
        when(colorRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> colorService.obtenerColorPorId(idInexistente));

        assertEquals("Color no encontrado", ex.getMessage());
        verify(colorRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debería crear un color a partir del DTO")
    void deberiaCrearColor() {
        // Given
        CrearColorDTO dto = new CrearColorDTO("Gris titanio", 1L);
        Color colorGuardado = new Color();
        colorGuardado.setId(10L);
        colorGuardado.setNombre("Gris topo");

        when(colorRepository.save(any(Color.class))).thenReturn(colorGuardado);

        // When
        Color resultado = colorService.crearColor(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Gris topo", resultado.getNombre());
        verify(colorRepository, times(1)).save(any(Color.class));
    }

    @Test
    @DisplayName("Debería actualizar el nombre de un color existente")
    void deberiaActualizarColor() {
        // Given
        Long id = 1L;
        when(colorRepository.findById(id)).thenReturn(Optional.of(colorEjemplo));

        Color devueltoPorSave = new Color();
        devueltoPorSave.setId(1L);
        devueltoPorSave.setNombre("Gris elefante actualizado");
        when(colorRepository.save(any(Color.class))).thenReturn(devueltoPorSave);

        CrearColorDTO dto = new CrearColorDTO("Gris elefante actualizado", 1L);

        // When
        Color resultado = colorService.actualizarColor(id, dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Gris elefante actualizado", resultado.getNombre());
        // también chequeamos que en la entidad cargada se haya seteado el nombre
        assertEquals("Gris elefante actualizado", colorEjemplo.getNombre());
        verify(colorRepository, times(1)).findById(id);
        verify(colorRepository, times(1)).save(colorEjemplo);
    }

    @Test
    @DisplayName("Debería eliminar un color existente")
    void deberiaEliminarColor() {
        // Given
        Long id = 1L;
        when(colorRepository.findById(id)).thenReturn(Optional.of(colorEjemplo));
        doNothing().when(colorRepository).delete(colorEjemplo);

        // When
        assertDoesNotThrow(() -> colorService.eliminarColor(id));

        // Then
        verify(colorRepository, times(1)).findById(id);
        verify(colorRepository, times(1)).delete(colorEjemplo);
    }
}
