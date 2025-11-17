package ar.edu.huergo.aguilar.borassi.tunari.service.calculadora;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    

}