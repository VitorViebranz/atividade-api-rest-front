package br.univille.poo2.api.service;

import br.univille.poo2.api.entity.Emprestimo;
import br.univille.poo2.api.entity.Livro;
import br.univille.poo2.api.entity.Usuario;
import br.univille.poo2.api.repository.EmprestimoRepository;
import br.univille.poo2.api.repository.LivroRepository;
import br.univille.poo2.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LivroRepository livroRepository;

    public List<Emprestimo> findAll() {
        return emprestimoRepository.findAll();
    }

    public long countAbertos() {
        return emprestimoRepository.countByDataDevolucaoIsNull();
    }

    public long countDevolvidos() {
        return emprestimoRepository.countByDataDevolucaoIsNotNull();
    }

    @Transactional
    public Emprestimo realizarEmprestimo(Long usuarioId, Long livroId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (livro.getDisponivel() == null || !livro.getDisponivel()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este livro já está emprestado no momento.");
        }

        livro.setDisponivel(false);
        livroRepository.save(livro);

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public Emprestimo devolver(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));

        if (emprestimo.getDataDevolucao() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este empréstimo já foi finalizado/devolvido anteriormente.");
        }

        emprestimo.setDataDevolucao(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }
}
