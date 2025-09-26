package ar.edu.huergo.aguilar.borassi.tunari.service.publicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.publicacion.PublicacionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;

import ar.edu.huergo.aguilar.borassi.tunari.repository.publicacion.PublicacionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;

    public List<Publicacion> obtenerTodosLosPublicacions() {
        return publicacionRepository.findAll();
    }

    public Publicacion obtenerPublicacionPorId(Long id) throws EntityNotFoundException {
        return publicacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada."));
    }

    public Publicacion crearPublicacion(PublicacionDTO dto) {
        Publicacion publicacion = new Publicacion();   
        return publicacionRepository.save(publicacion);
    }

    public void eliminarPublicacion(Long id) throws EntityNotFoundException {
        Publicacion publicacion = obtenerPublicacionPorId(id);
        publicacionRepository.delete(publicacion);
    }
}
