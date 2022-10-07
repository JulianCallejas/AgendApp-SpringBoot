
package com.agendapp.services;

import com.agendapp.entities.Agenda;
import com.agendapp.repositories.AgendaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaServiceImpl implements AgendaService{
    
    @Autowired
    private AgendaRepository agendaRepo;
    

    @Override
    public List<Agenda> listarAgendas() {
        return agendaRepo.findAll();
        
    }

    @Override
    public Agenda guardarAgenda(Agenda agenda) {
        return agendaRepo.save(agenda);
    }

    @Override
    public Optional<Agenda> buscarAgendaPorId(long idAgenda) {
        return agendaRepo.findById(idAgenda);
    }

    @Override
    public List<Agenda> buscarAgendasPorUsuario(String usuario) {
        return agendaRepo.findByUsuario(usuario);
    }
    
    
}
