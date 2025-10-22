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

import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.repository.agencia.AgenciaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - AgenciaService")
class AgenciaServiceTest {

    @Mock
    private AgenciaRepository agenciaRepository;

    @InjectMocks
    private AgenciaService agenciaService;

    private Agencia agenciaEjemplo;

    @BeforeEach
    void setUp() {
        agenciaEjemplo = new Agencia();
        agenciaEjemplo.setId(1L);
        agenciaEjemplo.setNombre("Tunari Motors");
        agenciaEjemplo.setUbicacion("Av. Siempre Viva 123");
    }

    @Test
    @DisplayName("Debería obtener todas las agencias")
    void deberiaObtenerTodasLasAgencias() {
        // Given
        List<Agencia> agencias = Arrays.asList(agenciaEjemplo);
        when(agenciaRepository.findAll()).thenReturn(agencias);

        // When
        List<Agencia> resultado = agenciaService.obtenerTodasLasAgencias();

        // Then
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

        assertEquals("Agencia no encontrada", ex.getMessage());
        verify(agenciaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería crear una nueva agencia")
    void deberiaCrearAgencia() {
        Agencia nueva = new Agencia();
        nueva.setNombre("Tunari Center");
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(nueva);

        Agencia resultado = agenciaService.crearAgencia(nueva);

        assertNotNull(resultado);
        assertEquals("Tunari Center", resultado.getNombre());
        verify(agenciaRepository, times(1)).save(any(Agencia.class));
    }

    @Test
    @DisplayName("Debería actualizar una agencia existente")
    void deberiaActualizarAgencia() {
        Long id = 1L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agenciaEjemplo));
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(agenciaEjemplo);

        agenciaEjemplo.setNombre("Tunari Motors Actualizada");
        Agencia resultado = agenciaService.actualizarAgencia(id, agenciaEjemplo);

        assertEquals("Tunari Motors Actualizada", resultado.getNombre());
        verify(agenciaRepository, times(1)).save(agenciaEjemplo);
    }

    @Test
    @DisplayName("Debería eliminar una agencia existente")
    void deberiaEliminarAgencia() {
        Long id = 1L;
        when(agenciaRepository.findById(id)).thenReturn(Optional.of(agenciaEjemplo));
        doNothing().when(agenciaRepository).delete(agenciaEjemplo);

        assertDoesNotThrow(() -> agenciaService.eliminarAgencia(id));
        verify(agenciaRepository, times(1)).delete(agenciaEjemplo);
    }
}
