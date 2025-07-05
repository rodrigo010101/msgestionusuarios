package com.edutech.msgestionusuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
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
import com.edutech.msgestionusuarios.model.Rol.TipoRol;
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
                new Rol(1, TipoRol.ADMIN, "Rol con todos los permisos", new ArrayList<>(), true, new ArrayList<>()));

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
        Rol nuevoRol = new Rol(1, TipoRol.MODERADOR, "Rol, solo con permiso de READ", new ArrayList<>(), true,
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

    @Test
    void actualizarRolyRetornarRolActualizado() {
        Rol rolExistente = new Rol(1, TipoRol.MODERADOR, "Rol, solo con permiso de READ", new ArrayList<>(), true,
                new ArrayList<>());
        when(rolRepository.save(any(Rol.class))).thenReturn(rolExistente);
        when(rolRepository.findById(1)).thenReturn(Optional.of(rolExistente));

        Rol rolActualizado = new Rol(1, TipoRol.INVITADO, "Rol, solo con permiso de Read", new ArrayList<>(), true,
                new ArrayList<>());

        boolean rolAct = rolService.updateRol(1, rolActualizado);
        verify(rolRepository).findById(1);

        assertTrue(rolAct);
        assertEquals(rolExistente, rolActualizado);
        assertEquals(rolExistente.getId(), rolActualizado.getId());

        verify(rolRepository).save(argumentCaptor.capture());

        Rol resultado = argumentCaptor.getValue();
        assertEquals(1, resultado.getId());
        assertEquals(resultado.getId(), rolActualizado.getId());

    }

    @Test
    void desactivarRolCuentaYdebeRetornaFalse() {
        Rol rol = new Rol();
        rol.setId(1);
        rol.desactivarRol(1);

        when(rolRepository.save(any(Rol.class))).thenReturn(rol);
        when(rolRepository.findById(1)).thenReturn(Optional.of(rol));

        rolService.desactivarCuenta(1);
        assertEquals(1, rol.getId());
        assertFalse(rol.isEstadoRol());
        verify(rolRepository).findById(1);
        verify(rolRepository).save(argThat(r -> !r.isEstadoRol()));
    }

    @Test
    void activarRolCuentaYdebeRetornarTrue() {
        Rol rolActivo = new Rol();
        rolActivo.setId(1);
        rolActivo.activarRol(0);

        when(rolRepository.findById(1)).thenReturn(Optional.of(rolActivo));
        when(rolRepository.save(any(Rol.class))).thenReturn(rolActivo);

        rolService.activarCuentaRol(1);
        assertTrue(true);
        assertEquals(1, rolActivo.getId());
        assertTrue(rolActivo.isEstadoRol());
        verify(rolRepository).findById(1);
    }

}
