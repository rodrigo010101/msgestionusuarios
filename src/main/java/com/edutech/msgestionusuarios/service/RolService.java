package com.edutech.msgestionusuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.msgestionusuarios.model.Rol;
import com.edutech.msgestionusuarios.repository.RolRepository;

import jakarta.transaction.Transactional;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Optional<Rol> findById(int idrol) {
        return rolRepository.findById(idrol);
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public void deleteById(Integer idrol) {
        rolRepository.deleteById(idrol);
    }

    public boolean existByNombre(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol);
    }

    public boolean updateRol(int id, Rol rol) {

        Optional<Rol> nuevoRol = rolRepository.findById(id);
        nuevoRol.get().setId(id);
        nuevoRol.get().setNombreRol(rol.getNombreRol());
        nuevoRol.get().setDescripcion(rol.getDescripcion());
        nuevoRol.get().setPermiso(rol.getPermiso());
        nuevoRol.get().setUsuarios(rol.getUsuarios());
        rolRepository.save(nuevoRol.get());
        return true;
    }

    @Transactional
    public void activarCuentaRol(int id) {
        Rol rolActivo = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol con " + id + " no encontrado"));
        rolActivo.activarRol(id);
        rolRepository.save(rolActivo);
    }

    @Transactional
    public void desactivarCuenta(int id) {
        Rol rolDescativar = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol con id " + id + " no encontrado"));
        rolDescativar.desactivarRol(id);
        rolRepository.save(rolDescativar);
    }

}
