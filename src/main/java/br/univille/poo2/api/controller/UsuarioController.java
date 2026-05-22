package br.univille.poo2.api.controller;

import br.univille.poo2.api.entity.Usuario;
import br.univille.poo2.api.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService pessoaService) {
        this.usuarioService = pessoaService;
    }

    @GetMapping({"/", ""})
    public ModelAndView index() {
        var mv = new ModelAndView("index");
        mv.addObject("lista", usuarioService.findAll());
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        var mv = new ModelAndView("novo");
        mv.addObject("objeto", new Usuario());
        return mv;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute Usuario usuario) {
        usuarioService.save(usuario);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        var mv = new ModelAndView("editar");
        mv.addObject("objeto", usuarioService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")));
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return new ModelAndView("redirect:/");
    }
}
