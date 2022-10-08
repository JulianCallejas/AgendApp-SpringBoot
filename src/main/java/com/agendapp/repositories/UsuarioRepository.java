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
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "CALL SP_SelectUsuariosWord (?)", nativeQuery = true)
    public List<Usuario> findFilter(String filtro);

    public Usuario findByUsuario(String usuario);

    public Usuario findByUsuarioAndContrasena(String usuario, String contrasena);

    @Query(value = "CALL SP_DeleteUser(?)", nativeQuery = true)
    public Usuario borrarPorUsuario(String Usuario);

    @Query(value = "CALL SP_CreateUser(?, ?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
    public Usuario crearUsuario(String usuario, String email, String contrasena, boolean administrador, String Id_Empleado, String nombre, String apellido, String cargo);

}
