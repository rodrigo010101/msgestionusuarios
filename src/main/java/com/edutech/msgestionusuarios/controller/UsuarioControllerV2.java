package com.edutech.msgestionusuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;

import com.edutech.msgestionusuarios.assemblers.UsuarioModelAssemblers;
import com.edutech.msgestionusuarios.model.Usuario;
import com.edutech.msgestionusuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/usuario")
public class UsuarioControllerV2 {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssemblers assemblers;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> getAllUsuario() {
        List<EntityModel<Usuario>> usuario = usuarioService.findAll().stream()
                .map(assemblers::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuario()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Usuario> getUsuarioId(@PathVariable int id) {
        Optional<Usuario> usuarioId = usuarioService.findById(id);
        return assemblers.toModel(usuarioId.get());
    }

    // crear
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioId(nuevoUsuario.getId())).toUri())
                .body(assemblers.toModel(nuevoUsuario));
    }

    // post
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        Usuario udpUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(assemblers.toModel(udpUsuario));
    }
}
