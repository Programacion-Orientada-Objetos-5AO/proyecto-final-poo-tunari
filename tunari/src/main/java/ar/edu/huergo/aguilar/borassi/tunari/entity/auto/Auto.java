package ar.edu.huergo.aguilar.borassi.tunari.entity.auto;

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
@Table(name = "autos")
public class Auto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // Un auto pertenece a UNA marca
        @ManyToOne(optional = false)
        @JoinColumn(name = "marca_id", nullable = false)
        @NotNull(message = "La marca no puede estar vacía")
        private Marca marca;

        // Un auto pertenece a UN modelo
        @ManyToOne(optional = false)
        @JoinColumn(name = "modelo_id", nullable = false)
        @NotNull(message = "El modelo no puede estar vacío")
        private Modelo modelo;

        // Un auto tiene UN color
        @ManyToOne(optional = false)
        @JoinColumn(name = "color_id", nullable = false)
        @NotNull(message = "El color no puede estar vacío")
        private Color color;

        // Un auto tiene UNA versión
        @ManyToOne(optional = false)
        @JoinColumn(name = "version_id", nullable = false)
        @NotNull(message = "La versión no puede estar vacía")
        private Version version;


        public Auto() {
        }

}
