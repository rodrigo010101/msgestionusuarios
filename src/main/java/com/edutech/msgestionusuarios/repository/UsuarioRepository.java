package com.edutech.msgestionusuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.msgestionusuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findById(Integer id);

    boolean findByNombre(String nombre);

    boolean findByApellido(String apellido);

    List<Usuario> findAllById(Iterable<Integer> ids);

}
