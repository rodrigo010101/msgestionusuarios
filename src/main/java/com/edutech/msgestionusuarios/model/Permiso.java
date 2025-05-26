package com.edutech.msgestionusuarios.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_permiso", nullable = false, length = 100)
    private tipoPermiso nombrePermiso;

    @Enumerated(EnumType.STRING)
    @Column(name = "descripcion", nullable = false, length = 100)
    private tipoDescripcion descripcion;

    @OneToMany(mappedBy = "permisos")
    private List<Rol> Rol = new ArrayList<>();

    private enum tipoDescripcion {
        LECTURA, ESCRITURA, ADMINISTRACION, EJECUCION
    }

    private enum tipoPermiso {
        CREATE, READ, UPDATE, DELETE, MANAGE_USERS
    }
}
