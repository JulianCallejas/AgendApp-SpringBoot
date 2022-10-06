
package com.agendapp.services;

import com.agendapp.entities.Empleado;
import java.util.List;
import java.util.Optional;


public interface EmpleadoService {
    
    public List<Empleado> listarEmpleados();
    
    public Empleado guardarEmpleado(Empleado empleado);
    
    public Optional<Empleado> buscarEmpleadoPorId_Empleado(String id_empleado);
    
    public Empleado buscarEmpleadoPorUsuario(String usuario);
    
    
}
