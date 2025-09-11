package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ActualizarMarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearMarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.ColorRepository;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ColorRepository colorRepository; // <- en vez de ColorService

    public List<Marca> obtenerTodasLasMarcas() {
        return marcaRepository.findAll();
    }

    public Marca obtenerMarcaPorId(Long id) {
        return marcaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada."));
    }

    public Marca crearMarca(CrearMarcaDTO dto) {
        Marca marca = new Marca();
        marca.setNombreMarca(dto.nombreMarca());

        return marcaRepository.save(marca);
    }

    public Marca actualizarMarca(Long id, ActualizarMarcaDTO dto) {
        Marca marca = obtenerMarcaPorId(id);

        if (dto.modelosIds() != null) {
            List<Modelo> modelos = dto.modelosIds().isEmpty()
                ? List.of()
                : modeloService.resolverModeloMarcaColor(dto.modelosIds());
            marca.setModelos(modelos);
        }

        if (dto.coloresIds() != null) {
            List<Color> colores = dto.coloresIds().isEmpty()
                ? List.of()
                : colorRepository.findAllById(dto.coloresIds());
            if (!dto.coloresIds().isEmpty() && colores.size() != dto.coloresIds().size()) {
                throw new IllegalArgumentException("Algunos colores no existen");
            }
            marca.setColores(colores);
        }

        return marcaRepository.save(marca);
    }

    public void eliminarMarca(Long id) {
        Marca marca = obtenerMarcaPorId(id);
        marcaRepository.delete(marca);
    }
}

