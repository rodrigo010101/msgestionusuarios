package com.edutech.msgestionusuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.msgestionusuarios.assemblers.RolModelAssemblers;
import com.edutech.msgestionusuarios.model.Rol;
import com.edutech.msgestionusuarios.service.RolService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Entity Rol
@RestController
@RequestMapping("/api/v2/rol")
public class RolControllerV2 {

    @Autowired
    private RolService rolService;

    @Autowired
    private RolModelAssemblers assemblers;

    // get obtener todos los roles
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Rol>> getAllRol() {
        List<EntityModel<Rol>> rol = rolService.findAll().stream().map(assemblers::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(rol,
                linkTo(methodOn(RolControllerV2.class).getAllRol()).withSelfRel());
    }

    // obtenre rol por id
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Rol> getRolId(@PathVariable int id) {
        Optional<Rol> rol = rolService.findById(id);
        return assemblers.toModel(rol.get());
    }

    // post crear
    @PostMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Rol>> crearRol(@RequestBody Rol rol) {
        Rol nuevoRol = rolService.save(rol);
        return ResponseEntity
                .created(linkTo(methodOn(RolControllerV2.class).getRolId(rol.getId())).toUri())
                .body(assemblers.toModel(nuevoRol));
    }

    // por actualizar
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Rol>> actualizarRol(@PathVariable int id, @RequestBody Rol rol) {
        rol.setId(id);
        Rol updRol = rolService.save(rol);
        return ResponseEntity.ok(assemblers.toModel(updRol));
    }

}