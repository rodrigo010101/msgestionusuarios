package com.edutech.msgestionusuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import com.edutech.msgestionusuarios.controller.RolControllerV2;
import com.edutech.msgestionusuarios.model.Usuario;

@Component
public class UsuarioModelAssemblers implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    @Override
    public @NonNull EntityModel<Usuario> toModel(@NonNull Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(RolControllerV2.class).getRolId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(RolControllerV2.class).getAllRol()).withRel("usuario"));
    }

}
