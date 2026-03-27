package ar.edu.huergo.aguilar.borassi.tunari.controller.auto;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearMarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.MarcaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Marca;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.MarcaMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.MarcaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    @org.springframework.beans.factory.annotation.Autowired
    private MarcaService marcaService;

    @org.springframework.beans.factory.annotation.Autowired
    private MarcaMapper marcaMapper;

    // GET /marcas
    @GetMapping
    public ResponseEntity<List<MarcaDTO>> obtenerTodasLasMarcas() {
        List<Marca> marcas = marcaService.obtenerTodasLasMarcas();
        return ResponseEntity.ok(marcaMapper.toDTOList(marcas));
    }

    // GET /marcas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> obtenerMarcaPorId(@PathVariable Long id) {
        Marca marca = marcaService.obtenerMarcaPorId(id);
        return ResponseEntity.ok(marcaMapper.toDTO(marca));
    }

    // POST /marcas
    @PostMapping
    public ResponseEntity<String> crearMarca(@RequestBody @Valid CrearMarcaDTO dto) {
        Marca marcaCreada = marcaMapper.toEntity(dto);
        marcaCreada = marcaService.crearMarca(marcaCreada);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(marcaCreada.getId())
            .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(location).body("Marca creada correctamente " + marcaMapper.toDTO(marcaCreada));
    }

    // PUT /marcas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> actualizarMarca(@PathVariable Long id, @RequestBody @Valid CrearMarcaDTO dto) {
        Marca actualizada = marcaService.actualizarMarca(id, dto.nombreMarca());
        return ResponseEntity.ok(marcaMapper.toDTO(actualizada));
    }

    // DELETE /marcas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMarca(@PathVariable Long id) {
        boolean eliminada = marcaService.eliminarMarca(id);

        if (!eliminada) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la marca con ID " + id + " para eliminar.");
        }

        return ResponseEntity.ok("Marca eliminada correctamente (ID: " + id + ")");
    }
}
