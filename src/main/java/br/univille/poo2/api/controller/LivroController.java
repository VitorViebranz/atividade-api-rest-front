package br.univille.poo2.api.controller;

import br.univille.poo2.api.entity.Livro;
import br.univille.poo2.api.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping("/livros")
    public ModelAndView index() {
        var mv = new ModelAndView("livros/index");
        mv.addObject("lista", service.findAll());
        return mv;
    }

    @GetMapping("/livros/novo")
    public ModelAndView novo() {
        var mv = new ModelAndView("livros/novo");
        mv.addObject("objeto", new Livro());
        return mv;
    }

    @PostMapping("/livros/salvar")
    public ModelAndView salvar(@ModelAttribute Livro livro) {
        service.save(livro);
        return new ModelAndView("redirect:/livros");
    }

    @GetMapping("/livros/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        var mv = new ModelAndView("livros/editar");
        mv.addObject("objeto", service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.")));
        return mv;
    }

    @GetMapping("/livros/excluir/{id}")
    public ModelAndView excluir(@PathVariable Long id) {
        service.deleteById(id);
        return new ModelAndView("redirect:/livros");
    }
}
