package com.agendapp.services;

import com.agendapp.entities.Usuario;
import com.agendapp.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll(Sort.by(Sort.Direction.ASC, "Usuario"));
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepo.findById(id);
    }

    @Override
    public Usuario buscarUsuarioPorUsuario(String usuario) {
        return usuarioRepo.findByUsuario(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorUsuarioContrasena(String usuario, String contrasena) {
        return usuarioRepo.findByUsuarioAndContrasena(usuario, contrasena);
    }

    @Override
    public List<Usuario> filtrarUsuarios(String filtro) {
        return usuarioRepo.findFilter(filtro);
    }

}
