package com.edutech.msgestionusuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.edutech.msgestionusuarios.model.Permiso;
import com.edutech.msgestionusuarios.model.Permiso.TipoAcceso;
import com.edutech.msgestionusuarios.model.Permiso.TipoPermiso;
import com.edutech.msgestionusuarios.repository.PermisoRepository;
import com.edutech.msgestionusuarios.service.PermisoService;

@ExtendWith(MockitoExtension.class)
public class PermisoServiceTest {
    @Captor
    private ArgumentCaptor<Integer> idCaptor;
    @Captor
    private ArgumentCaptor<Permiso> captorPermiso;
    @Mock
    private PermisoRepository permisoRepository;

    @InjectMocks
    private PermisoService permisoService;

    @Test
    void crearPermisoYdebeRetornarLaCreacion() {

        Permiso permisoNuevo = new Permiso(1, TipoPermiso.CREATE, TipoAcceso.ADMINISTRACION, true);

        when(permisoRepository.save(any(Permiso.class))).thenReturn(permisoNuevo);

        Permiso guardado = permisoService.save(permisoNuevo);

        assertEquals(1, guardado.getId());
        assertEquals(permisoNuevo, guardado);
        assertEquals(permisoNuevo.getNombrePermiso(), guardado.getNombrePermiso());
    }

    @Test
    void guardarPermisoYdebeRetornarElguardado() {
        Permiso guardado = new Permiso();
        guardado.setId(1);

        when(permisoRepository.save(any(Permiso.class))).thenReturn(guardado);

        Permiso resultado = permisoService.save(guardado);
        assertEquals(1, resultado.getId());
        assertEquals(guardado, resultado);

        verify(permisoRepository).save(resultado);
        verify(permisoRepository).save(captorPermiso.capture());
    }

    @Test
    void buscarPermisoPorIdYdebeRetornarElobjeto() {
        Permiso permisoId = new Permiso();
        permisoId.setId(1);
        // mockear el permiso repo y usar el metodo findById
        when(permisoRepository.findById(anyInt())).thenReturn(Optional.of(permisoId));

        Optional<Permiso> resultado = permisoService.findById(1);
        assertEquals(1, resultado.get().getId());

        verify(permisoRepository).findById(idCaptor.capture());

        assertEquals(1, idCaptor.getValue());

    }

    @Test
    void eliminarPermisoYnoRetornaNada() {
        doNothing().when(permisoRepository).deleteById(anyInt());

        permisoService.deleteById(1);

        verify(permisoRepository).deleteById(idCaptor.capture());
        assertEquals(1, idCaptor.getValue());
    }

    @Test
    void listarPermisoYdebeRetornarListaPermiso() {
        // objeto permiso
        List<Permiso> listPermiso = Arrays.asList(
                new Permiso(1, TipoPermiso.CREATE, TipoAcceso.ADMINISTRACION, true),
                new Permiso(2, TipoPermiso.DELETE, TipoAcceso.EJECUCION, true));

        // mockrepo permiso usando el metodo findAll
        when(permisoRepository.findAll()).thenReturn(listPermiso);

        List<Permiso> resultListPermiso = permisoService.findAll();
        assertFalse(resultListPermiso.isEmpty());
        assertEquals(listPermiso.size(), resultListPermiso.size());
        assertEquals(1, resultListPermiso.get(0).getId());

        verify(permisoRepository).findAll();

    }

    @Test
    void activarPermisoYdebeRetornarTrue() {
        Permiso permisoAct = new Permiso();
        permisoAct.setId(1);
        permisoAct.activarPermiso(1);

        when(permisoRepository.findById(1)).thenReturn(Optional.of(permisoAct));
        when(permisoRepository.save(any(Permiso.class))).thenReturn(permisoAct);

        permisoService.activarCuentaPermiso(1);
        assertEquals(1, permisoAct.getId());

        verify(permisoRepository).findById(1);
        verify(permisoRepository).save(argThat(p -> p.isEstadoPermiso()));

    }

    @Test
    void descativarPermisoYdebeRetornarFalse() {
        Permiso permisoDesc = new Permiso();
        permisoDesc.setId(1);
        permisoDesc.desactivarPermiso(1);

        when(permisoRepository.findById(1)).thenReturn(Optional.of(permisoDesc));
        when(permisoRepository.save(any(Permiso.class))).thenReturn(permisoDesc);

        permisoService.desactivarCuentaPermiso(1);
        assertEquals(1, permisoDesc.getId());

        verify(permisoRepository).findById(1);
        verify(permisoRepository).save(argThat(p -> !p.isEstadoPermiso()));

    }

    @Test
    void actualizarPermisoYdebeRetornarLaActualizacion() {

        // objeto actual
        Permiso permisoActual = new Permiso(1, TipoPermiso.UPDATE, TipoAcceso.ADMINISTRACION, true);

        // objeto actualizado
        Permiso permisoActualizado = new Permiso(1, TipoPermiso.MANAGE_USERS, TipoAcceso.EJECUCION, true);

        when(permisoRepository.findById(1)).thenReturn(Optional.of(permisoActual));
        when(permisoRepository.save(any(Permiso.class))).thenReturn(permisoActual);

        boolean permisoOk = permisoService.updatePermiso(1, permisoActualizado);
        verify(permisoRepository).findById(1);

        assertTrue(permisoOk);
        assertEquals(1, permisoActualizado.getId());
        assertEquals(permisoActual, permisoActualizado);

        verify(permisoRepository).save(captorPermiso.capture());

        // permiso capturado objeto
        Permiso permisoCapturado = captorPermiso.getValue();
        assertEquals(1, permisoCapturado.getId());
        assertEquals(permisoCapturado.getId(), permisoActualizado.getId());
    }

}
