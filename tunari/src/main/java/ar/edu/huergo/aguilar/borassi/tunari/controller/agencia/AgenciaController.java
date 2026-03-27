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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.CrearAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.ModificarStockAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.agencia.MostrarAgenciaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.agencia.AgenciaMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.agencia.AgenciaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;



@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/agencias") //El dominio con el que acciona el controller
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
        Agencia agenciaNueva = this.agenciaMapper.toEntity(dto);
        agenciaNueva = this.agenciaService.crearAgencia(agenciaNueva, dto.marcaId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(agenciaNueva.getId())
                .toUri();

        return ResponseEntity.created(location).body(this.agenciaMapper.toDTO(agenciaNueva));
    }

    public ResponseEntity<MostrarAgenciaDTO> actualizarAgencia(@PathVariable Long id, @RequestBody @Valid CrearAgenciaDTO dto) {

        Agencia datos = new Agencia();
        datos.setNombre(dto.nombre());
        datos.setUbicacion(dto.ubicacion());

        Agencia actualizada = agenciaService.actualizarAgencia(id, datos, dto.marcaId());
        return ResponseEntity.ok(agenciaMapper.toDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAgencia(@PathVariable Long id) {
        this.agenciaService.eliminarAgencia(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/stock")
    public ResponseEntity<MostrarAgenciaDTO> actualizarStock(@Valid @RequestBody ModificarStockAgenciaDTO autoStock) {
        Long autoId = autoStock.idAuto();
        int stock = autoStock.nuevoStock();
        Long agenciaId = autoStock.idAgencia();
        Agencia agenciaActualizada = agenciaService.actualizarStock(agenciaId, autoId, stock);
        return ResponseEntity.ok(agenciaMapper.toDTO(agenciaActualizada));
    }
    
}
