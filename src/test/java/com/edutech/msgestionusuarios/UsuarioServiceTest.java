package com.edutech.msgestionusuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.edutech.msgestionusuarios.model.Usuario;
import com.edutech.msgestionusuarios.repository.UsuarioRepository;
import com.edutech.msgestionusuarios.service.UsuarioService;

@ExtendWith(MockitoExtension.class)

public class UsuarioServiceTest {
    @Captor
    private ArgumentCaptor<Usuario> userCaptor;

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService usuarioService;

    @Test
    void buscarUsuarioPorIdYdebeRetornarElusuario() {

        Usuario user = new Usuario();
        user.setId(1);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<Usuario> resultado = usuarioService.findById(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.get().getId());
        // assertEquals("No Encontrado", ex.getMessage());

        verify(usuarioRepository).findById(1);

    }

    @Test
    void guardarUsuario() {
        Usuario user = new Usuario();

        user.setNombre("Amalia");

        when(usuarioRepository.save(user)).thenReturn(user);
        Usuario resultado = usuarioService.save(user);

        assertNotNull(resultado);
        assertEquals("Amalia", resultado.getNombre());
        verify(usuarioRepository).save(user);
        verify(usuarioRepository).save(userCaptor.capture());
    }

    @Test
    void listarTodosLosUsuariosYRetornarUnaLista() {

        List<Usuario> listUsuario = Arrays.asList(
                new Usuario(1, "Amalia", "12", "amaliaSofia@gmail.com", "sofia", "amalia", true, new ArrayList<>()),
                new Usuario(2, "sofia ", "90", "amaliaSofia90@gmail.com", "amalia", "sofia", false, new ArrayList<>()));

        when(usuarioRepository.findAll()).thenReturn(listUsuario);
        assertEquals(1, listUsuario.get(0).getId());
        assertEquals(2, listUsuario.get(1).getId());
        assertEquals(2, listUsuario.size());

        List<Usuario> resultado = usuarioService.findAll();
        assertEquals(listUsuario.size(), resultado.size());
        assertEquals(listUsuario.get(0).getId(), resultado.get(0).getId());

        verify(usuarioRepository).findAll();

    }

    @Test
    void actualizarUsuario() {

        Usuario linkId = new Usuario();
        linkId.setId(1);

        Usuario usuarioExistente = new Usuario(1, "Amalia", "12", "amaliaSofia@gmail.com", "sofia", "amalia", true,
                new ArrayList<>());

        Usuario usuarioActualizado = new Usuario(1, "Sofia", "2025", "amaliaSofia@gmail.com", "Amalia", "sofia", true,
                new ArrayList<>());

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        // boolean metodo actualizar para usuario se debe crear
        boolean resultado = usuarioService.udpdateUsuario(1, usuarioActualizado);

        verify(usuarioRepository).findById(1);
        assertTrue(resultado);
        verify(usuarioRepository).save(userCaptor.capture());

        Usuario guardado = userCaptor.getValue();
        assertEquals(guardado.getId(), usuarioActualizado.getId());

    }

    @Test
    void eliminarUsuarioNoRetornaNada() {
        doNothing().when(usuarioRepository).deleteById(1);
        usuarioService.deleteById(1);
        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void activarUsuarioCuenta() {
        Usuario linkId = new Usuario();
        linkId.setId(1);
        linkId.activarCuenta();

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(linkId));

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(linkId);

        usuarioService.activarCuenta(1);
        verify(usuarioRepository).findById(1);
        verify(usuarioRepository).save(argThat(u -> u.isEstado()));
        assertTrue(linkId.isEstado());
    }

    @Test
    void desactivarCuenta() {
        Usuario linkId = new Usuario();
        linkId.setId(1);
        linkId.desactivarCuenta();

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(linkId));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(linkId);

        usuarioService.desactivarCuenta(1);

        verify(usuarioRepository).findById(1);
        verify(usuarioRepository).save(argThat(u -> !u.isEstado()));
        assertFalse(linkId.isEstado());
    }
}
