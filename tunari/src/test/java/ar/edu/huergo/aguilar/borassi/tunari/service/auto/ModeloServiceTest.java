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

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.ModeloRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - ModeloService")
class ModeloServiceTest {

    @Mock
    private ModeloRepository modeloRepository;

    @InjectMocks
    private ModeloService modeloService;
    
    @Mock
    private VersionService versionService;

    @Mock
    private MarcaService marcaService;

    @Mock
    private ColorService colorService;

    private Modelo modeloEjemplo;

    @BeforeEach
    void setUp() {
        modeloEjemplo = new Modelo();
        modeloEjemplo.setId(1L);
        modeloEjemplo.setNombre("Bronco Sport");
    }

    @Test
    @DisplayName("Debería obtener todos los modelos")
    void deberiaObtenerTodosLosModelos() {
        // Given
        List<Modelo> modelosEsperados = Arrays.asList(modeloEjemplo);
        when(modeloRepository.findAll()).thenReturn(modelosEsperados);

        // When
        List<Modelo> resultado = modeloService.obtenerTodosLosModelos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Bronco Sport", resultado.get(0).getNombre());
        verify(modeloRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener modelo por ID cuando existe")
    void deberiaObtenerModeloPorId() {
        // Given
        Long id = 1L;
        when(modeloRepository.findById(id)).thenReturn(Optional.of(modeloEjemplo));

        // When
        Modelo resultado = modeloService.obtenerModeloPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Bronco Sport", resultado.getNombre());
        verify(modeloRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando el modelo no existe")
    void deberiaLanzarExcepcionCuandoNoExiste() {
        // Given
        Long idInexistente = 999L;
        when(modeloRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> modeloService.obtenerModeloPorId(idInexistente));

        assertEquals("Modelo no encontrado.", ex.getMessage());
        verify(modeloRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debería crear un modelo a partir del DTO")
    void deberiaCrearModelo() {
        // Given
        CrearModeloDTO dto = new CrearModeloDTO("Bronco Sport 2025", 1L, List.of(2L, 3L), List.of(4L, 1000L) );
        Modelo guardado = new Modelo();
        guardado.setId(10L);
        guardado.setNombre("Bronco Sport");

        when(modeloRepository.save(any(Modelo.class))).thenReturn(guardado);

        // When
        Modelo resultado = modeloService.crearModelo(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Bronco Sport", resultado.getNombre());
        verify(modeloRepository, times(1)).save(any(Modelo.class));
    }

    @Test
    @DisplayName("Debería actualizar el nombre de un modelo existente")
    void deberiaActualizarModelo() {
        // Given
        Long id = 1L;
        when(modeloRepository.findById(id)).thenReturn(Optional.of(modeloEjemplo));

        Modelo devueltoPorSave = new Modelo();
        devueltoPorSave.setId(1L);
        devueltoPorSave.setNombre("Bronco Sport 2025");
        when(modeloRepository.save(any(Modelo.class))).thenReturn(devueltoPorSave);

        CrearModeloDTO dto = new CrearModeloDTO("Bronco Sport 2025", 1L, List.of(2L, 3L), List.of(4L, 1000L) );
        
        // When
        Modelo resultado = modeloService.actualizarModelo(id, dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Bronco Sport 2025", resultado.getNombre());
        // también chequeamos que en la entidad cargada se haya seteado el nombre
        assertEquals("Bronco Sport 2025", modeloEjemplo.getNombre());
        verify(modeloRepository, times(1)).findById(id);
        verify(modeloRepository, times(1)).save(modeloEjemplo);
    }

    @Test
    @DisplayName("Debería eliminar un modelo existente")
    void deberiaEliminarModelo() {
        // Given
        Long id = 1L;
        when(modeloRepository.findById(id)).thenReturn(Optional.of(modeloEjemplo));
        doNothing().when(modeloRepository).delete(modeloEjemplo);

        // When
        assertDoesNotThrow(() -> modeloService.eliminarModelo(id));

        // Then
        verify(modeloRepository, times(1)).findById(id);
        verify(modeloRepository, times(1)).delete(modeloEjemplo);
    }
}
