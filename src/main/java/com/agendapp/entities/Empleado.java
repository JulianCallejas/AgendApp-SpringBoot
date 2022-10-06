
package com.agendapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="empleados")
public class Empleado {
    
    
    @Column(name = "Id_Empleado", nullable = false, length = 12, unique = true)
    private String id_empleado;
    
    @Id
    @Column(name = "Usuario", nullable = false, length = 15, unique = true)
    private String usuario;
    
    @Column(name = "Nombre", nullable = true, length = 45, unique = false)
    private String nombre;
    
    @Column(name = "Apellido", nullable = true, length = 45, unique = false)
    private String apellido;
    
    @Column(name = "Cargo", nullable = true, length = 45, unique = false)
    private String cargo;

    public Empleado() {
        super();
    }

    public Empleado(String usuario, String nombre, String apellido, String cargo) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
    }

    public Empleado(String usuario) {
        this.usuario = usuario;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id_empleado=" + id_empleado + ", usuario=" + usuario + ", nombre=" + nombre + ", apellido=" + apellido + ", cargo=" + cargo + '}';
    }
    
    public String getNombreCompleto(){
        return this.nombre + " " + this.apellido;
    }
    
}
