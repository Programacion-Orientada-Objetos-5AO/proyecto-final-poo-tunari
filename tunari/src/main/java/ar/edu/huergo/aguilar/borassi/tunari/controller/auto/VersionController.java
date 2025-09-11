package ar.edu.huergo.aguilar.borassi.tunari.controller.auto;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.VersionDTO;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.VersionMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.VersionService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/versiones")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @Autowired
    private VersionMapper versionMapper;

    @GetMapping
    public ResponseEntity<List<VersionDTO>> obtenerTodasLasVersiones() {
        return ResponseEntity.ok(versionMapper.toDTOList(versionService.obtenerTodasLasVersiones()));
    }

    @PostMapping
    public ResponseEntity<VersionDTO> crearVersion(@RequestBody @Valid VersionDTO versionDTO) {
        Version versionNueva = versionMapper.toEntity(versionDTO);
        versionNueva = versionService.crearVersion(versionNueva);
        VersionDTO versionCreada = versionMapper.toDTO(versionNueva);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(versionCreada.id())
            .toUri();
        return ResponseEntity.created(location).body(versionCreada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VersionDTO> obtenerVersionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(versionMapper.toDTO(versionService.obtenerVersionPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VersionDTO> actualizarVersion(@PathVariable Long id, @RequestBody @Valid VersionDTO versionDTO) {
        VersionDTO versionActualizada = versionMapper.toDTO(versionService.actualizarVersion(id, versionMapper.toEntity(versionDTO)));
        return ResponseEntity.ok(versionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVersion(@PathVariable Long id) {
        versionService.eliminarVersion(id);
        return ResponseEntity.noContent().build();
    }

}
