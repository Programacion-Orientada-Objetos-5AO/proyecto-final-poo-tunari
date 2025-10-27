package ar.edu.huergo.aguilar.borassi.tunari.service.publicacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.publicacion.CrearPublicacionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.AutoStock;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import ar.edu.huergo.aguilar.borassi.tunari.repository.publicacion.PublicacionRepository;
import ar.edu.huergo.aguilar.borassi.tunari.repository.security.UsuarioRepository;
import ar.edu.huergo.aguilar.borassi.tunari.service.agencia.AgenciaService;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.ColorService;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.ModeloService;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.VersionService;
import ar.edu.huergo.aguilar.borassi.tunari.service.security.JwtTokenService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModeloService modeloService;
    @Autowired
    private VersionService versionService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private AgenciaService agenciaService;
    @Autowired
    private JwtTokenService jwtTokenService;

    public List<Publicacion> obtenerTodasLasPublicaciones() {
        return publicacionRepository.findAll();
    }

    public Publicacion obtenerPublicacionPorId(Long id) throws EntityNotFoundException {
        return publicacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Publicacion no encontrada."));
    }

    public Publicacion crearPublicacion(Long modeloId, String authHeader) {

        Modelo modelo = modeloService.obtenerModeloPorId(modeloId);
        String token = authHeader.substring(7); // eliminar "Bearer "
        String username = this.jwtTokenService.extraerUsername(token);

        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));;

        Publicacion publicacion = new Publicacion();
        publicacion.setModelo(modelo);
        publicacion.setUsuario(usuario);

        return publicacionRepository.save(publicacion);
    }

    public void eliminarPublicacion(Long id) throws EntityNotFoundException {
        Publicacion publicacion = obtenerPublicacionPorId(id);
        publicacionRepository.delete(publicacion);
    }
    


    public List<Agencia> filtrarPublicacion(Long modeloId, Long versionId, Long colorId) {

    Modelo modelo = modeloService.obtenerModeloPorId(modeloId);
    Color color = colorService.obtenerColorPorId(colorId);
    Version version = versionService.obtenerVersionPorId(versionId);
    modelo.validarVehiculo(color, version);

    Vehiculo filtro = new Vehiculo(modelo, color, version);

    List<Agencia> agenciasConAuto = new ArrayList<>();

    for (Agencia ag : agenciaService.obtenerTodasLasAgencias()) {
        int stock = ag.getListaAutos().stream()
            .filter(as -> filtro.equals(as.getAuto()))
            .mapToInt(AutoStock::getStock)
            .sum();

        if (stock > 0) {
            agenciasConAuto.add(ag);
        }
    }

    return agenciasConAuto;
}


}
