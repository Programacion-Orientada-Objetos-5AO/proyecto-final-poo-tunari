package ar.edu.huergo.aguilar.borassi.tunari.repository.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {
    // Metodo para buscar versiones por nombre, ignorando mayusculas y minusculas
    java.util.List<Version> findByNombreVersionContainingIgnoreCase(String nombreVersion);
}
