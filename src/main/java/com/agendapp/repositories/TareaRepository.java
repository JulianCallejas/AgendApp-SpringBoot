package com.agendapp.repositories;

import com.agendapp.entities.Tarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;




@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long>{
    
    
    public List<Tarea> findByUsuario(String usuario);
    
    @Query(value = "SELECT * FROM tareas t WHERE t.Id_Agenda LIKE ?1 ORDER BY t.Estado DESC, t.Fecha DESC; ", nativeQuery = true)
    public List<Tarea> findByIdAgenda(long idAgenda);
    
    
}
