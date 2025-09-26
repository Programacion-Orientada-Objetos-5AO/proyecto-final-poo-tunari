package ar.edu.huergo.aguilar.borassi.tunari.repository.publicacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.publicacion.Publicacion;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
}