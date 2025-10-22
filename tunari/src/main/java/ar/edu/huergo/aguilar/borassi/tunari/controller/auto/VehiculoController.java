package ar.edu.huergo.aguilar.borassi.tunari.controller.auto;


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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.VehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearVehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.VehiculoService;
import jakarta.validation.Valid;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.VehiculoMapper;

@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/autos") //El dominio con el que acciona el controller
public class VehiculoController {

    @Autowired
    private VehiculoService autoService;

    @Autowired
    private VehiculoMapper autoMapper;

     // GET /autos
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> obtenerTodosLosAutos() {
        List<Vehiculo> autos = this.autoService.obtenerTodosLosAutos();
        return ResponseEntity.ok(this.autoMapper.toDTOList(autos));
    }

    // GET /autos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> obtenerAutoPorId(@PathVariable Long id) {
        Vehiculo auto = this.autoService.obtenerAutoPorId(id);
        return ResponseEntity.ok(this.autoMapper.toDTO(auto));
    }

    // POST /autos
    @PostMapping
    public ResponseEntity<VehiculoDTO> crearAuto(@RequestBody @Valid CrearVehiculoDTO dto) {
        Vehiculo creado = this.autoService.crearAuto(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location).body(this.autoMapper.toDTO(creado));
    }

    // DELETE /autos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAuto(@PathVariable Long id) {
        this.autoService.eliminarAuto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

