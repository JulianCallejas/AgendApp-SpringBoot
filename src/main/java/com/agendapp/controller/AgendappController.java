package com.agendapp.controller;

import com.agendapp.entities.Agenda;
import com.agendapp.entities.Empleado;
import com.agendapp.entities.Tarea;
import com.agendapp.entities.Usuario;
import com.agendapp.entities.Usulog;
import com.agendapp.services.AgendaServiceImpl;
import com.agendapp.services.EmpleadoServiceImpl;
import com.agendapp.services.TareaServiceImpl;
import com.agendapp.services.UsuarioServiceImpl;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    BCryptPasswordEncoder encriptadorService;

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
        modelo.addAttribute("mensaje", null);
        return "login";
    }

    @GetMapping("/login/no-autorizado")
    public String getLogInNoAutorizado(Model modelo) {
        modelo.addAttribute("mensaje", "Usuario o contraseña invalidos");
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
        System.out.println(totalTareas);

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

            if (resultado.equals("exito")) {
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
            if (resultado.equals("exito")) {
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

        if (resultado.equals("exito")) {

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
        filtro = usuarios.size() == 0 ? "null" : filtro;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/new-user")
    public String getCrearUsuario(Model modelo) {

        modelo.addAttribute("usuario", null);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("mensaje", null);

        return "new-ususario";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/new-user")
    public String postCrearUsuario(@RequestParam(value = "fusuario", defaultValue = "") String fusuario,
            @RequestParam(value = "fcontrasena", defaultValue = "") String fcontrasena,
            @RequestParam(value = "fcontrasena2", defaultValue = "") String fcontrasena2,
            @RequestParam(value = "femail", defaultValue = "") String femail,
            @RequestParam(value = "fnumeroid", defaultValue = "") String fnumeroid,
            @RequestParam(value = "fnombre", defaultValue = "") String fnombre,
            @RequestParam(value = "fapellido", defaultValue = "") String fapellido,
            @RequestParam(value = "fcargo", defaultValue = "") String fcargo,
            Model modelo) {

        String resultado = "exito";
        String mensaje = "Usuario creado correctamente";

        try {

            if (!fcontrasena.equals(fcontrasena2)) {
                resultado = "fallo";
                mensaje = "La contraseña no coincide";
            }
            if (resultado.equals("exito")) {
                Usuario nuevoUsuario = usuarioService.crearUsuario(fusuario, femail, fcontrasena, false, fnumeroid, fnombre, fapellido, fcargo);
                Empleado nuevoEmpleado = new Empleado(fnumeroid, fusuario, fnombre, fapellido, fcargo);
                Agenda nuevaAgenda = new Agenda(fusuario);
                System.out.println(nuevoUsuario);
                System.out.println(nuevoEmpleado);
                System.out.println(nuevaAgenda);

            }

        } catch (Exception e) {
            resultado = "fallo";
            mensaje = e.toString();
        }

        modelo.addAttribute("usuario", null);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("resultado", resultado);
        modelo.addAttribute("mensaje", mensaje);

        return "new-ususario";
    }

    @GetMapping("/user/{usuario}")
    public String getEditarUsuario(@PathVariable String usuario, Model modelo) {

        modelo.addAttribute("usuario", usuarioService.buscarUsuarioPorUsuario(usuario));
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("mensaje", null);

        return "ususario";
    }

    @PostMapping("/user/{usuario}")
    public String postEditarUsuario(@PathVariable String usuario,
            @RequestParam(value = "fcontrasena", defaultValue = "") String fcontrasena,
            @RequestParam(value = "fcontrasena2", defaultValue = "") String fcontrasena2,
            @RequestParam(value = "femail", defaultValue = "") String femail,
            @RequestParam(value = "fnumeroid", defaultValue = "") String fnumeroid,
            @RequestParam(value = "fnombre", defaultValue = "") String fnombre,
            @RequestParam(value = "fapellido", defaultValue = "") String fapellido,
            @RequestParam(value = "fcargo", defaultValue = "") String fcargo,
            Model modelo) {

        String resultado = "exito";
        String mensaje = "Usuario guardado correctamente";
        Usuario usuarioExiste = usuarioService.buscarUsuarioPorUsuario(usuario);
        Empleado empleado = empleadoService.buscarEmpleadoPorUsuario(usuario);

        try {
            if (fcontrasena.length() > 0) {
                System.out.println(fcontrasena);
                System.out.println(fcontrasena2);
                if (fcontrasena.equals(fcontrasena2)) {
                    usuarioExiste.setContrasena(encriptadorService.encode(fcontrasena));
                } else {
                    resultado = "fallo";
                    mensaje = "La contraseña no coincide";
                }
            }

            usuarioExiste.setEmail(femail);
            empleado.setId_empleado(fnumeroid);
            empleado.setNombre(fnombre);
            empleado.setApellido(fapellido);
            empleado.setCargo(fcargo);

            if (resultado.equals("exito")) {
                usuarioService.guardarUsuario(usuarioExiste);
                empleadoService.guardarEmpleado(empleado);
            }

        } catch (Exception e) {
            resultado = "fallo";
            mensaje = e.toString();
        }

        modelo.addAttribute("usuario", usuarioExiste);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("resultado", resultado);
        modelo.addAttribute("mensaje", mensaje);

        return "ususario";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/del-user/{usuario}")
    public String postBorrarUsuario(@PathVariable String usuario, Model modelo) {

        Usuario usuarioBorrar = usuarioService.buscarUsuarioPorUsuario(usuario);

        String resultado = "exito";
        String mensaje = "Usuario borrado correctamente";

        try {
            Usuario usuborrado = usuarioService.borrarUsuarioPorUsuario(usuarioBorrar.getUsuario());
        } catch (Exception e) {
            resultado = "fallo";
            mensaje = e.toString();
        }

        modelo.addAttribute("usuario", usuarioBorrar);
        modelo.addAttribute("usuarioLogueado", this.usuarioLogueado);
        modelo.addAttribute("usuarioConsulta", this.usuarioConsulta);
        modelo.addAttribute("resultado", resultado);
        modelo.addAttribute("mensaje", mensaje);

        return "del-ususario";
    }

}
