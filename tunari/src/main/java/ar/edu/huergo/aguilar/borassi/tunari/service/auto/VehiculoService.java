package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearVehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository autoRepository;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private MarcaService marcaService;

    public List<Vehiculo> obtenerTodosLosAutos() {
        return autoRepository.findAll();
    }

    public Vehiculo obtenerAutoPorId(Long id) throws EntityNotFoundException {
        return autoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auto no encontrado."));
    }

    public Vehiculo crearAuto(CrearVehiculoDTO dto) {
        Marca marca = marcaService.obtenerMarcaPorId(dto.getMarcaId());
        Modelo modelo = modeloService.obtenerModeloPorId(dto.getModeloId());
        Color color = colorService.obtenerColorPorId(dto.getColorId());
        Version version = versionService.obtenerVersionPorId(dto.getVersionId());

        modelo.validarAuto(color, version);
        Vehiculo auto = new Vehiculo();
        auto.setMarca(marca);
        auto.setModelo(modelo);
        auto.setColor(color);       
        auto.setVersion(version);   
        return autoRepository.save(auto);
    }

    public void eliminarAuto(Long id) throws EntityNotFoundException {
        Vehiculo auto = obtenerAutoPorId(id);
        autoRepository.delete(auto);
    }
    
    public Vehiculo resolverAuto(Long autoId) throws IllegalArgumentException, EntityNotFoundException {
        Vehiculo auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new EntityNotFoundException("Auto no encontrado."));
        return auto;
    }
}

