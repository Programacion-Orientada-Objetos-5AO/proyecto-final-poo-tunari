package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
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

    public List<Auto> obtenerTodosLosAutos() {
        return autoRepository.findAll();
    }

    public Auto obtenerAutoPorId(Long id) throws EntityNotFoundException {
        return autoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auto no encontrado."));
    }

    public Auto crearAuto(Auto auto, String marca, List<Long> coloresIds, List<Long> modelosIds, List<Long> versionesIds) {
        List<Modelo> modelo = modeloService.resolverModelo(modelosIds);
        auto.setModelo(modelo);
        List<Color> color = colorService.resolverColor(coloresIds);
        auto.setColores(color);
        List<Version> versiones = versionService.resolverVersion(versionesIds);
        auto.setVersiones(versiones);
        String marcaAuto = marca;
        auto.setMarca(marcaAuto);
        return autoRepository.save(auto);
    }

    public Auto actualizarAuto(Long id, Auto auto, List<Long> coloresIds, List<Long> modelosIds, List<Long> versionesIds) throws EntityNotFoundException {
        Auto autoExistente = obtenerAutoPorId(id);
        autoExistente.setModelo(auto.getModelo());
        if (modelosIds != null) {
            List<Modelo> modelos = modeloService.resolverModelo(modelosIds);
            autoExistente.setModelo(modelos);
        }
        if (versionesIds != null) {
            List<Version> versiones = versionService.resolverVersion(versionesIds);
            autoExistente.setVersiones(versiones);
        }
        if (coloresIds != null) {
            List<Color> colores = colorService.resolverColor(coloresIds);
            autoExistente.setColores(colores);
        }
        return autoRepository.save(autoExistente);
    }

    public void eliminarAuto(Long id) throws EntityNotFoundException {
        Auto auto = obtenerAutoPorId(id);
        autoRepository.delete(auto);
    }
}

