package com.edutech.msgestionusuarios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.msgestionusuarios.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Rol findById(int idrol);

    boolean findByNombre(String nombreRol);

    // List<Rol> findAllById(Iterable<Integer> ids);
    List<Rol> findAll(Rol rol);

    // crear
    // Rol save(Rol rol);

    // read
    // List<Rol> findAll();

    // find by id
    // Rol findByID(Integer idrol);

    // delete by id
    // void deleteById(Integer idrol);

}
