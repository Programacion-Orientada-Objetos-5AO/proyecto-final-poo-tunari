package ar.edu.huergo.aguilar.borassi.tunari.repository.tarea;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.aguilar.borassi.tunari.entity.tarea.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
}
   