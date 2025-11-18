package ar.edu.huergo.aguilar.borassi.tunari.controller.tarea;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

import ar.edu.huergo.aguilar.borassi.tunari.dto.tarea.CrearTareaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.tarea.TareaDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.tarea.Tarea;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.tarea.TareaMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.tarea.TareaService;

@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/api/tareas") //El dominio con el que acciona el controller
public class TareaController {

    @Autowired
    private TareaService tareaService;
    @Autowired
    private TareaMapper tareaMapper;

    @GetMapping 
    public ResponseEntity<List<TareaDTO>> obtenerTodasLasTareas() {
        List<Tarea> tareas = this.tareaService.obtenerTodasLasTareas();
        return ResponseEntity.ok(this.tareaMapper.toDTOList(tareas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerTareaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tareaMapper.toDTO(tareaService.obtenerTareaPorId(id)));
    }

    @PostMapping
    public ResponseEntity<String> crearTarea(@RequestBody CrearTareaDTO tareaDTO) {
        Tarea tarea = tareaMapper.toEntity(tareaDTO);
        this.tareaService.crearTarea(tarea);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(tareaDTO.nombreTarea())
            .toUri();
        return ResponseEntity.created(location).body("Tarea creada correctamente");
        
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable Long id, @RequestBody CrearTareaDTO tareaDTO) throws NotFoundException {
        String nombreTarea = tareaDTO.nombreTarea();
        String descripcionTarea = tareaDTO.descripcionTarea();
        String creadorTarea = tareaDTO.creadorTarea();
        return ResponseEntity.ok(tareaMapper.toDTO(tareaService.actualizarTarea(id, nombreTarea, descripcionTarea, creadorTarea)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTarea (@PathVariable Long id){
        this.tareaService.eliminarTarea(id);
        return ResponseEntity.ok("Tarea eliminada correctamente");
    }
}
