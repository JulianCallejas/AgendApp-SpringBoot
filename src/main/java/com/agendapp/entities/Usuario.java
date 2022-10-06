
package com.agendapp.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario {
    
    @Id  //Establece el id de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY )  //Genera autonumerico
    private long id;
    
    @Column(name = "Usuario", nullable = false, length = 15, unique = true, insertable = false, updatable = false) 
    private String usuario;
    
    @Column(name = "Email", nullable = false, length = 45, unique = true)   
    private String email;
    
    @Column(name = "Contrasena", nullable = false, length = 65)   
    private String contrasena;
    
    @Column(name = "Administrador")   
    private boolean administrador;

    public Usuario() {
        super();
    }

    public Usuario(String usuario, String contrasena) {
        super();
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public Usuario(String usuario, String email, String contrasena, boolean administrador) {
        super();
        this.usuario = usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.administrador = administrador;
    }

    public Usuario(long id, String usuario, String email, String contrasena, boolean administrador) {
        super();
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.administrador = administrador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", usuario=" + usuario + ", email=" + email + ", contrasena=" + contrasena + ", administrador=" + administrador + '}';
    }
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "Usuario")
    private Empleado empleado;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    
    
}
