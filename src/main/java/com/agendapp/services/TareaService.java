package com.agendapp.services;

import com.agendapp.entities.Tarea;
import java.util.List;
import java.util.Optional;

public interface TareaService {

    public List<Tarea> listarTareas();

    public Tarea guardarTarea(Tarea tarea);
    
    public void eliminarTarea(Tarea tarea);

    public Optional<Tarea> buscarTareaPorId(long id_tarea);

    public List<Tarea> buscarTareasPorUsuario(String usuario);

    public List<Tarea> buscarTareasPorAgenda(long  idAgenda);

}
