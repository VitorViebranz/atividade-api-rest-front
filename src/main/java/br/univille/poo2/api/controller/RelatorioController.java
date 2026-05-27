package br.univille.poo2.api.controller;

import br.univille.poo2.api.service.EmprestimoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RelatorioController {

    private final EmprestimoService emprestimoService;

    public RelatorioController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping("/relatorios")
    public ModelAndView index() {
        var mv = new ModelAndView("relatorios/index");
        long emprestimosAbertos = emprestimoService.countAbertos();
        long emprestimosDevolvidos = emprestimoService.countDevolvidos();
        mv.addObject("emprestimosAbertos", emprestimosAbertos);
        mv.addObject("emprestimosDevolvidos", emprestimosDevolvidos);
        mv.addObject("emprestimosTotal", emprestimosAbertos + emprestimosDevolvidos);
        return mv;
    }
}
