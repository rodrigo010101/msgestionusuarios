package com.edutech.msgestionusuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.msgestionusuarios.model.Permiso;
import com.edutech.msgestionusuarios.service.PermisoService;

@RestController
@RequestMapping("/api/v1/permiso")

public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @PostMapping
    public ResponseEntity<Permiso> crearPermiso(@RequestBody Permiso permiso) {
        try {
            Permiso newPermiso = permisoService.save(permiso);

            return new ResponseEntity<>(newPermiso, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idpermiso}")
    public ResponseEntity<Permiso> idExisting(@PathVariable Integer idpermiso) {
        try {
            // obj
            Permiso idExisting = permisoService.findById(idpermiso);
            if (idExisting != null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Permiso>> listExisting() {
        try {
            // obj listexisting
            List<Permiso> listexisting = permisoService.findAll();
            if (listexisting.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(listexisting, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idpermiso}")
    public ResponseEntity<Void> delExisting(@PathVariable Integer idpermiso) {
        try {
            // obj delExisting
            Permiso delExisting = permisoService.findById(idpermiso);
            if (delExisting != null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                permisoService.deleteById(idpermiso);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idpermiso}")
    public ResponseEntity<Permiso> updatePermiso(@RequestBody Permiso permiso, @PathVariable Integer idpermiso) {
        try {
            boolean peractualizado = permisoService.update(idpermiso, permiso);
            if (!peractualizado) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Permiso actualizado = permisoService.findById(idpermiso);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
