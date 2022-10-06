
package com.agendapp.services;

import com.agendapp.entities.Tarea;
import com.agendapp.repositories.TareaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class TareaServiceImpl implements TareaService {
    
    @Autowired
    private TareaRepository tareaRepo;
    

    @Override
    public List<Tarea> listarTareas() {
        return tareaRepo.findAll();
    }

    @Override
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepo.save(tarea);
    }

    @Override
    public Optional<Tarea> buscarTareaPorId(long id_tarea) {
        return tareaRepo.findById(id_tarea);
    }

    @Override
    public List<Tarea> buscarTareasPorUsuario(String usuario) {
        return tareaRepo.findByUsuario(usuario);
    }

    @Override
    public List<Tarea> buscarTareasPorAgenda(long idAgenda) {
        return tareaRepo.findByIdAgenda(idAgenda);
    }

    @Override
    public void eliminarTarea(Tarea tarea) {
        tareaRepo.delete(tarea);
    }

    
    
    
    
}
