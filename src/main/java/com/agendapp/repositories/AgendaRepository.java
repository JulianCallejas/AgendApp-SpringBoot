
package com.agendapp.repositories;

import com.agendapp.entities.Agenda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{
    
    public List<Agenda> findByUsuario(String usuario);
    
    
    
}
