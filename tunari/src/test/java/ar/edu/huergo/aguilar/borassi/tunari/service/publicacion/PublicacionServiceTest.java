package ar.edu.huergo.aguilar.borassi.tunari.service.publicacion;

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

import ar.edu.huergo.aguilar.borassi.tunari.dto.publicacion.CrearPublicacionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;
import ar.edu.huergo.aguilar.borassi.tunari.repository.publicacion.PublicacionRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.ModeloService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PublicacionService")
class PublicacionServiceTest {

    @Mock
    private PublicacionRepository publicacionRepository;

    @Mock
    private ModeloService modeloService; // porque el service usa modeloId del DTO

    @InjectMocks
    private PublicacionService publicacionService;

    private Publicacion publicacionEjemplo;
    private Modelo modeloEjemplo;

    @BeforeEach
    void setUp() {
        modeloEjemplo = new Modelo();
        modeloEjemplo.setId(1L);
        modeloEjemplo.setNombre("Nuevo modelo 2025");

        publicacionEjemplo = new Publicacion();
        publicacionEjemplo.setId(1L);
        publicacionEjemplo.setModelo(modeloEjemplo);
    }

    @Test
    @DisplayName("Debería obtener todas las publicaciones")
    void deberiaObtenerTodasLasPublicaciones() {
        when(publicacionRepository.findAll()).thenReturn(Arrays.asList(publicacionEjemplo));

        List<Publicacion> resultado = publicacionService.obtenerTodasLasPublicaciones();

        assertEquals(1, resultado.size());
        assertEquals("Nuevo modelo 2025", resultado.get(0).getModelo().getNombre());
        verify(publicacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener una publicación por ID cuando existe")
    void deberiaObtenerPorId() {
        Long id = 1L;
        when(publicacionRepository.findById(id)).thenReturn(Optional.of(publicacionEjemplo));

        Publicacion resultado = publicacionService.obtenerPublicacionPorId(id);

        assertNotNull(resultado);
        assertEquals("Nuevo modelo 2025", resultado.getModelo().getNombre());
        verify(publicacionRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la publicación no existe")
    void deberiaLanzarExcepcionCuandoNoExiste() {
        when(publicacionRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> publicacionService.obtenerPublicacionPorId(99L));

        assertEquals("Publicacion no encontrada.", ex.getMessage());
    }
    
    @Test
    @DisplayName("Debería crear una nueva publicación usando modeloId del DTO")
    void deberiaCrearPublicacionDesdeDTO() {
        // Given
        CrearPublicacionDTO dto = new CrearPublicacionDTO(1L);

        when(modeloService.obtenerModeloPorId(1L)).thenReturn(modeloEjemplo);
        when(publicacionRepository.save(any(Publicacion.class))).thenAnswer(invocation -> {
            Publicacion p = invocation.getArgument(0);
            p.setId(10L);
            return p;
        });

        // When
        Publicacion resultado = publicacionService.crearPublicacion(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Nuevo modelo 2025", resultado.getModelo().getNombre());
        verify(publicacionRepository, times(1)).save(any(Publicacion.class));
        verify(modeloService, times(1)).obtenerModeloPorId(1L);
    }

    @Test
    @DisplayName("Debería eliminar una publicación existente")
    void deberiaEliminarPublicacion() {
        Long id = 1L;
        when(publicacionRepository.findById(id)).thenReturn(Optional.of(publicacionEjemplo));
        doNothing().when(publicacionRepository).delete(publicacionEjemplo);

        assertDoesNotThrow(() -> publicacionService.eliminarPublicacion(id));
        verify(publicacionRepository, times(1)).findById(id);
        verify(publicacionRepository, times(1)).delete(publicacionEjemplo);
    }
}
