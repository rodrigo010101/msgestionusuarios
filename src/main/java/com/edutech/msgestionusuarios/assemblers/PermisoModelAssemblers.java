package com.edutech.msgestionusuarios.assemblers;

import com.edutech.msgestionusuarios.controller.PermisoControllerV2;
import com.edutech.msgestionusuarios.model.Permiso;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

@Component
public class PermisoModelAssemblers implements RepresentationModelAssembler<Permiso, EntityModel<Permiso>> {

    @Override
    public @Nonnull EntityModel<Permiso> toModel(@Nonnull Permiso permiso) {
        return EntityModel.of(
                permiso,
                linkTo(methodOn(PermisoControllerV2.class).getPermisoId(permiso.getId())).withSelfRel(),
                linkTo(methodOn(PermisoControllerV2.class).getAllUsuario()).withRel("permiso"));
    }
}
