package ar.edu.huergo.aguilar.borassi.tunari.controller.calculadora;


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

import ar.edu.huergo.aguilar.borassi.tunari.dto.calculadora.RequestCalculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.dto.calculadora.ResponseCalculoDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.calculadora.Calculo;
import ar.edu.huergo.aguilar.borassi.tunari.mapper.calculadora.CalculoMapper;
import ar.edu.huergo.aguilar.borassi.tunari.service.calculadora.CalculoService;



@RestController //Tipo de controller, en este caso un RESTful API controller
@RequestMapping("/api/calculos") //El dominio con el que acciona el controller
public class CalculoController {
    @Autowired
    private CalculoService calculoService;
    @Autowired
    private CalculoMapper calculoMapper;

    @GetMapping 
    public ResponseEntity<List<ResponseCalculoDTO>> obtenerTodosLosCalculos() {
        List<Calculo> calculos = this.calculoService.obtenerTodosLosCalculos();
        return ResponseEntity.ok(this.calculoMapper.toDTOList(calculos));
    }


    @PostMapping
    public ResponseEntity<ResponseCalculoDTO> hacerCalculo(@RequestBody RequestCalculoDTO calculoDto) {
        Calculo calculo = calculoMapper.toEntity(calculoDto);
        calculo = calculoService.calcular(calculo);
        return ResponseEntity.ok(calculoMapper.toDTO(calculo));
        
    }

    @GetMapping("/ultimosCinco")
    public ResponseEntity<List<ResponseCalculoDTO>> obtenerUltimosCincoCalculos() {
        List<Calculo> calculos = this.calculoService.obtenerUltimosCincoCalculos();
        return ResponseEntity.ok(this.calculoMapper.toDTOList(calculos));
    }
    
    

}
