
package com.agendapp.services;

import com.agendapp.entities.Empleado;
import com.agendapp.repositories.EmpleadoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepo;
    
    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepo.findAll();
    }

    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepo.save(empleado);
    }

    @Override
    public Optional<Empleado> buscarEmpleadoPorId_Empleado(String id_empleado) {
        return empleadoRepo.findById(id_empleado);
    }

    @Override
    public Empleado buscarEmpleadoPorUsuario(String usuario) {
        return empleadoRepo.findByUsuario(usuario);
    }
    
}
