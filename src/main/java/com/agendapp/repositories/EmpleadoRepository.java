package com.agendapp.repositories;

import com.agendapp.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

    public Empleado findByUsuario(String usuario);

}
