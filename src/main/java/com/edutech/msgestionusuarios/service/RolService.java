package com.edutech.msgestionusuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.msgestionusuarios.model.Rol;
import com.edutech.msgestionusuarios.repository.RolRepository;

@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol findById(int idrol) {
        return rolRepository.findById(idrol);
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public void deleteById(Integer idrol) {
        rolRepository.deleteById(idrol);
    }

    public boolean existByNombre(String nombreRol) {
        return rolRepository.findByNombre(nombreRol);
    }

}
