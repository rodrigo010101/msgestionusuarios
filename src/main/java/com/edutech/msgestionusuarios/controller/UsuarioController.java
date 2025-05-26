package com.edutech.msgestionusuarios.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.edutech.msgestionusuarios.model.Usuario;
import com.edutech.msgestionusuarios.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            if (usuario.getId() != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (usuario.getNameUsuario() == null || usuario.getNameUsuario().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (usuarioService.existByNombre(usuario.getNombre())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Usuario newUsuario = usuarioService.save(usuario);
            return new ResponseEntity<>(newUsuario, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getId(@PathVariable Integer id) {
        try {
            // obj
            Optional<Usuario> userId = usuarioService.findById(id);
            if (!userId.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getList() {
        try {
            // obj
            List<Usuario> listUsuario = usuarioService.findAll();
            if (!listUsuario.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable Integer id) {
        try {
            // obj
            Optional<Usuario> delUsuario = usuarioService.findById(id);
            if (!delUsuario.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                usuarioService.deleteById(id);
                return new ResponseEntity<>(delUsuario.get(), HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> putUsuario(@RequestBody Usuario usuario, @PathVariable Integer id) {
        try {
            // obj
            Optional<Usuario> userIdExisting = usuarioService.findById(id);
            if (!userIdExisting.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (usuarioService.existByNombre(usuario.getNombre())
                    || usuarioService.existByApellido(usuario.getApellido())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (usuario.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                // obj
                Usuario newUsuario = userIdExisting.get();
                newUsuario.setNombre(usuario.getNombre());
                newUsuario.setApellido(usuario.getApellido());
                usuarioService.save(newUsuario);
                return new ResponseEntity<>(newUsuario, HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> putActivar(@PathVariable Integer id) {
        usuarioService.activarCuenta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> putDesactivar(@PathVariable Integer id) {
        usuarioService.desactivarCuenta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
