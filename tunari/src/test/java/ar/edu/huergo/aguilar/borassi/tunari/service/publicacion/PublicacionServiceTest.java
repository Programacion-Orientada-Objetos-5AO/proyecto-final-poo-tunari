package ar.edu.huergo.aguilar.borassi.tunari.service.publicacion;

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

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.repository.publicacion.PublicacionRepository;
import ar.edu.huergo.aguilar.borassi.tunari.repository.security.UsuarioRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.ModeloService;
import ar.edu.huergo.aguilar.borassi.tunari.service.security.JwtTokenService;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PublicacionService")
class PublicacionServiceTest {

    @Mock private PublicacionRepository publicacionRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private ModeloService modeloService;
    @Mock private JwtTokenService jwtTokenService;

    @InjectMocks
    private PublicacionService publicacionService;

    private Publicacion publicacionEjemplo;
    private Modelo modeloEjemplo;
    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        modeloEjemplo = new Modelo();
        modeloEjemplo.setId(1L);
        modeloEjemplo.setNombre("Nuevo modelo 2025");

        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(7L);
        usuarioEjemplo.setUsername("fran");

        publicacionEjemplo = new Publicacion();
        publicacionEjemplo.setId(1L);
        publicacionEjemplo.setModelo(modeloEjemplo);
        publicacionEjemplo.setUsuario(usuarioEjemplo);
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
    @DisplayName("Debería crear una nueva publicación usando modeloId y authHeader")
    void deberiaCrearPublicacion() {
        // Given
        Long modeloId = 1L;
        String authHeader = "Bearer abc.def.ghi";
        String token = "abc.def.ghi";
        String username = "fran";

        when(modeloService.obtenerModeloPorId(modeloId)).thenReturn(modeloEjemplo);
        when(jwtTokenService.extraerUsername(token)).thenReturn(username);
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(usuarioEjemplo));

        Publicacion guardada = new Publicacion();
        guardada.setId(10L);
        guardada.setModelo(modeloEjemplo);
        guardada.setUsuario(usuarioEjemplo);

        when(publicacionRepository.save(any(Publicacion.class))).thenReturn(guardada);

        // When
        Publicacion resultado = publicacionService.crearPublicacion(modeloId, authHeader);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Nuevo modelo 2025", resultado.getModelo().getNombre());
        assertEquals("fran", resultado.getUsuario().getUsername());

        verify(modeloService, times(1)).obtenerModeloPorId(modeloId);
        verify(jwtTokenService, times(1)).extraerUsername(token);
        verify(usuarioRepository, times(1)).findByUsername(username);
        verify(publicacionRepository, times(1)).save(any(Publicacion.class));
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
