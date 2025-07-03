package com.edutech.msgestionusuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        // int idNoExiste = 99;

        when(usuarioRepository.findById(1)).thenReturn(user);
        // when(usuarioRepository.findById(idNoExiste)).thenReturn(null);

        // RuntimeException ex = assertThrows(RuntimeException.class, () -> {
        // usuarioService.findById(idNoExiste);
        // });

        Usuario resultado = usuarioService.findById(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
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
}
