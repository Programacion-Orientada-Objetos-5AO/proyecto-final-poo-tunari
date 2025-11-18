package ar.edu.huergo.aguilar.borassi.tunari.repository.calculadora;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.calculadora.Calculo;


//CrudRepository es una interfaz que proporciona metodos para CRUD (Create, Read, Update, Delete)
@Repository
public interface CalculoRepository extends JpaRepository<Calculo, Long> {
    
    @Query(value = "Select * from calculos order by Id DESC limit 5", nativeQuery=true)
    List<Calculo> findCincoUltimosCalculos();
}