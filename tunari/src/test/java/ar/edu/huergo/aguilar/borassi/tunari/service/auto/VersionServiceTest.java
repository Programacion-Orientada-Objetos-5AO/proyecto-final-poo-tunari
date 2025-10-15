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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearVersionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.VersionRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - VersionService")
class VersionServiceTest {

    @Mock
    private VersionRepository versionRepository;

    @Mock
    private MarcaService marcaService;

    @InjectMocks
    private VersionService versionService;

    private Version versionEjemplo;

    @BeforeEach
    void setUp() {
        versionEjemplo = new Version();
        versionEjemplo.setId(1L);
        versionEjemplo.setNombre("Titanium");
    }

    @Test
    @DisplayName("Debería obtener todas las versiones")
    void deberiaObtenerTodasLasVersiones() {
        // Given
        List<Version> versionesEsperadas = Arrays.asList(versionEjemplo);
        when(versionRepository.findAll()).thenReturn(versionesEsperadas);

        // When
        List<Version> resultado = versionService.obtenerTodasLasVersiones();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Titanium", resultado.get(0).getNombre());
        verify(versionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener version por ID cuando existe")
    void deberiaObtenerVersionPorId() {
        // Given
        Long id = 1L;
        when(versionRepository.findById(id)).thenReturn(Optional.of(versionEjemplo));

        // When
        Version resultado = versionService.obtenerVersionPorId(id);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Titanium", resultado.getNombre());
        verify(versionRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando la version no existe")
    void deberiaLanzarExcepcionCuandoNoExiste() {
        // Given
        Long idInexistente = 999L;
        when(versionRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> versionService.obtenerVersionPorId(idInexistente));

        assertEquals("Version no encontrada.", ex.getMessage());
        verify(versionRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debería crear una marca a partir del DTO")
    void deberiaCrearMarca() {
        // Given
        CrearVersionDTO dto = new CrearVersionDTO("Titanium", 1l);
        Version guardada = new Version();
        guardada.setId(10L);
        guardada.setNombre("Titanium");

        when(versionRepository.save(any(Version.class))).thenReturn(guardada);

        // When
        Version resultado = versionService.crearVersion(dto);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("Titanium", resultado.getNombre());
        verify(versionRepository, times(1)).save(any(Version.class));
    }

    @Test
    @DisplayName("Debería actualizar el nombre de una version existente")
    void deberiaActualizarVersion() {
        // Given
        Long id = 1L;
        when(versionRepository.findById(id)).thenReturn(Optional.of(versionEjemplo));

        Version devueltaPorSave = new Version();
        devueltaPorSave.setId(1L);
        devueltaPorSave.setNombre("Titanium actualizado");
        when(versionRepository.save(any(Version.class))).thenReturn(devueltaPorSave);

        CrearVersionDTO dto = new CrearVersionDTO("Titanium actualizado", 1l);

        // When
        Version resultado = versionService.actualizarVersion(id, dto);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Titanium actualizado", resultado.getNombre());
        // también chequeamos que en la entidad cargada se haya seteado el nombre
        assertEquals("Titanium actualizado", versionEjemplo.getNombre());
        verify(versionRepository, times(1)).findById(id);
        verify(versionRepository, times(1)).save(versionEjemplo);
    }

    @Test
    @DisplayName("Debería eliminar una version existente")
    void deberiaEliminarVersion() {
        // Given
        Long id = 1L;
        when(versionRepository.findById(id)).thenReturn(Optional.of(versionEjemplo));
        doNothing().when(versionRepository).delete(versionEjemplo);

        // When
        assertDoesNotThrow(() -> versionService.eliminarVersion(id));

        // Then
        verify(versionRepository, times(1)).findById(id);
        verify(versionRepository, times(1)).delete(versionEjemplo);
    }
}
