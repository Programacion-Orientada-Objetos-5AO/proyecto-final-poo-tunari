package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearAutoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.AutoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private MarcaService marcaService;

    public List<Auto> obtenerTodosLosAutos() {
        return autoRepository.findAll();
    }

    public Auto obtenerAutoPorId(Long id) throws EntityNotFoundException {
        return autoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auto no encontrado."));
    }

    public Auto crearAuto(CrearAutoDTO dto) {
        Marca marca = marcaService.obtenerMarcaPorId(dto.marcaId());
        Modelo modelo = modeloService.obtenerModeloPorId(dto.modeloId());
        Color color = colorService.obtenerColorPorId(dto.colorId());
        Version version = versionService.obtenerVersionPorId(dto.versionId());

        if (!modelo.validarAuto(color, version)) {
            throw new IllegalArgumentException("El modelo no es compatible con el color o la versión proporcionados.");
        }
        Auto auto = new Auto();
        auto.setMarca(marca);
        auto.setModelo(modelo);
        auto.setColor(color);       
        auto.setVersion(version);   
        return autoRepository.save(auto);
    }

    public void eliminarAuto(Long id) throws EntityNotFoundException {
        Auto auto = obtenerAutoPorId(id);
        autoRepository.delete(auto);
    }
    
       public Auto resolverAuto(Long autoId) throws IllegalArgumentException, EntityNotFoundException {
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new EntityNotFoundException("Auto no encontrado."));
        return auto;
    }
}

