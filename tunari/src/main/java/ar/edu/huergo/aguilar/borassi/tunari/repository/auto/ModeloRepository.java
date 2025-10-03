package ar.edu.huergo.aguilar.borassi.tunari.repository.auto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Modelo;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    // Método para buscar colores por nombre, ignorando mayúsculas y minúsculas
    List<Modelo> findByNombreModeloContainingIgnoreCase(String nombreModelo);
    boolean existsByIdAndVersiones_Id(Long modeloId, Long versionId);
    boolean existsByIdAndColores_Id(Long modeloId, Long colorId);
}
