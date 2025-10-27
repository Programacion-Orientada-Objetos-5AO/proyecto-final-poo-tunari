package ar.edu.huergo.aguilar.borassi.tunari.controller.publicacion;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.MostrarAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.publicacion.CrearPublicacionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.publicacion.MostrarPublicacionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.publicacion.PublicacionMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.publicacion.PublicacionService;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.agencia.AgenciaMapper;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Autowired
    private AgenciaMapper agenciaMapper;

    // GET /publicacions
    @GetMapping
    public ResponseEntity<List<MostrarPublicacionDTO>> obtenerTodasLasPublicaciones() {
        List<Publicacion> publicaciones = publicacionService.obtenerTodasLasPublicaciones();
        return ResponseEntity.ok(publicacionMapper.toDTOList(publicaciones));
    }

    // GET /publicaciones/filtrar?modeloId=..&colorId=..&versionId=..
    @GetMapping("/filtrar")
    public ResponseEntity<List<MostrarAgenciaDTO>> filtrarPublicacion(
        @RequestParam("modeloId") Long modeloId,
        @RequestParam("colorId") Long colorId,
        @RequestParam("versionId") Long versionId
    ) {
        List<Agencia> agencias = publicacionService.filtrarPublicacion(modeloId, colorId, versionId);
        return ResponseEntity.ok(agenciaMapper.toDTOList(agencias));
    }

    // GET /publicacions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MostrarPublicacionDTO> obtenerPublicacionPorId(@PathVariable Long id) {
        Publicacion publicacion = publicacionService.obtenerPublicacionPorId(id);
        return ResponseEntity.ok(publicacionMapper.toDTO(publicacion));
    }

    // POST /publicacions
    @PostMapping
    public ResponseEntity<String> crearPublicacion(@RequestBody @Valid CrearPublicacionDTO dto,
        @RequestHeader("Authorization") String authHeader) {
        Long modeloId = dto.modeloId();
        Publicacion publicacionCreada = publicacionService.crearPublicacion(modeloId, authHeader);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(publicacionCreada.getId())
            .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(location).body("Publicacion creada correctamente " + publicacionMapper.toDTO(publicacionCreada));
    }


    // DELETE /publicacions/{id}
    @DeleteMapping("/{id}")
    public void eliminarPublicacion(@PathVariable Long id) {
        publicacionService.eliminarPublicacion(id);
    }
}
