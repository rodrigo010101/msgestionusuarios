package com.edutech.msgestionusuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.msgestionusuarios.model.Rol;
import com.edutech.msgestionusuarios.model.Rol.tipoRol;
import com.edutech.msgestionusuarios.repository.RolRepository;
import com.edutech.msgestionusuarios.service.RolService;

@ExtendWith(MockitoExtension.class)

public class RolServiceTest {
    @Captor
    private ArgumentCaptor<Rol> argumentCaptor;
    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    @Test
    void buscarRolPorIdYretornarElRol() {

        Rol rol = new Rol();
        rol.setId(1);
        // llamando al mock rolRepository y usando el metodo findById
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));
        // usando Optional para validar que exista un dato al menos no retorna null y
        // para acceder se debe realizar desde el get para
        // obtener el dato que tiene encapsulado Optional
        Optional<Rol> resultado = rolService.findById(1);
        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());

        verify(rolRepository).findById(1);

    }

    @Test
    void litarTodosLosRoles() {
        List<Rol> listRol = Arrays.asList(
                new Rol(1, tipoRol.ADMIN, "Rol con todos los permisos", new ArrayList<>(), new ArrayList<>()));

        when(rolRepository.findAll()).thenReturn(listRol);

        List<Rol> resultadoLista = rolService.findAll();
        assertEquals(listRol.get(0), resultadoLista.get(0));
        assertEquals(1, resultadoLista.get(0).getId());

        // verifica que llamo al mock de rolRepository con el metodo findAll
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    void crearRolYdebeRetornarElnuevoRol() {
        /*
         * segunda opcion es explicita
         * Rol nuevoRol = new Rol(1, tipoRol.MODERADOR, "Rol, solo con permiso de READ",
         * new ArrayList<Permiso>(),
         * new ArrayList<Usuario>());
         */
        // crear objeto
        Rol nuevoRol = new Rol(1, tipoRol.MODERADOR, "Rol, solo con permiso de READ", new ArrayList<>(),
                new ArrayList<>());

        when(rolRepository.save(any(Rol.class))).thenReturn(nuevoRol);

        Rol resultado = rolService.save(nuevoRol);
        assertEquals(1, resultado.getId());
        assertEquals(nuevoRol, resultado);

        verify(rolRepository).save(resultado);

    }

    @Test
    void eliminarRolYnoretorNada() {

        doNothing().when(rolRepository).deleteById(1);
        rolService.deleteById(1);
        verify(rolRepository).deleteById(1);
    }
}
