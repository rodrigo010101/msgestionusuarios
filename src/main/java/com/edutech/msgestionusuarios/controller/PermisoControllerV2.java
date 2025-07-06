package com.edutech.msgestionusuarios.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.edutech.msgestionusuarios.assemblers.PermisoModelAssemblers;
import com.edutech.msgestionusuarios.model.Permiso;
import com.edutech.msgestionusuarios.service.PermisoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

// Entity = Permiso

@RestController
@RequestMapping("/api/v2/permiso")
public class PermisoControllerV2 {
    @Autowired
    private PermisoService permisoService;

    @Autowired
    private PermisoModelAssemblers assemblers;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Permiso>> getAllUsuario() {
        List<EntityModel<Permiso>> permiso = permisoService.findAll().stream().map(assemblers::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(permiso,
                linkTo(methodOn(PermisoControllerV2.class).getAllUsuario()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Permiso> getPermisoId(@PathVariable int id) {
        Optional<Permiso> permiso = permisoService.findById(id);
        return assemblers.toModel(permiso.get());
    }

    // crear
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Permiso>> crearPermiso(@RequestBody Permiso permiso) {
        Permiso nuevopermiso = permisoService.save(permiso);
        return ResponseEntity.created(linkTo(methodOn(PermisoControllerV2.class).getPermisoId(permiso.getId())).toUri())
                .body(assemblers.toModel(nuevopermiso));
    }

    // actualizar
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Permiso>> actualizarPermiso(@PathVariable int id, @RequestBody Permiso permiso) {
        permiso.setId(id);
        Permiso updPermiso = permisoService.save(permiso);
        return ResponseEntity.ok(assemblers.toModel(updPermiso));
    }
}
