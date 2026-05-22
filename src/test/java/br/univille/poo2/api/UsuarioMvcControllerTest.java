package br.univille.poo2.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import br.univille.poo2.api.controller.UsuarioController;
import br.univille.poo2.api.entity.Usuario;
import br.univille.poo2.api.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioMvcControllerTest {

    @Test
    void deveAbrirPaginaInicial() {
        UsuarioService service = Mockito.mock(UsuarioService.class);
        Mockito.when(service.findAll()).thenReturn(List.of());
        UsuarioController controller = new UsuarioController(service);

        ModelAndView mv = controller.index();

        assertEquals("index", mv.getViewName());
        assertNotNull(mv.getModel().get("lista"));
    }

    @Test
    void deveAbrirPaginaDeEdicao() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        UsuarioService service = Mockito.mock(UsuarioService.class);
        Mockito.when(service.findById(1L)).thenReturn(Optional.of(usuario));
        UsuarioController controller = new UsuarioController(service);

        ModelAndView mv = controller.editar(1L);

        assertEquals("editar", mv.getViewName());
        assertEquals(usuario, mv.getModel().get("objeto"));
    }
}
