package com.edutech.msgestionusuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.edutech.msgestionusuarios.controller.RolControllerV2;
import com.edutech.msgestionusuarios.model.Rol;

import jakarta.annotation.Nonnull;

@Component
public class RolModelAssemblers implements RepresentationModelAssembler<Rol, EntityModel<Rol>> {
    @Override
    public @Nonnull EntityModel<Rol> toModel(@Nonnull Rol rol) {
        return EntityModel.of(
                rol,
                linkTo(methodOn(RolControllerV2.class).getRolId(rol.getId())).withSelfRel(),
                linkTo(methodOn(RolControllerV2.class).getAllRol()).withRel("rol"));
    }
}
