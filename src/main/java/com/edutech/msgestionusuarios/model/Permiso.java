package com.edutech.msgestionusuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permiso")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_permiso", nullable = false, length = 100)
    private tipoPermiso nombrePermiso;

    @Enumerated(EnumType.STRING)
    @Column(name = "descripcion", nullable = false, length = 100)
    private tipoDescripcion descripcion;

    public enum tipoDescripcion {
        LECTURA, ESCRITURA, ADMINISTRACION, EJECUCION
    }

    public enum tipoPermiso {
        CREATE, READ, UPDATE, DELETE, MANAGE_USERS
    }
}
