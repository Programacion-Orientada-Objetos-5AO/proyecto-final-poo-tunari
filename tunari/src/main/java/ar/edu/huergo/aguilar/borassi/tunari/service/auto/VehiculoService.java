package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearVehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private MarcaService marcaService;

    public List<Vehiculo> obtenerTodosLosVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo obtenerVehiculoPorId(Long id) throws EntityNotFoundException {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehiculo no encontrado."));
    }

    public Vehiculo crearVehiculo(CrearVehiculoDTO dto) {
        Marca marca = marcaService.obtenerMarcaPorId(dto.getMarcaId());
        Modelo modelo = modeloService.obtenerModeloPorId(dto.getModeloId());
        Color color = colorService.obtenerColorPorId(dto.getColorId());
        Version version = versionService.obtenerVersionPorId(dto.getVersionId());

        modelo.validarVehiculo(color, version);
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setColor(color);       
        vehiculo.setVersion(version);   
        return vehiculoRepository.save(vehiculo);
    }

    public void eliminarVehiculo(Long id) throws EntityNotFoundException {
        Vehiculo vehiculo = obtenerVehiculoPorId(id);
        vehiculoRepository.delete(vehiculo);
    }
    
    public Vehiculo resolverVehiculo(Long vehiculoId) throws IllegalArgumentException, EntityNotFoundException {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Vehiculo no encontrado."));
        return vehiculo;
    }
}

