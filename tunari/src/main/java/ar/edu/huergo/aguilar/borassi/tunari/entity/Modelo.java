package ar.edu.huergo.aguilar.borassi.tunari.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Data
@AllArgsConstructor
@Entity
@Table(name = "modelos")
public class Modelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del modelo no puede estar vacío")
    private String nombreModelo;

    @NotBlank(message = "La marca a la cual pertenece no puede estar vacía")
    private String marca;

    @OneToMany
    @JoinTable(name = "autos_marcas", 
            joinColumns = @JoinColumn(name = "modelo_id"),
            inverseJoinColumns = @JoinColumn(name = "marca_id"))
    private List<Marca> marcas;

    @OneToMany
    @JoinTable(name = "autos_versiones", 
            joinColumns = @JoinColumn(name = "modelo_id"),
            inverseJoinColumns = @JoinColumn(name = "version_id"))
    private List<Version> versiones;

}

