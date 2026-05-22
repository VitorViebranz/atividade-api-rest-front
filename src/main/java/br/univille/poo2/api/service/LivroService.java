package br.univille.poo2.api.service;

import br.univille.poo2.api.entity.Livro;
import br.univille.poo2.api.repository.LivroRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    public List<Livro> findAll() {
        return repository.findAll();
    }

    public Livro insert(Livro livro) {
        if(Strings.isBlank(livro.getTitulo())){
            throw new RuntimeException("Título não informado.");
        }
        if(Strings.isBlank(livro.getAutor())){
            throw new RuntimeException("Autor não informado.");
        }
        if(Strings.isBlank(livro.getIsbn())){
            throw new RuntimeException("ISBN não informado.");
        }

        // Validação de regra de negócio (Equivalente ao E-mail)
        Optional<Livro> livroComEsseIsbn = repository.findByIsbn(livro.getIsbn());
        if (livroComEsseIsbn.isPresent()) {
            throw new RuntimeException("ISBN já está cadastrado.");
        }
        return repository.save(livro);
    }

    public Optional<Livro> findById(Long id) {
        Optional<Livro> livroOpt = repository.findById(id);
        if (livroOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado");
        }
        return livroOpt;
    }

    public Livro update(Livro livro) {
        if(Strings.isBlank(livro.getTitulo())){
            throw new RuntimeException("Título não informado.");
        }
        if(Strings.isBlank(livro.getAutor())){
            throw new RuntimeException("Autor não informado.");
        }
        if(Strings.isBlank(livro.getIsbn())){
            throw new RuntimeException("ISBN não informado.");
        }

        Livro livroExistente = repository.findById(livro.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado."));

        Optional<Livro> livroComEsseIsbn = repository.findByIsbn(livro.getIsbn());
        if (livroComEsseIsbn.isPresent() && !livroComEsseIsbn.get().getId().equals(livro.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN já está cadastrado para outro livro.");
        }

        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAutor(livro.getAutor());
        livroExistente.setIsbn(livro.getIsbn());
        livroExistente.setDisponivel(livro.getDisponivel());

        return repository.save(livroExistente);
    }

    public Livro save(Livro livro) {
        if (livro.getId() == null) {
            return insert(livro);
        }
        return update(livro);
    }

    public void delete(Livro livro){
        repository.delete(livro);
    }

    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }
}
