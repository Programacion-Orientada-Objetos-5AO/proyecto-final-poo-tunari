package ar.edu.huergo.aguilar.borassi.tunari.service.calculadora;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.aguilar.borassi.tunari.dto.calculadora.EstadisticasDTO;
import ar.edu.huergo.aguilar.borassi.tunari.entity.calculadora.Calculo;
import ar.edu.huergo.aguilar.borassi.tunari.repository.calculadora.CalculoRepository;

@Service
public class CalculoService {
    @Autowired
    private CalculoRepository calculoRepository;


    public List<Calculo> obtenerTodosLosCalculos() {
        return calculoRepository.findAll();
    }

    public Calculo calcular(Calculo calculo){
        if (calculo.getOperacion().equalsIgnoreCase("Sumar")) {
            calculo.sumar();
            return calculoRepository.save(calculo);
        }
        if (calculo.getOperacion().equalsIgnoreCase("Restar")) {
            calculo.restar();
            return calculoRepository.save(calculo);
        }
        if (calculo.getOperacion().equalsIgnoreCase("Multiplicar")) {
            calculo.multiplicar();
            return calculoRepository.save(calculo);
        }
        if (calculo.getOperacion().equalsIgnoreCase("Dividir")) {
            calculo.dividir();
            return calculoRepository.save(calculo);
        }

        throw new IllegalArgumentException("Operacion incorrecta");
    }

    public List<Calculo> obtenerUltimosCincoCalculos() {
        return calculoRepository.findCincoUltimosCalculos();
    }

    public Map<String, Long> cantidadPorOperacion() {
        Map<String, Long> map = new HashMap<>();
        Long cantidad = calculoRepository.contarPorNombre("Sumar");
        map.put("Sumar", cantidad);
        cantidad = calculoRepository.contarPorNombre("Restar");
        map.put("Restar", cantidad);
        cantidad = calculoRepository.contarPorNombre("Multiplicar");
        map.put("Multiplicar", cantidad);
        cantidad = calculoRepository.contarPorNombre("Dividir");
        map.put("Dividir", cantidad);
        return map;

    }

    public Double promedioResultados() {
        return calculoRepository.promedioResultados();
    }
    
    public Long cantidadOperaciones(){
        return calculoRepository.count();
    }

    public EstadisticasDTO estadisticas(){
        Double promedioResultados = promedioResultados();
        Long cantidadOperaciones = cantidadOperaciones();
        Map<String, Long> map = cantidadPorOperacion();
        EstadisticasDTO estadisticas = new EstadisticasDTO(null, promedioResultados, cantidadOperaciones, map);
        return estadisticas;
    }

}