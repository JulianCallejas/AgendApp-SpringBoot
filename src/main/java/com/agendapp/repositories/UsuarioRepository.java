/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.agendapp.repositories;

import com.agendapp.entities.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
    @Query(value = "SELECT * FROM usuarios u WHERE CONCAT(u.Usuario,  u.Email) LIKE %?1%", nativeQuery = true)
    public List<Usuario> findFilter(String palabraClave);
    
    public Usuario findByUsuario(String usuario);
    
    public Usuario findByUsuarioAndContrasena(String usuario, String contrasena);
    
}
