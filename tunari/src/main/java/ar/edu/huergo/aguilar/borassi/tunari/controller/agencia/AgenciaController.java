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

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.MostrarAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.agencia.AgenciaMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.agencia.AgenciaService;
import jakarta.validation.Valid;



@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/agencia") //El dominio con el que acciona el controller
public class AgenciaController {
    @Autowired
    private AgenciaService agenciaService;

    @Autowired
    private AgenciaMapper agenciaMapper;

     // GET /agencias
    @GetMapping
    public ResponseEntity<List<MostrarAgenciaDTO>> obtenerTodosLosAgencias() {
        List<Agencia> agencias = this.agenciaService.obtenerTodasLasAgencias();
        return ResponseEntity.ok(this.agenciaMapper.toDTOList(agencias));
    }

    // GET /agencias/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MostrarAgenciaDTO> obtenerAgenciaPorId(@PathVariable Long id) {
        Agencia agencia = this.agenciaService.obtenerAgenciaPorId(id);
        return ResponseEntity.ok(this.agenciaMapper.toDTO(agencia));
    }

    // POST /agencias
    @PostMapping
    public ResponseEntity<MostrarAgenciaDTO> crearAgencia(@RequestBody @Valid CrearAgenciaDTO dto) {
        Agencia creado = this.agenciaService.crearAgencia(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location).body(this.agenciaMapper.toDTO(creado));
    }

    // DELETE /agencias/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAgencia(@PathVariable Long id) {
        this.agenciaService.eliminarAgencia(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    
}
