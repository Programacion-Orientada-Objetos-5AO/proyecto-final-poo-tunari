package ar.edu.huergo.aguilar.borassi.tunari.repository.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.auto.Vehiculo;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}