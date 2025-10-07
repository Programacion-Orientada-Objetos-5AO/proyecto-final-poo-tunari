package ar.edu.huergo.aguilar.borassi.tunari.repository.agencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.AutoStock;

//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface AutoStockRepository extends JpaRepository<AutoStock, Long> {
    // Metodo para buscar versiones por nombre, ignorando mayusculas y minusculas
}
