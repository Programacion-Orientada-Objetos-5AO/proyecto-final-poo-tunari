package ar.edu.huergo.aguilar.borassi.tunari.repository.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import java.util.List;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    // Método para buscar colores por nombre, ignorando mayúsculas y minúsculas
    List<Color> findByNombreColorContainingIgnoreCase(String nombreColor);
    
}