package ar.edu.huergo.aguilar.borassi.tunari.service.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearMarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.repository.auto.MarcaRepository;
import jakarta.persistence.EntityNotFoundException;


@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;


    public List<Marca> obtenerTodasLasMarcas() {
        return marcaRepository.findAll();
    }

    public Marca obtenerMarcaPorId(Long id) {
        return marcaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada."));
    }

    public Marca crearMarca(Marca marca) {
        return marcaRepository.save(marca);
    }

    public Marca actualizarMarca(Long id, String nombre) {
        Marca marca = obtenerMarcaPorId(id);
        marca.setNombre(nombre);
        return marcaRepository.save(marca);
    }

    public void eliminarMarca(Long id) {
        Marca marca = obtenerMarcaPorId(id);
        marcaRepository.delete(marca);
    }

    
}

