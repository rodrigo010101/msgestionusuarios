package com.edutech.msgestionusuarios.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.msgestionusuarios.model.Rol;
import com.edutech.msgestionusuarios.service.RolService;
import com.edutech.msgestionusuarios.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/rol")

public class RolController {

    @Autowired
    private RolService rolService;
    @Autowired
    private UsuarioService uService;

    // solicitud HTTP POST crear Rol
    @PostMapping
    public ResponseEntity<Rol> postCreatedRol(@RequestBody Rol rol) {
        // obj createRol
        try {
            if (rol.getId() != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (rol.getNombreRol() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            } else {
                // obj new
                Rol newRol = rolService.save(rol);
                return new ResponseEntity<>(newRol, HttpStatus.CREATED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // solicitud HTTP GET, listar Rol
    @GetMapping
    public ResponseEntity<List<Rol>> getReadRol() {
        // obj rolId
        try {
            List<Rol> rolAll = rolService.findAll();
            if (rolAll.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(rolAll, HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // solicitud HTTP GET , buscar por id
    @GetMapping("/{idrol}")
    public ResponseEntity<Rol> getRolId(@PathVariable Integer idrol) {

        try {
            // obj busRolId
            Optional<Rol> busRolId = rolService.findById(idrol);
            if (busRolId == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(busRolId.get(), HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // solicitud HTTP DElETE , eliminar por id

    @DeleteMapping("/{idrol}")
    public ResponseEntity<Rol> delRol(@PathVariable Integer idrol) {
        try {
            // obj delRolid
            Optional<Rol> idRolDel = rolService.findById(idrol);
            if (idRolDel.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                rolService.deleteById(idrol);
                return new ResponseEntity<>(idRolDel.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // solicitud HTTP PUT , actualizar por id y validar si existe el obj

    @PutMapping("/{idrol}")
    public ResponseEntity<Rol> putRol(@RequestBody Rol rol, @PathVariable Integer idrol) {
        try {
            // obj idExisting
            Optional<Rol> idExisting = rolService.findById(idrol);
            if (idExisting == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (rol.getNombreRol() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            if (rol.getDescripcion() == null || rol.getDescripcion().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
                // objUpdate
                Optional<Rol> objUpdate = rolService.findById(idrol);
                objUpdate.get().setNombreRol(rol.getNombreRol());
                objUpdate.get().setDescripcion(rol.getDescripcion());
                rolService.save(objUpdate.get());
                return new ResponseEntity<>(objUpdate.get(), HttpStatus.OK);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{idrol}/roles")
    public ResponseEntity<String> asignarRoles(@PathVariable Integer idUsuario, @RequestBody List<Integer> idRol) {
        uService.asignarRol(idUsuario, idRol);
        return ResponseEntity.ok("Roles asignado correctamente");
    }
}
