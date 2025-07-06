package com.edutech.msgestionusuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.msgestionusuarios.model.Permiso;
import com.edutech.msgestionusuarios.repository.PermisoRepository;

import jakarta.transaction.Transactional;

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

    public boolean updatePermiso(int idpermiso, Permiso permiso) {

        Optional<Permiso> perm = permisoRepository.findById(idpermiso);
        if (perm == null) {
            return false;
        }
        perm.get().setId(idpermiso);
        perm.get().setNombrePermiso(permiso.getNombrePermiso());
        perm.get().setTipoAcceso(permiso.getTipoAcceso());
        permisoRepository.save(perm.get());
        return true;
    }

    @Transactional
    public void activarCuentaPermiso(int id) {
        Permiso activo = permisoRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Id " + id + " no encontrado."));
        activo.activarPermiso(id);
        permisoRepository.save(activo);

    }

    public void desactivarCuentaPermiso(int id) {
        Permiso desactivar = permisoRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Id " + id + " no encontrado."));
        desactivar.desactivarPermiso(id);
        permisoRepository.save(desactivar);
    }

}
