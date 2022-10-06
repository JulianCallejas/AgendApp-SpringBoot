
package com.agendapp.entities;


import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tareas")
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_tarea;
    
    @Column(name = "Usuario", nullable = false, length = 15, unique = false) 
    private String usuario;
    
    @Column(name = "Creadapor", nullable = false, length = 15, unique = false) 
    private String creadaPor;
    
    @Column(name = "Fecha", nullable = false) 
    private Date fecha;
    
    @Column(name = "Hora_Inicio") 
    private String hora_inicio;
    
    @Column(name = "Hora_Final") 
    private String hora_final;
    
    @Column(name = "Titulo_Tarea", nullable = false, length = 45, unique = false) 
    private String titulo_tarea;
    
    @Column(name = "Descripcion", nullable = false, length = 255, unique = false) 
    private String descripcion;
    
    @Column(name = "Comentarios", nullable = true, unique = false) 
    private String comentarios;
    
    @Column(name = "Estado", nullable = true, length = 45, unique = false)
    private String estado;
    
    @Column(name = "Fecha_Creacion") 
    private Date fecha_creacion;
    
    @Column(name = "Fecha_Cierre") 
    private Date fecha_cierre;
    
    @Column(name = "IdAgenda", nullable = false)
    private long idAgenda;
    

    public long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(long idAgenda) {
        this.idAgenda = idAgenda;
    }
    

    
    public Tarea() {
        super();
    }

    public Tarea(String usuario, String creadaPor, Date fecha, String titulo_tarea, String descripcion, long idAgenda) {
        this.usuario = usuario;
        this.creadaPor = creadaPor;
        this.fecha = fecha;
        this.titulo_tarea = titulo_tarea;
        this.descripcion = descripcion;
        
    }

    public Tarea(long id_tarea, String usuario, String creadaPor, Date fecha, String hora_inicio, String hora_final, String titulo_tarea, String descripcion, String comentarios, String estado, Date fecha_creacion, Date fecha_cierre, long idAgenda) {
        this.id_tarea = id_tarea;
        this.usuario = usuario;
        this.creadaPor = creadaPor;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.titulo_tarea = titulo_tarea;
        this.descripcion = descripcion;
        this.comentarios = comentarios;
        this.estado = estado;
        this.fecha_creacion = fecha_creacion;
        this.fecha_cierre = fecha_cierre;
       
    }

    public long getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(long id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCreadaPor() {
        return creadaPor;
    }

    public void setCreadaPor(String creadaPor) {
        this.creadaPor = creadaPor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getTitulo_tarea() {
        return titulo_tarea;
    }

    public void setTitulo_tarea(String titulo_tarea) {
        this.titulo_tarea = titulo_tarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Date fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

   public boolean compararHoras(){
       
       if (this.hora_final == null){
           return true;
       }
       
       if (this.hora_inicio == null){
           return true;
       }else{
           float hi = Float.valueOf(this.hora_inicio.substring(0, 2)) + Float.valueOf(this.hora_inicio.substring(3, 5))/60;
           float hf = Float.valueOf(this.hora_final.substring(0, 2)) + Float.valueOf(this.hora_final.substring(3, 5))/60;
           
           if (hi>hf){
               return false;
           }
       }
       return true;
   }

    

    @Override
    public String toString() {
        return "Tarea{" + "id_tarea=" + id_tarea + ", usuario=" + usuario + ", creadaPor=" + creadaPor + ", fecha=" + fecha + ", hora_inicio=" + hora_inicio + ", hora_final=" + hora_final + ", titulo_tarea=" + titulo_tarea + ", descripcion=" + descripcion + ", comentarios=" + comentarios + ", estado=" + estado + ", fecha_creacion=" + fecha_creacion + ", fecha_cierre=" + fecha_cierre + '}';
    }
    
    
    
    
    
}
