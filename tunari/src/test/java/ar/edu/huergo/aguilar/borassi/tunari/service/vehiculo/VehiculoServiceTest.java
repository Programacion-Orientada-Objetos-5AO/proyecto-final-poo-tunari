package ar.edu.huergo.aguilar.borassi.tunari.service.vehiculo;

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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.TipoVehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.*;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.VehiculoRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.*;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - VehiculoService (con TipoVehiculo)")
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private MarcaService marcaService;

    @Mock
    private ModeloService modeloService;

    @Mock
    private ColorService colorService;

    @Mock
    private VersionService versionService;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Marca marca;
    private Modelo modelo;
    private Color color;
    private Version version;
    private Vehiculo vehiculoEjemplo;

    @BeforeEach
    void setUp() {
        marca = new Marca();
        marca.setId(1L);
        marca.setNombre("Ford");

        modelo = new Modelo();
        modelo.setId(2L);
        modelo.setNombre("Ranger");
        modelo.setMarca(marca);

        color = new Color();
        color.setId(3L);
        color.setNombre("Negro");
        color.setMarca(marca);

        version = new Version();
        version.setId(4L);
        version.setNombre("XLT");
        version.setMarca(marca);

        vehiculoEjemplo = new Vehiculo();
        vehiculoEjemplo.setId(10L);
        vehiculoEjemplo.setMarca(marca);
        vehiculoEjemplo.setModelo(modelo);
        vehiculoEjemplo.setColor(color);
        vehiculoEjemplo.setVersion(version);

        modelo.setColores(List.of(color));
        modelo.setVersiones(List.of(version));
    }

    @Test
    @DisplayName("Debería obtener todos los vehículos")
    void deberiaObtenerTodosLosVehiculos() {
        when(vehiculoRepository.findAll()).thenReturn(Arrays.asList(vehiculoEjemplo));

        List<Vehiculo> resultado = vehiculoService.obtenerTodosLosVehiculos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ranger", resultado.get(0).getModelo().getNombre());
        verify(vehiculoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener vehículo por ID cuando existe")
    void deberiaObtenerVehiculoPorId() {
        when(vehiculoRepository.findById(10L)).thenReturn(Optional.of(vehiculoEjemplo));

        Vehiculo resultado = vehiculoService.obtenerVehiculoPorId(10L);

        assertNotNull(resultado);
        assertEquals("Ranger", resultado.getModelo().getNombre());
        verify(vehiculoRepository, times(1)).findById(10L);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el vehículo no existe")
    void deberiaLanzarExcepcionCuandoNoExiste() {
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> vehiculoService.obtenerVehiculoPorId(99L));

        assertEquals("Vehículo no encontrado.", ex.getMessage());
        verify(vehiculoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debería crear un nuevo AUTO correctamente")
    void deberiaCrearAuto() {
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marca);
        when(modeloService.obtenerModeloPorId(2L)).thenReturn(modelo);
        when(colorService.obtenerColorPorId(3L)).thenReturn(color);
        when(versionService.obtenerVersionPorId(4L)).thenReturn(version);
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(new Auto());

        Vehiculo resultado = vehiculoService.crearVehiculo(
                new Vehiculo(),
                1L, 2L, 3L, 4L,
                TipoVehiculo.AUTO, 480
        );

        assertNotNull(resultado);
        assertTrue(resultado instanceof Auto);
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    @DisplayName("Debería crear una PICKUP correctamente")
    void deberiaCrearPickUp() {
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marca);
        when(modeloService.obtenerModeloPorId(2L)).thenReturn(modelo);
        when(colorService.obtenerColorPorId(3L)).thenReturn(color);
        when(versionService.obtenerVersionPorId(4L)).thenReturn(version);
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(new PickUp());

        Vehiculo resultado = vehiculoService.crearVehiculo(
                new Vehiculo(),
                1L, 2L, 3L, 4L,
                TipoVehiculo.PICKUP, 2000
        );

        assertNotNull(resultado);
        assertTrue(resultado instanceof PickUp);
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }


    @Test
    @DisplayName("Debería actualizar un vehículo existente correctamente")
    void deberiaActualizarVehiculo() {
        when(vehiculoRepository.findById(10L)).thenReturn(Optional.of(vehiculoEjemplo));
        when(marcaService.obtenerMarcaPorId(1L)).thenReturn(marca);
        when(modeloService.obtenerModeloPorId(2L)).thenReturn(modelo);
        when(colorService.obtenerColorPorId(3L)).thenReturn(color);
        when(versionService.obtenerVersionPorId(4L)).thenReturn(version);
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculoEjemplo);

        Vehiculo actualizado = new Vehiculo();
        actualizado.setMarca(marca);
        actualizado.setModelo(modelo);
        actualizado.setColor(color);
        actualizado.setVersion(version);

        Vehiculo resultado = vehiculoService.actualizarVehiculo(10L, actualizado, 1L, 2L, 3L, 4L);

        assertNotNull(resultado);
        assertEquals("Ranger", resultado.getModelo().getNombre());
        verify(vehiculoRepository, times(1)).save(vehiculoEjemplo);
    }

    @Test
    @DisplayName("Debería eliminar un vehículo correctamente")
    void deberiaEliminarVehiculo() {
        when(vehiculoRepository.findById(10L)).thenReturn(Optional.of(vehiculoEjemplo));
        doNothing().when(vehiculoRepository).delete(vehiculoEjemplo);

        assertDoesNotThrow(() -> vehiculoService.eliminarVehiculo(10L));

        verify(vehiculoRepository, times(1)).delete(vehiculoEjemplo);
    }

    @Test
    @DisplayName("Debería resolver un vehículo existente por ID")
    void deberiaResolverVehiculo() {
        when(vehiculoRepository.findById(10L)).thenReturn(Optional.of(vehiculoEjemplo));

        Vehiculo resultado = vehiculoService.resolverVehiculo(10L);

        assertNotNull(resultado);
        assertEquals(vehiculoEjemplo, resultado);
        verify(vehiculoRepository, times(1)).findById(10L);
    }
}
