package com.edutech.msgestionusuarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.msgestionusuarios.model.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    Permiso findById(int idpermiso);

    List<Permiso> findAll(Permiso permiso);

}
