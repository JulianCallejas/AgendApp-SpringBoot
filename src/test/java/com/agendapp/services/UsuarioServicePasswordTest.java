
package com.agendapp.services;


import com.agendapp.AgendAppSpringApplication;
import com.agendapp.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.agendapp.repositories.UsuarioRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UsuarioServicePasswordTest implements CommandLineRunner{
    
//    public static void main(String[] args) {
//		SpringApplication.run(AgendAppSpringApplication.class, args);
//	}
    
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
//        return bCryptPasswordEncoder;
//    }
    
//    @Autowired
//    private UsuarioServiceImpl usuarioService;
//    
//    
    

    @Override
    public void run(String... args) throws Exception {
//        List<Usuario> usuarios;
//        usuarios = usuarioService.listarUsuarios();
//        System.out.println(usuarios);
//        
//        Usuario user = usuarioService.buscarUsuarioPorUsuario("angel");
//        System.out.println(user);
//        
//        Usuario userlog = usuarioService.buscarUsuarioPorUsuarioContrasena("angel", "1234");
//        System.out.println(userlog);
//        
//        Usuario userlog1 = usuarioService.buscarUsuarioPorUsuarioContrasena("angel", "12354");
//        System.out.println(userlog1);
//        
        
//        System.out.println(passwordEncoder().encode(userlog.getContrasena()));

        
    }
    
    
    
}
