package com.agendapp.services;

import com.agendapp.entities.Rol;
import com.agendapp.entities.Usuario;
import com.agendapp.entities.Usulog;
import com.agendapp.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usulog userDetails = null;
        try {
            Usuario usuario = usuarioRepo.findByUsuario(username);
            if (usuario != null){
                userDetails = new Usulog(usuario.getUsuario(), usuario.getContrasena(),usuario.isAdministrador() ? Rol.ROLE_ADMIN : Rol.ROLE_USER);
            }else{
                userDetails = null;
            }
            
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return userDetails;
    }

}
