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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearMarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - MarcaService")
class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    private Marca marcaEjemplo;

    @BeforeEach
    void setUp() {
        marcaEjemplo = new Marca();
        marcaEjemplo.setId(1L);
        marcaEjemplo.setNombreMarca("Toyota");
    }

    @Test
    @DisplayName("Debería obtener todas las marcas")
    void deberiaObtenerTodasLasMarcas() {
        // Given
        List<Marca> marcasEsperadas = Arrays.asList(marcaEjemplo);
        when(marcaRepository.findAll()).thenReturn(marcasEsperadas);

        // When
        List<Marca> resultado = marcaService.obtenerTodasLasMarcas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Toyota", resultado.get(0).getNombreMarca());
        verify(marcaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener marca por ID cuando existe")
    void deberiaObtenerMarcaPorId() {
        // Given
        Long id = 1L;
        when(marcaRepository.findById(id)).thenReturn(Optional.of(marcaEjemplo));

        // When
        Marca resultado = marcaService.obtenerMarcaPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Toyota", resultado.getNombreMarca());
        verify(marcaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando la marca no existe")
    void deberiaLanzarExcepcionCuandoNoExiste() {
        // Given
        Long idInexistente = 999L;
        when(marcaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> marcaService.obtenerMarcaPorId(idInexistente));

        assertEquals("Marca no encontrada.", ex.getMessage());
        verify(marcaRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debería crear una marca a partir del DTO")
    void deberiaCrearMarca() {
        // Given
        CrearMarcaDTO dto = new CrearMarcaDTO("Honda");
        Marca guardada = new Marca();
        guardada.setId(10L);
        guardada.setNombreMarca("Honda");

        when(marcaRepository.save(any(Marca.class))).thenReturn(guardada);

        // When
        Marca resultado = marcaService.crearMarca(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Honda", resultado.getNombreMarca());
        verify(marcaRepository, times(1)).save(any(Marca.class));
    }

    @Test
    @DisplayName("Debería actualizar el nombre de una marca existente")
    void deberiaActualizarMarca() {
        // Given
        Long id = 1L;
        when(marcaRepository.findById(id)).thenReturn(Optional.of(marcaEjemplo));

        Marca devueltaPorSave = new Marca();
        devueltaPorSave.setId(1L);
        devueltaPorSave.setNombreMarca("Toyota Actualizada");
        when(marcaRepository.save(any(Marca.class))).thenReturn(devueltaPorSave);

        CrearMarcaDTO dto = new CrearMarcaDTO("Toyota Actualizada");

        // When
        Marca resultado = marcaService.actualizarMarca(id, dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Toyota Actualizada", resultado.getNombreMarca());
        // también chequeamos que en la entidad cargada se haya seteado el nombre
        assertEquals("Toyota Actualizada", marcaEjemplo.getNombreMarca());
        verify(marcaRepository, times(1)).findById(id);
        verify(marcaRepository, times(1)).save(marcaEjemplo);
    }

    @Test
    @DisplayName("Debería eliminar una marca existente")
    void deberiaEliminarMarca() {
        // Given
        Long id = 1L;
        when(marcaRepository.findById(id)).thenReturn(Optional.of(marcaEjemplo));
        doNothing().when(marcaRepository).delete(marcaEjemplo);

        // When
        assertDoesNotThrow(() -> marcaService.eliminarMarca(id));

        // Then
        verify(marcaRepository, times(1)).findById(id);
        verify(marcaRepository, times(1)).delete(marcaEjemplo);
    }
}
