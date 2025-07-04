package com.edutech.msgestionusuarios.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Usuario> findById(Integer id) {
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
        Usuario userid = usuarioRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("No se encontro el id " + id));
        userid.activarCuenta();
        usuarioRepository.save(userid);
    }

    @Transactional
    public void desactivarCuenta(Integer id) {
        Usuario userId = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el id" + id));
        userId.desactivarCuenta();
        usuarioRepository.save(userId);
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

    public boolean udpdateUsuario(int id, Usuario usuario) {

        Optional<Usuario> user = usuarioRepository.findById(id);
        user.get().setId(id);
        user.get().setNameUsuario(usuario.getNameUsuario());
        user.get().setPassUsuario(usuario.getPassUsuario());
        user.get().setNombre(usuario.getNombre());
        user.get().setApellido(usuario.getApellido());
        user.get().setEmail(usuario.getEmail());
        user.get().setEstado(usuario.isEstado());
        user.get().setRoles(usuario.getRoles());
        usuarioRepository.save(user.get());
        return true;
    }
}
