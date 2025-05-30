package com.edutech.msgestionusuarios.service;

import java.util.List;

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

    public void deleteById(int idpermiso) {
        permisoRepository.deleteById(idpermiso);
    }

    public Permiso findById(int idpermiso) {
        return permisoRepository.findById(idpermiso);
    }

    public boolean update(int idpermiso, Permiso permiso) {

        Permiso perm = permisoRepository.findById(idpermiso);
        if (perm == null) {
            return false;
        }
        perm.setId(idpermiso);
        perm.setNombrePermiso(permiso.getNombrePermiso());
        perm.setDescripcion(permiso.getDescripcion());
        permisoRepository.save(perm);
        return true;
    }

}
