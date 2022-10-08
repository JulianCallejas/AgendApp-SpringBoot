package com.agendapp.services;

import com.agendapp.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    public List<Usuario> listarUsuarios();

    public Usuario guardarUsuario(Usuario usuario);

    public Optional<Usuario> buscarUsuarioPorId(Long id);

    public Usuario buscarUsuarioPorUsuario(String usuario);

    public Usuario buscarUsuarioPorUsuarioContrasena(String usuario, String contrasena);

    public List<Usuario> filtrarUsuarios(String filtro);

    public Usuario borrarUsuarioPorUsuario(String usuario);

    public Usuario crearUsuario(String usuario, String email, String contrasena, boolean administrador, String Id_Empleado, String nombre, String apellido, String cargo);
}
