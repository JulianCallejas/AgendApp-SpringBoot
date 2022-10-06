
package com.agendapp.entities;

import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="agendas")
public class Agenda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAgenda;
    
    @Column(name = "Usuario", nullable = false, length = 15, unique = false) 
    private String usuario;

    public Agenda() {
        super();
    }

    public Agenda(long idAgenda, String usuario) {
        this.idAgenda = idAgenda;
        this.usuario = usuario;
    }

    public Agenda(long idAgenda) {
        this.idAgenda = idAgenda;
    }

    public long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(long idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Agenda{" + "idAgenda=" + idAgenda + ", usuario=" + usuario + '}';
    }

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn (name="IdAgenda", referencedColumnName="IdAgenda")
    private List<Tarea> tarea;

    public List<Tarea> getTarea() {
        return tarea;
    }

    public void setTarea(List<Tarea> tarea) {
        this.tarea = tarea;
    }
    
}
