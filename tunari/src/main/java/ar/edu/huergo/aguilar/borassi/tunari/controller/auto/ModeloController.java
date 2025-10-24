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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.ModeloMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.ModeloService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    @Autowired
    private ModeloMapper modeloMapper;

    @Autowired
    private ModeloService modeloService;

    @GetMapping
    public ResponseEntity<List<ModeloDTO>> obtenerTodosLosModelos() {
        return ResponseEntity.ok(modeloMapper.toDTOList((modeloService.obtenerTodosLosModelos())));
    }

    @PostMapping
    public ResponseEntity<ModeloDTO> crearModelo(@RequestBody @Valid CrearModeloDTO modeloDTO) {
        Modelo modeloNuevo = modeloMapper.toEntity(modeloDTO);
        Long marcaId = modeloDTO.marcaId();
        List<Long> coloresIds = modeloDTO.coloresIds();
        List<Long> versionesIds = modeloDTO.versionesIds();
        modeloNuevo = modeloService.crearModelo(modeloNuevo, marcaId, coloresIds, versionesIds);
        ModeloDTO modeloCreado = modeloMapper.toDTO(modeloNuevo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(modeloCreado.id())
            .toUri();
        return ResponseEntity.created(location).body(modeloCreado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloDTO> obtenerModeloPorId(@PathVariable Long id) {
        return ResponseEntity.ok(modeloMapper.toDTO(modeloService.obtenerModeloPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloDTO> actualizarModelo(@PathVariable Long id, @RequestBody @Valid CrearModeloDTO modeloDTO) {
        Long idModelo = id;
        String nombre = modeloDTO.nombre();
        Long marcaId = modeloDTO.marcaId();
        List<Long> coloresIds = modeloDTO.coloresIds();
        List<Long> versionesIds = modeloDTO.versionesIds();
        Modelo modeloActualizado = modeloService.actualizarModelo(id, nombre, marcaId, coloresIds, versionesIds);
        return ResponseEntity.ok(modeloMapper.toDTO(modeloActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModelo(@PathVariable Long id) {
        modeloService.eliminarModelo(id);
        return ResponseEntity.noContent().build();
    }

    
}
