package ar.edu.huergo.aguilar.borassi.tunari.entity.calculadora;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "calculos")
public class Calculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La operacion es obligatoria")
    @Column(nullable = false)
    private String operacion;

    @NotNull(message = "El parametro uno es obligatorio")
    @Column(nullable = false)
    private Double parametroUno;

    @NotNull(message = "El parametro 2 es obligatorio")
    @Column(nullable = false)
    private Double parametroDos;

    @NotNull(message = "El parametro 2 es obligatorio")
    @Column(nullable = false)
    private Double resultado;

    public void sumar() {
        Double resultadoSuma = parametroUno + parametroDos;
        this.resultado = resultadoSuma;
    }
    public void restar() {
        Double resultadoResta = parametroUno - parametroDos;
        this.resultado = resultadoResta;
    }
    public void multiplicar() {
        Double resultadoMultiplicacion = parametroUno * parametroDos;
        this.resultado = resultadoMultiplicacion;
    }
    public void dividir() {
        Double resultadoDivision = parametroUno / parametroDos;
        this.resultado = resultadoDivision;
    }
}
