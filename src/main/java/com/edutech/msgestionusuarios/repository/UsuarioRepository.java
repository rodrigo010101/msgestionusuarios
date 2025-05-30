package com.edutech.msgestionusuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edutech.msgestionusuarios.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findById(int id);

    boolean findByNombre(String nombre);

    boolean findByApellido(String apellido);

    // List<Usuario> findAllById(Iterable<Integer> ids);

    // List<Usuario> findAll(Usuario usuario);

}
