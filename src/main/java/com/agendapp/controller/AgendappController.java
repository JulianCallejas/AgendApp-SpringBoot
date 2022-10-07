package com.agendapp.controller;

import com.agendapp.encriptador.Encriptador;
import com.agendapp.entities.Agenda;
import com.agendapp.entities.Tarea;
import com.agendapp.entities.Usuario;
import com.agendapp.entities.Usulog;
import com.agendapp.services.AgendaServiceImpl;
import com.agendapp.services.EmpleadoServiceImpl;
import com.agendapp.services.TareaServiceImpl;
import com.agendapp.services.UsuarioServiceImpl;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AgendappController {

    private Usuario usuarioLogueado;
    private Usuario usuarioConsulta;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private EmpleadoServiceImpl empleadoService;

    @Autowired
    private AgendaServiceImpl agendaService;

    @Autowired
    private TareaServiceImpl tareaService;

    private void setUsuarioLogueado() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = null;
        if (principal instanceof UserDetails) {
            userDetails = (UserDetails) principal;
        }
        this.usuarioLogueado = usuarioService.buscarUsuarioPorUsuario(userDetails.getUsername());
        this.usuarioLogueado.setContrasena("");
        this.usuarioConsulta = this.usuarioLogueado;

    }

    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogIn(Model modelo) {
        modelo.addAttribute("usuario", new Usulog());
        return "login";
    }

    @GetMapping("/cerrar-sesion")
    public String getCerrarSesion(Model modelo) {
        this.usuarioLogueado = null;
        this.usuarioConsulta = null;
        return "redirect:/login";
    }

    @GetMapping("/inicio")
    public String getInicio(Model modelo) {

        if (this.usuarioLogueado == null) {
            setUsuarioLogueado();
        }

        if (this.usuarioLogueado.isAdministrador()) {
            return "redirect:/admin/dashBoard";
        } else {
            return "redirect:/dashBoard";
        }

    }

    @GetMapping("/dashBoard")
    public String getdashBoard(Model modelo) {
        long pagina = 1;

        Agenda agenda = new Agenda();
        agenda.setIdAgenda(this.agendaService.buscarAgendasPorUsuario(usuarioConsulta.getUsuario()).get(0).getIdAgenda());
        agenda.setTarea(tareaService.buscarTareasPorAgenda(agenda.getIdAgenda()));

        long totalTareas = agenda.getTarea().size();
        totalTareas = totalTareas > ((pagina * 3) - 1) ? ((pagina * 3) - 1) : totalTareas - 1;

        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("agenda", agenda);
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("totalTareas", totalTareas);

        return "dashboard";
    }

    @GetMapping("/dashBoard/{pagina}")
    public String getdashBoardId(@PathVariable long pagina, Model modelo) {

        Agenda agenda = new Agenda();
        agenda.setIdAgenda(this.agendaService.buscarAgendasPorUsuario(usuarioConsulta.getUsuario()).get(0).getIdAgenda());
        agenda.setTarea(tareaService.buscarTareasPorAgenda(agenda.getIdAgenda()));

        long totalTareas = agenda.getTarea().size();
        totalTareas = totalTareas > ((pagina * 3) - 1) ? ((pagina * 3) - 1) : totalTareas - 1;

        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("agenda", agenda);
        modelo.addAttribute("pagina", pagina);
        modelo.addAttribute("totalTareas", totalTareas);

        return "dashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashBoard/usuario-{usuario}")
    public String getdashBoard(@PathVariable String usuario, Model modelo) {

        this.usuarioConsulta = usuarioService.buscarUsuarioPorUsuario(usuario);
        return "redirect:/dashBoard";

    }

    @GetMapping("/new-task")
    public String getNewTask(Model modelo) {
        Tarea tarea = new Tarea();
        tarea.setFecha(Date.valueOf(LocalDate.now()));
        tarea.setHora_inicio(("08:00:00"));
        tarea.setHora_final(("23:59:00"));
        modelo.addAttribute("tarea", tarea);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("mensaje", null);
        return "new-task";

    }

    @PostMapping("/new-task")
    public String postNewTask(@ModelAttribute("tarea") Tarea tarea, Model modelo) {
        String mensaje, resultado;
        resultado = "exito";
        mensaje = "Tarea creada correctamente";

        try {
            tarea.setFecha_creacion(Date.valueOf(LocalDate.now()));
            tarea.setUsuario(this.usuarioConsulta.getUsuario());
            tarea.setCreadaPor(this.usuarioLogueado.getUsuario());
            tarea.setIdAgenda(this.agendaService.buscarAgendasPorUsuario(usuarioConsulta.getUsuario()).get(0).getIdAgenda());
            tarea.setEstado("Pendiente");
            if (!tarea.compararHoras()) {
                resultado = "fallo";
                mensaje = "La hora final debe ser mayor que la hora inicial";
            }

            if (resultado == "exito") {
                tareaService.guardarTarea(tarea);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            resultado = "fallo";
            mensaje = e.toString();

        }
        modelo.addAttribute("mensaje", mensaje);
        modelo.addAttribute("resultado", resultado);
        return "new-task";
    }

    @GetMapping("/task/{taskId}")
    public String getTask(@PathVariable long taskId, Model modelo) {
        Tarea tarea = tareaService.buscarTareaPorId(taskId).get();

        if (!tarea.getUsuario().equals(this.usuarioConsulta.getUsuario())) {
            return "redirect:/dashBoard";
        }

        modelo.addAttribute("tarea", tarea);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("mensaje", null);
        return "edit-task";
    }

    @PostMapping("/task/{taskId}")
    public String postTask(@ModelAttribute("tarea") Tarea tarea, @PathVariable long taskId,
            @RequestParam(value = "estado", defaultValue = "false") boolean estado, Model modelo) {

        Tarea tareaExiste = tareaService.buscarTareaPorId(taskId).get();
        String resultado = "exito";
        String mensaje = "Tarea guardada correctamente";

        try {

            if (usuarioLogueado.isAdministrador() | tareaExiste.getUsuario().equals(tareaExiste.getCreadaPor())) {
                System.out.println("Modifica datos");
                tareaExiste.setFecha(tarea.getFecha());
                tareaExiste.setTitulo_tarea(tarea.getTitulo_tarea());
                tareaExiste.setDescripcion(tarea.getDescripcion());
            }
            tareaExiste.setHora_inicio(tarea.getHora_inicio());
            tareaExiste.setHora_final(tarea.getHora_final());

            tareaExiste.setComentarios(tarea.getComentarios());

            if (tareaExiste.getEstado().equals("Pendiente") & estado) {
                tareaExiste.setEstado("Completa");
                tareaExiste.setFecha_cierre(Date.valueOf(LocalDate.now()));
            } else if (tareaExiste.getEstado().equals("Completa") & !estado) {
                tareaExiste.setEstado("Pendiente");
            }

            if (!tareaExiste.compararHoras()) {
                resultado = "fallo";
                mensaje = "La hora final debe ser mayor que la hora inicial";
            }
            if (resultado == "exito") {
                tareaService.guardarTarea(tareaExiste);
            }

        } catch (Exception e) {
            resultado = "fallo";
            mensaje = e.toString();
        }
        modelo.addAttribute("tarea", tareaExiste);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("mensaje", mensaje);
        modelo.addAttribute("resultado", resultado);
        return "edit-task";
    }

    @PostMapping("/del-task/{taskId}")
    public String postDelTask(@PathVariable long taskId, Model modelo) {
        Tarea tarea = tareaService.buscarTareaPorId(taskId).get();
        String resultado = "exito";
        String mensaje = "Tarea eliminada correctamente";
        if (!tarea.getUsuario().equals(tarea.getCreadaPor()) & !usuarioLogueado.isAdministrador()) {
            resultado = "fallo";
            mensaje = "No tiene permisos para eliminar la tarea, contacte al Administrador";
        }

        if (resultado == "exito") {

            try {
                tareaService.eliminarTarea(tarea);
            } catch (Exception e) {
                resultado = "fallo";
                mensaje = e.toString();
            }
        }

        modelo.addAttribute("tarea", tarea);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("mensaje", mensaje);
        modelo.addAttribute("resultado", resultado);
        return "del-task";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/dashBoard")
    public String getAdminDashBoard(Model modelo) {

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        long pagina = 1;
        long totalUsuarios = usuarios.size();
        totalUsuarios = totalUsuarios > ((pagina * 3) - 1) ? ((pagina * 3) - 1) : totalUsuarios - 1;

        String filtro = "";

        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("filtro", filtro);
        modelo.addAttribute("pagina", pagina);

        modelo.addAttribute("totalUsuarios", totalUsuarios);
        return "admin-dashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/dashboard")
    public String postAdminDashBoard(@RequestParam(value = "filtro", defaultValue = "") String filtro, Model modelo) {

        if (filtro.length() == 0) {
            return "redirect:/admin/dashBoard";
        }

        List<Usuario> usuarios = usuarioService.filtrarUsuarios(filtro);
        long pagina = 1;
        long totalUsuarios = usuarios.size() - 1;

//        totalUsuarios = totalUsuarios > ((pagina * 3) - 1) ? ((pagina * 3) - 1) : totalUsuarios - 1;
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("filtro", filtro);
        modelo.addAttribute("pagina", pagina);

        modelo.addAttribute("totalUsuarios", totalUsuarios);
        return "admin-dashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/dashboard/{pagina}")
    public String getAdminDashBoardId(@PathVariable long pagina, Model modelo) {

        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();

            long totalUsuarios = usuarios.size();
            totalUsuarios = totalUsuarios > ((pagina * 3) - 1) ? ((pagina * 3) - 1) : totalUsuarios - 1;

            String filtro = "";

            modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
            modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
            modelo.addAttribute("usuarios", usuarios);
            modelo.addAttribute("filtro", filtro);
            modelo.addAttribute("pagina", pagina);

            modelo.addAttribute("totalUsuarios", totalUsuarios);
            return "admin-dashboard";

        } catch (Exception e) {
            System.out.println(e.toString());
            return "admin-dashboard";
        }

    }

    @GetMapping("/new-user")
    public String crearUsuario(@PathVariable String usuario, Model modelo) {
        modelo.addAttribute("usuario", usuarioService.buscarUsuarioPorUsuario(usuario));
        return "ususario";
    }

    @GetMapping("/user/{usuario}")
    public String editarUsuario(@PathVariable String usuario, Model modelo) {
        modelo.addAttribute("usuario", usuarioService.buscarUsuarioPorUsuario(usuario));
        return "ususario";
    }

}
