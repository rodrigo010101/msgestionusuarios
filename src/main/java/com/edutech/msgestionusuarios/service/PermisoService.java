package com.edutech.msgestionusuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.msgestionusuarios.model.Permiso;
import com.edutech.msgestionusuarios.repository.PermisoRepository;

@Service
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }

    public void deleteById(Integer idpermiso) {
        permisoRepository.deleteById(idpermiso);
    }

    public Optional<Permiso> findById(Integer idpermiso) {
        return permisoRepository.findById(idpermiso);
    }

}
