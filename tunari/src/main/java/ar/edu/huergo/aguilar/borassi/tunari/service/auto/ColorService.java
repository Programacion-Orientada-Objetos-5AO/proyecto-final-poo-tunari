package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.ColorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private MarcaService marcaService;


    public List<Color> obtenerTodosLosColores() {
        return colorRepository.findAll();
    }

    public Color obtenerColorPorId(Long id) throws EntityNotFoundException {
        return colorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Color no encontrado"));
    }

    public Color crearColor(Color color, Long marcaId) throws EntityNotFoundException {;
        color.setMarca(marcaService.obtenerMarcaPorId(marcaId));
        return colorRepository.save(color);
    }

    public Color actualizarColor(Long id, String nombre, Long marcaId) throws EntityNotFoundException {
        Color colorExistente = obtenerColorPorId(id);
        colorExistente.setNombre(nombre);
        colorExistente.setMarca(marcaService.obtenerMarcaPorId(marcaId));
        return colorRepository.save(colorExistente);
    }
    
    public void eliminarColor(Long id) throws EntityNotFoundException {
        Color color = obtenerColorPorId(id);
        colorRepository.delete(color);
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