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
    private TipoPermiso nombrePermiso;

    @Enumerated(EnumType.STRING)
    @Column(name = "descripcion", nullable = false, length = 100)
    private TipoAcceso tipoAcceso;

    public enum TipoAcceso {
        LECTURA, ESCRITURA, ADMINISTRACION, EJECUCION
    }

    public enum TipoPermiso {
        CREATE, READ, UPDATE, DELETE, MANAGE_USERS
    }

    @Column(nullable = false)
    private boolean estadoPermiso = true;

    public void activarPermiso(int id) {
        this.estadoPermiso = true;
    }

    public void desactivarPermiso(int id) {
        this.estadoPermiso = false;
    }

}
