package ar.edu.huergo.aguilar.borassi.tunari.repository.auto;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Version;
import java.util.List;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findByNombreContainingIgnoreCase(String nombreVersion);
}
