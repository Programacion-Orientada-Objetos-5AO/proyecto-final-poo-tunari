package ar.edu.huergo.aguilar.borassi.tunari.repository.agencia;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.agencia.Agencia;//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {
    List<Agencia> findByNombreContainingIgnoreCase(String nombreAgencia);
}
