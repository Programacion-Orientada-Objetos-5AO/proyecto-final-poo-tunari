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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.AutoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearAutoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Auto;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.AutoService;
import jakarta.validation.Valid;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.AutoMapper;

@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/autos") //El dominio con el que acciona el controller
public class AutoController {

    @Autowired
    private AutoService autoService;

    @Autowired
    private AutoMapper autoMapper;

     // GET /autos
    @GetMapping
    public ResponseEntity<List<AutoDTO>> obtenerTodosLosAutos() {
        List<Auto> autos = this.autoService.obtenerTodosLosAutos();
        return ResponseEntity.ok(this.autoMapper.toDTOList(autos));
    }

    // GET /autos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AutoDTO> obtenerAutoPorId(@PathVariable Long id) {
        Auto auto = this.autoService.obtenerAutoPorId(id);
        return ResponseEntity.ok(this.autoMapper.toDTO(auto));
    }

    // POST /autos
    @PostMapping
    public ResponseEntity<AutoDTO> crearAuto(@RequestBody @Valid CrearAutoDTO dto) {
        Auto creado = this.autoService.crearAuto(dto);
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

