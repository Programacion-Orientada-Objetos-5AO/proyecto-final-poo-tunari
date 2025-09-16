package ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion;

import java.util.List;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name = "publicaciones")
public class Publicacion {
    

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    @NotNull(message = "El modelo no puede estar vacío")
    private Modelo modelo;

    @ManyToMany
    @JoinTable(name = "autos_agencias", 
            joinColumns = @JoinColumn(name = "auto_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> agencias;


}
