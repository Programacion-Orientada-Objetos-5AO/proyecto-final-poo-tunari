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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearVehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.VehiculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.VehiculoMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.VehiculoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private VehiculoMapper vehiculoMapper;

    // GET /vehiculos
    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> obtenerTodosLosVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.obtenerTodosLosVehiculos();
        return ResponseEntity.ok(vehiculoMapper.toDTOList(vehiculos));
    }

    // GET /vehiculos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> obtenerVehiculoPorId(@PathVariable Long id) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(id);
        return ResponseEntity.ok(vehiculoMapper.toDTO(vehiculo));
    }

    // POST /vehiculos  (actualizado)
    @PostMapping
    public ResponseEntity<VehiculoDTO> crearVehiculo(@RequestBody @Valid CrearVehiculoDTO dto) {

        Vehiculo vehiculo = new Vehiculo();

        Vehiculo creado = vehiculoService.crearVehiculo(
            vehiculo,
            dto.getMarcaId(),
            dto.getModeloId(),
            dto.getColorId(),
            dto.getVersionId(),
            dto.getTipo(),
            dto.getMedicionBaul()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        // Devolver el DTO mapeado
        return ResponseEntity.created(location).body(vehiculoMapper.toDTO(creado));
    }

    // DELETE /vehiculos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
