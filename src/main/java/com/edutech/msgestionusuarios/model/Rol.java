package com.edutech.msgestionusuarios.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity
@Table(name = "rol")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "nombreRol", nullable = false, unique = true)
    private TipoRol nombreRol;

    // descripcion del rol , rol con todos los permisos,rol basico de usuario,puede
    // moderar contenido o invitado
    @Column(name = "descripcion", length = 150, nullable = false)
    private String descripcion;

    @ManyToMany
    @JoinTable(name = "rol_permiso", joinColumns = @JoinColumn(name = "rol_id"), inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private List<Permiso> permiso = new ArrayList<>();

    @Column(nullable = false)
    private boolean estadoRol = true;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<Usuario> usuarios = new ArrayList<>();

    public enum TipoRol {
        ADMIN,
        USER,
        MODERADOR,
        INVITADO
    }

    public void desactivarRol(int id) {
        this.estadoRol = false;
    }

    public void activarRol(int id) {
        this.estadoRol = true;
    }
}