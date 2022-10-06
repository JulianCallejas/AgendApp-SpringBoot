
package com.agendapp.services;

import com.agendapp.AgendAppSpringApplication;
import com.agendapp.entities.Agenda;
import com.agendapp.entities.Tarea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.agendapp.repositories.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class AendaServiceTest implements CommandLineRunner{
    
//    public static void main(String[] args) {
//		SpringApplication.run(AgendAppSpringApplication.class, args);
//	}
//    
//    @Autowired
//    private AgendaServiceImpl agendaService;

    @Override
    public void run(String... args) throws Exception {
//        List<Agenda> agenda;
//        agenda = agendaService.listarAgendas();
//        System.out.println(agenda);
//        
//        Optional<Agenda> agid = agendaService.buscarAgendaPorId(10);
//        System.out.println(agid);
//        
//        List<Agenda> agus;
//        agus = agendaService.buscarAgendasPorUsuario("pepe");
//        
//        System.out.println(agus);
//        System.out.println(agus.get(0).getTarea());
        
    }

    
}
