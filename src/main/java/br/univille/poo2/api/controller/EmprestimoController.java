package br.univille.poo2.api.controller;

import br.univille.poo2.api.service.EmprestimoService;
import br.univille.poo2.api.service.LivroService;
import br.univille.poo2.api.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmprestimoController {

    private final EmprestimoService service;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public EmprestimoController(EmprestimoService service, UsuarioService usuarioService, LivroService livroService) {
        this.service = service;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    @GetMapping("/emprestimos")
    public ModelAndView index() {
        var mv = new ModelAndView("emprestimos/index");
        mv.addObject("lista", service.findAll());
        mv.addObject("usuarios", usuarioService.findAll());
        mv.addObject("livros", livroService.findAll());
        mv.addObject("objeto", new EmprestimoForm());
        return mv;
    }

    @PostMapping("/emprestimos/salvar")
    public ModelAndView salvar(@ModelAttribute EmprestimoForm objeto) {
        service.realizarEmprestimo(objeto.getUsuarioId(), objeto.getLivroId());
        return new ModelAndView("redirect:/emprestimos");
    }

    @GetMapping("/emprestimos/devolver/{id}")
    public ModelAndView devolver(@PathVariable Long id) {
        service.devolver(id);
        return new ModelAndView("redirect:/emprestimos");
    }

    public static class EmprestimoForm {
        private Long usuarioId;
        private Long livroId;

        public Long getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Long usuarioId) {
            this.usuarioId = usuarioId;
        }

        public Long getLivroId() {
            return livroId;
        }

        public void setLivroId(Long livroId) {
            this.livroId = livroId;
        }
    }
}
