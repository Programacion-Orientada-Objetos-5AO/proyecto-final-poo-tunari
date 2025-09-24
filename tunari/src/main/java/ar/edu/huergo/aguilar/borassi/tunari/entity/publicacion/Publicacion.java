package ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion;

import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;
import ar.edu.huergo.aguilar.borassi.tunari.entity.security.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    @NotNull(message = "El modelo no puede estar vacío")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "El usuario no puede estar vacío")
    private Usuario usuario;

    @CurrentTimestamp
    private final LocalDateTime fecha;
}
