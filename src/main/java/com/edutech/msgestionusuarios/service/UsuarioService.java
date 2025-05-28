package com.edutech.msgestionusuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.msgestionusuarios.model.Usuario;
import com.edutech.msgestionusuarios.repository.RolRepository;
import com.edutech.msgestionusuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(int id) {
        return usuarioRepository.findById(id);
    }

    public boolean existByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    public boolean existByApellido(String apellido) {
        return usuarioRepository.findByApellido(apellido);
    }

    @Transactional
    public void activarCuenta(Integer id) {
        var userid = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con " + id));
        userid.activarCuenta();
    }

    @Transactional
    public void desactivarCuenta(Integer id) {
        var userId = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el id" + id));
        userId.desactivarCuenta();
    }

    public boolean estadoActivo(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")).isEstado();
    }

    public void asignarRol(Integer usuarioId, List<Integer> id) {
        var userId = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var roles = rolRepository.findAllById(id);

        userId.getRoles().clear();
        userId.getRoles().addAll(roles);
        usuarioRepository.save(userId);
    }
}
