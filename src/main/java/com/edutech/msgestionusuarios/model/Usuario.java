package com.edutech.msgestionusuarios.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityScan
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nameUsuario;
    @Column(name = "password", nullable = false)
    private String passUsuario;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private boolean estado = true;
    // private boolean activo =true;

    @ManyToMany
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Rol> roles = new ArrayList<>();

    public void activarCuenta() {
        this.estado = true;
    }

    public void desactivarCuenta() {
        this.estado = false;
    }

    // metodo de roles ,agregar,eliminar, limpiar
    public void agregarRol(Rol rol) {
        this.roles.add(rol);
        rol.getUsuarios().add(this);
    }

    public void removeRol(Rol rol) {
        this.roles.remove(rol);
        rol.getUsuarios().remove(this);
    }

    public void limpRol() {
        for (Rol rol : new ArrayList<>(roles)) {
            removeRol(rol);
        }
    }
}
