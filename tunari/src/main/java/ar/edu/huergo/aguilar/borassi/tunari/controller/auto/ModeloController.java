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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ModeloDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.ModeloMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.ModeloService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/ingredientes")
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
    public ResponseEntity<ModeloDTO> crearModelo(@RequestBody @Valid ModeloDTO modeloDTO) {
        Modelo modeloNuevo = modeloMapper.toEntity(modeloDTO);
        modeloNuevo = modeloService.crearModelo(modeloNuevo);
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
    public ResponseEntity<ModeloDTO> actualizarModelo(@PathVariable Long id, @RequestBody @Valid ModeloDTO modeloDTO) {
        return ResponseEntity.ok(modeloMapper.toDTO(modeloService.actualizarModelo(id, modeloMapper.toEntity(modeloDTO))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarModelo(@PathVariable Long id) {
        modeloService.eliminarModelo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<ModeloDTO>> obtenerModeloPorId(@RequestParam String nombreModelo) {
        return ResponseEntity.ok(modeloMapper.toDTOList(modeloService.obtenerModeloPorNombre(nombreModelo)));
    }
}
