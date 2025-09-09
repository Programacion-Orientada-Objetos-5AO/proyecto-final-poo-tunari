package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloService modeloService;

    public List<Marca> obtenerTodasLasMarcas() {
        return marcaRepository.findAll();
    }

    public Marca obtenerMarcaPorId(Long id) throws EntityNotFoundException {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada."));
    }

    public Marca crearMarca(Marca marca, List<Long> modelosIds) {
        List<Modelo> modelo = modeloService.resolverModeloMarcaColor(modelosIds);
        marca.setModelos(modelo);
        return marcaRepository.save(marca);
    }

    public Marca actualizarMarca(Long id, Marca marca, List<Long> modelosIds) throws EntityNotFoundException {
        Marca marcaExistente = obtenerMarcaPorId(id);
        marcaExistente.setNombreMarca(marca.getNombreMarca());
        if (modelosIds != null) {
            List<Modelo> modelos = modeloService.resolverModeloMarcaColor(modelosIds);
            marcaExistente.setModelos(modelos);
        }
        return marcaRepository.save(marcaExistente);
    }

    public void eliminarMarca(Long id) throws EntityNotFoundException {
        Marca marca = obtenerMarcaPorId(id);
        marcaRepository.delete(marca);
    }
}
