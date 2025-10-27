package ar.edu.huergo.aguilar.borassi.tunari.service.agencia;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.repository.agencia.AgenciaRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.MarcaService;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.VehiculoService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - AgenciaService")
class AgenciaServiceTest {

    @Mock private AgenciaRepository agenciaRepository;
    @Mock private MarcaService marcaService;
    // Lo mockeamos por si en el futuro se usa en estos métodos
    @Mock private VehiculoService vehiculoService;

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
    @DisplayName("Debería crear una nueva agencia correctamente")
    void deberiaCrearAgenciaCorrectamente() {
        // Given
        Agencia nuevaAgencia = new Agencia();
        nuevaAgencia.setNombre("Tunari Center");
        nuevaAgencia.setUbicacion("Av. Rivadavia 456");

        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marcaEjemplo);
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(nuevaAgencia);

        // When
        Agencia resultado = agenciaService.crearAgencia(nuevaAgencia, 1L);

        // Then
        assertNotNull(resultado);
        assertEquals(nuevaAgencia.getNombre(), resultado.getNombre());
        assertEquals(nuevaAgencia.getUbicacion(), resultado.getUbicacion());
        assertEquals(marcaEjemplo, resultado.getMarca());

        verify(marcaService, times(1)).obtenerMarcaPorId(1L);
        verify(agenciaRepository, times(1)).save(nuevaAgencia);
    }


    @Test
    @DisplayName("Debería actualizar una agencia existente correctamente")
    void deberiaActualizarAgenciaExistente() {
        // Given
        Long id = 1L;

        Agencia datosActualizados = new Agencia();
        datosActualizados.setNombre("Tunari Motors Actualizada");
        datosActualizados.setUbicacion("Av. Boca Juniors");

        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agenciaEjemplo));
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marcaEjemplo);
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(agenciaEjemplo);

        Agencia resultado = agenciaService.actualizarAgencia(id, datosActualizados, 1L);

        assertNotNull(resultado);

        verify(agenciaRepository, times(1)).findById(id);
        verify(marcaService, times(1)).obtenerMarcaPorId(1L);
        verify(agenciaRepository, times(1)).save(agenciaEjemplo);


        assertEquals("Tunari Motors Actualizada", agenciaEjemplo.getNombre());
        assertEquals("Av. Boca Juniors", agenciaEjemplo.getUbicacion());
        assertEquals(marcaEjemplo, agenciaEjemplo.getMarca());

        assertEquals("Tunari Motors Actualizada", resultado.getNombre());
        assertEquals("Av. Boca Juniors", resultado.getUbicacion());
        assertEquals("Toyota", resultado.getMarca().getNombre());
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
