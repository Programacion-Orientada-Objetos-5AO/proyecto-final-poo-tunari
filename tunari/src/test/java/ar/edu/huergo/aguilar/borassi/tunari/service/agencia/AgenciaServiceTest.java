package ar.edu.huergo.aguilar.borassi.tunari.service.agencia;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.repository.agencia.AgenciaRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.MarcaService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - AgenciaService")
class AgenciaServiceTest {

    @Mock
    private AgenciaRepository agenciaRepository;

    @Mock
    private MarcaService marcaService;

    @InjectMocks
    private AgenciaService agenciaService;

    private Agencia agenciaEjemplo;
    private Marca marcaEjemplo;

    @BeforeEach
    void setUp() {
        // Marca de ejemplo
        marcaEjemplo = new Marca();
        marcaEjemplo.setId(1L);
        marcaEjemplo.setNombre("Toyota");

        // Agencia de ejemplo
        agenciaEjemplo = new Agencia();
        agenciaEjemplo.setId(1L);
        agenciaEjemplo.setNombre("Tunari Motors");
        agenciaEjemplo.setUbicacion("Av. Siempre Viva 123");
        agenciaEjemplo.setMarca(marcaEjemplo);
    }

    @Test
    @DisplayName("Debería obtener todas las agencias")
    void deberiaObtenerTodasLasAgencias() {
        when(agenciaRepository.findAll()).thenReturn(Arrays.asList(agenciaEjemplo));

        List<Agencia> resultado = agenciaService.obtenerTodasLasAgencias();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tunari Motors", resultado.get(0).getNombre());
        verify(agenciaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener agencia por ID cuando existe")
    void deberiaObtenerAgenciaPorId() {
        Long id = 1L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agenciaEjemplo));

        Agencia resultado = agenciaService.obtenerAgenciaPorId(id);

        assertNotNull(resultado);
        assertEquals("Tunari Motors", resultado.getNombre());
        verify(agenciaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar excepción si no existe la agencia")
    void deberiaLanzarExcepcionSiNoExiste() {
        Long id = 99L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> agenciaService.obtenerAgenciaPorId(id));

        assertEquals("Agencia no encontrado", ex.getMessage());
        verify(agenciaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería crear una nueva agencia desde el DTO")
    void deberiaCrearAgenciaDesdeDTO() {
        // Given
        CrearAgenciaDTO dto = new CrearAgenciaDTO(null, "Tunari Center", "Av. Rivadavia 456", 1L);

        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marcaEjemplo);
        when(agenciaRepository.save(any(Agencia.class))).thenAnswer(invocation -> {
            Agencia a = invocation.getArgument(0);
            a.setId(10L);
            return a;
        });

        // When
        Agencia resultado = agenciaService.crearAgencia(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Tunari Center", resultado.getNombre());
        assertEquals("Av. Rivadavia 456", resultado.getUbicacion());
        assertEquals("Toyota", resultado.getMarca().getNombre());
        verify(agenciaRepository, times(1)).save(any(Agencia.class));
        verify(marcaService, times(1)).obtenerMarcaPorId(1L);
    }

    @Test
    @DisplayName("Debería actualizar una agencia existente desde el DTO")
    void deberiaActualizarAgenciaDesdeDTO() {
        Long id = 1L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agenciaEjemplo));
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marcaEjemplo);
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(agenciaEjemplo);

        CrearAgenciaDTO dto = new CrearAgenciaDTO(id, "Tunari Motors Actualizada", "Av. Siempre Viva 999", 1L);

        Agencia resultado = agenciaService.actualizarAgencia(id, dto);

        assertNotNull(resultado);
        assertEquals("Tunari Motors Actualizada", resultado.getNombre());
        assertEquals("Av. Siempre Viva 999", resultado.getUbicacion());
        verify(agenciaRepository, times(1)).save(any(Agencia.class));
        verify(marcaService, times(1)).obtenerMarcaPorId(1L);
    }

    @Test
    @DisplayName("Debería eliminar una agencia existente")
    void deberiaEliminarAgencia() {
        Long id = 1L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agenciaEjemplo));
        doNothing().when(agenciaRepository).delete(agenciaEjemplo);

        assertDoesNotThrow(() -> agenciaService.eliminarAgencia(id));
        verify(agenciaRepository, times(1)).findById(id);
        verify(agenciaRepository, times(1)).delete(agenciaEjemplo);
    }
}
