package ar.edu.huergo.aguilar.borassi.tunari.controller.agencia;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.MostrarAutoStockDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAutoStockDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.AutoStock;
import ar.edu.huergo.aguilar.borassi.tunari.service.agencia.AutoStockService;
import jakarta.validation.Valid;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.agencia.AutoStockMapper;

@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/autoStock") //El dominio con el que acciona el controller
public class AutoStockController {

    @Autowired
    private AutoStockService autoStockService;

    @Autowired
    private AutoStockMapper autoStockMapper;

     // GET /autoStocks
    @GetMapping
    public ResponseEntity<List<MostrarAutoStockDTO>> obtenerTodosLosAutoStocks() {
        List<AutoStock> autoStocks = this.autoStockService.obtenerTodosLosAutoStock();
        return ResponseEntity.ok(this.autoStockMapper.toDTOList(autoStocks));
    }

    // GET /autoStocks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MostrarAutoStockDTO> obtenerAutoStockPorId(@PathVariable Long id) {
        AutoStock autoStock = this.autoStockService.obtenerAutoStockPorId(id);
        return ResponseEntity.ok(this.autoStockMapper.toDTO(autoStock));
    }

    // POST /autoStocks
    @PostMapping
    public ResponseEntity<MostrarAutoStockDTO> crearAutoStock(@RequestBody @Valid CrearAutoStockDTO dto) {
        AutoStock creado = this.autoStockService.crearAutoStock(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location).body(this.autoStockMapper.toDTO(creado));
    }

    // DELETE /autoStocks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutoStock(@PathVariable Long id) {
        this.autoStockService.eliminarAutoStock(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
