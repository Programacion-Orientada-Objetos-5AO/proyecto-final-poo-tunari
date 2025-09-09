package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.*;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private ModeloService modeloService;

    public List<Color> obtenerTodosLosColores() {
        return colorRepository.findAll();
    }

    public Color obtenerColorPorId(Long id) throws EntityNotFoundException {
        return colorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Color no encontrado"));
    }

    public Color crearColor(CrearColorDTO color) {
        Color colorEntity = new Color();
        colorEntity.setNombreColor(color.nombreColor());
        colorEntity.setMarca(marcaService.obtenerMarcaPorId(color.marcaId()));
        List<Modelo> modelos = modeloService.resolverModelo(color.modelosIds());
        colorEntity.setModelos(modelos);
        return colorRepository.save(colorEntity);
    }

    public Color actualizarColor(Long id, CrearColorDTO color) throws EntityNotFoundException {
        Color colorExistente = obtenerColorPorId(id);
        colorExistente.setNombreColor(color.nombreColor());
        colorExistente.setMarca(marcaService.obtenerMarcaPorId(color.marcaId()));
        List<Modelo> modelos = modeloService.resolverModelo(color.modelosIds());
        colorExistente.setModelos(modelos);
        return colorRepository.save(colorExistente);
    }
    
    public void eliminarColor(Long id) throws EntityNotFoundException {
        Color color = obtenerColorPorId(id);
        colorRepository.delete(color);
    }

    public List<Color> obtenerColorPorNombre(String nombreColor) {
        return colorRepository.findByNombreContainingIgnoreCase(nombreColor);
    }

    public List<Color> resolverColor(List<Long> coloresIds) throws IllegalArgumentException, EntityNotFoundException {
        if (coloresIds == null || coloresIds.isEmpty()) {
            throw new IllegalArgumentException("Hay que ingresar un color.");
        }
        List<Color> colores = colorRepository.findAllById(coloresIds);
        if (colores.size() != colores.stream().filter(Objects::nonNull).distinct()
                .count()) {
            throw new EntityNotFoundException("Uno o más colores no existen. Intente nuevamente.");
        }
        return colores;
    }
}