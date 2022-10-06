
package com.agendapp.services;

import com.agendapp.entities.Agenda;
import java.util.List;
import java.util.Optional;


public interface AgendaService {
    
    public List<Agenda> listarAgendas();

    public Agenda guardarTarea(Agenda agenda);

    public Optional<Agenda> buscarAgendaPorId(long idAgenda);

    public List<Agenda> buscarAgendasPorUsuario(String usuario);
    
    
    
}
