package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ColorService colorService;

    public List<Marca> obtenerTodasLasMarcas() {
        return marcaRepository.findAll();
    }

    public Marca obtenerMarcaPorId(Long id) throws EntityNotFoundException {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada."));
    }

    public Marca crearMarca(Marca marca, List<Long> modelosIds, List<Long> coloresIds) {
        List<Modelo> modelo = modeloService.resolverModelo(modelosIds);
        marca.setModelos(modelo);
        List<Color> color = colorService.resolverColor(coloresIds);
        marca.setColores(color);
        return marcaRepository.save(marca);
    }

    public Marca actualizarMarca(Long id, Marca marca, List<Long> modelosIds) throws EntityNotFoundException {
        Marca marcaExistente = obtenerMarcaPorId(id);
        marcaExistente.setNombreMarca(marca.getNombreMarca());
        if (modelosIds != null) {
            List<Modelo> modelos = modeloService.resolverModelo(modelosIds);
            marcaExistente.setModelos(modelos);
        }
        return marcaRepository.save(marcaExistente);
    }

    public void eliminarMarca(Long id) throws EntityNotFoundException {
        Marca marca = obtenerMarcaPorId(id);
        marcaRepository.delete(marca);
    }
}
