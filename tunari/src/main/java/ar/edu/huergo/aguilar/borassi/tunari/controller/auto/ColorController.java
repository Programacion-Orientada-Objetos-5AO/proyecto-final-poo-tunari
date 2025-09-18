package ar.edu.huergo.aguilar.borassi.tunari.controller.auto;


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

import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.ColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.auto.CrearColorDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.auto.ColorMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.auto.ColorService;

@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/api/colores") //El dominio con el que acciona el controller
public class ColorController {
    @Autowired
    private ColorService colorService;
    @Autowired
    private ColorMapper colorMapper;

    @GetMapping 
    public ResponseEntity<List<ColorDTO>> obtenerTodosLosColores() {
        List<Color> colors = this.colorService.obtenerTodosLosColores();
        return ResponseEntity.ok(this.colorMapper.toDTOList(colors));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorDTO> obtenerColorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(colorMapper.toDTO(colorService.obtenerColorPorId(id)));
    }

    @PostMapping
    public ResponseEntity<String> crearColor(@RequestBody CrearColorDTO colorDto) {
        this.colorService.crearColor(colorDto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(colorDto.nombreColor())
            .toUri();
        return ResponseEntity.created(location).body("Color creado correctamente");
        
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ColorDTO> actualizarColor(@PathVariable Long id, @RequestBody CrearColorDTO colorDto) throws NotFoundException {
        return ResponseEntity.ok(colorMapper.toDTO(colorService.actualizarColor(id, colorDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarColor (@PathVariable Long id){
        this.colorService.eliminarColor(id);
        return ResponseEntity.ok("Color eliminado correctamente");
    }
}
