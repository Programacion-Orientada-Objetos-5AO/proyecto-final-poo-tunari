package ar.edu.huergo.aguilar.borassi.tunari.repository.auto;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Color;
import java.util.List;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findByNombreContainingIgnoreCase(String nombreColor);
}