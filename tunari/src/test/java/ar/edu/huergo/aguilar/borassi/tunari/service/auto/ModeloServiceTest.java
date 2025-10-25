package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.ModeloRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - ModeloService")
class ModeloServiceTest {

    @Mock
    private ModeloRepository modeloRepository;

    @Mock
    private VersionService versionService;

    @Mock
    private MarcaService marcaService;

    @Mock
    private ColorService colorService;

    @InjectMocks
    private ModeloService modeloService;

    private Modelo modeloEjemplo;
    private Marca marcaEjemplo;
    private List<Long> coloresIds;
    private List<Long> versionesIds;

    @BeforeEach
    void setUp() {
        modeloEjemplo = new Modelo();
        modeloEjemplo.setId(1L);
        modeloEjemplo.setNombre("Bronco Sport");

        marcaEjemplo = new Marca();
        marcaEjemplo.setId(1L);
        marcaEjemplo.setNombre("Ford");

        coloresIds = List.of(10L, 11L);
        versionesIds = List.of(20L, 21L);
    }

    @Test
    @DisplayName("Debería obtener todos los modelos")
    void deberiaObtenerTodosLosModelos() {
        when(modeloRepository.findAll()).thenReturn(List.of(modeloEjemplo));

        List<Modelo> resultado = modeloService.obtenerTodosLosModelos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Bronco Sport", resultado.get(0).getNombre());
        verify(modeloRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener modelo por ID cuando existe")
    void deberiaObtenerModeloPorId() {
        Long id = 1L;
        when(modeloRepository.findById(id)).thenReturn(Optional.of(modeloEjemplo));

        Modelo resultado = modeloService.obtenerModeloPorId(id);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Bronco Sport", resultado.getNombre());
        verify(modeloRepository).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando el modelo no existe")
    void deberiaLanzarExcepcionCuandoNoExiste() {
        Long idInexistente = 999L;
        when(modeloRepository.findById(idInexistente)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> modeloService.obtenerModeloPorId(idInexistente));

        assertEquals("Modelo no encontrado.", ex.getMessage());
        verify(modeloRepository).findById(idInexistente);
    }

    @Test
    @DisplayName("Debería crear un modelo correctamente")
    void deberiaCrearModelo() {
        // Given
        Modelo nuevo = new Modelo();
        nuevo.setNombre("Bronco Sport 2025");

        when(versionService.resolverVersion(versionesIds)).thenReturn(List.of(new Version()));
        when(colorService.resolverColor(coloresIds)).thenReturn(List.of(new Color()));
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marcaEjemplo);
        when(modeloRepository.save(any(Modelo.class))).thenReturn(nuevo);

        // When
        Modelo resultado = modeloService.crearModelo(nuevo, 1L, coloresIds, versionesIds);

        // Then
        assertNotNull(resultado);
        assertEquals("Bronco Sport 2025", resultado.getNombre());
        verify(modeloRepository).save(nuevo);
    }

    @Test
    @DisplayName("Debería actualizar el modelo correctamente")
    void deberiaActualizarModelo() {
        Long id = 1L;
        when(modeloRepository.findById(id)).thenReturn(Optional.of(modeloEjemplo));
        when(versionService.resolverVersion(versionesIds)).thenReturn(List.of(new Version()));
        when(colorService.resolverColor(coloresIds)).thenReturn(List.of(new Color()));
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marcaEjemplo);
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modeloEjemplo);

        Modelo resultado = modeloService.actualizarModelo(id, "Bronco Sport 2025", 1L, coloresIds, versionesIds);

        assertNotNull(resultado);
        assertEquals("Bronco Sport 2025", resultado.getNombre());
        verify(modeloRepository).save(modeloEjemplo);
    }

    @Test
    @DisplayName("Debería eliminar un modelo existente")
    void deberiaEliminarModelo() {
        Long id = 1L;
        when(modeloRepository.findById(id)).thenReturn(Optional.of(modeloEjemplo));
        doNothing().when(modeloRepository).delete(modeloEjemplo);

        assertDoesNotThrow(() -> modeloService.eliminarModelo(id));

        verify(modeloRepository).findById(id);
        verify(modeloRepository).delete(modeloEjemplo);
    }
}
