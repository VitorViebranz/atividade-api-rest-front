package br.univille.poo2.api.service;

import br.univille.poo2.api.entity.Usuario;
import br.univille.poo2.api.repository.UsuarioRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRep;

    public List<Usuario> findAll() {
        return userRep.findAll();
    }

    public Usuario insert(Usuario usuario) {
        if(Strings.isBlank(usuario.getNome())){
            throw new RuntimeException("Nome não informado.");
        }
        if(Strings.isBlank(usuario.getEmail())){
            throw new RuntimeException("Email não informado.");
        }
        Optional<Usuario> usuarioComEsseEmail = userRep.findByEmailIgnoreCase(usuario.getEmail());
        if (usuarioComEsseEmail.isPresent()) {
            throw new RuntimeException("Email já está cadastrado.");
        }
        return userRep.save(usuario);
    }

    public Optional<Usuario> findById(Long id) {
        Optional<Usuario> usuarioOpt = userRep.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return usuarioOpt;
    }

    public Usuario update(Usuario usuario) {
        if(Strings.isBlank(usuario.getNome())){
            throw new RuntimeException("Nome não informado.");
        }
        if(Strings.isBlank(usuario.getEmail())){
            throw new RuntimeException("Email não informado.");
        }

        Usuario usuarioExistente = userRep.findById(usuario.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        Optional<Usuario> usuarioComEsseEmail = userRep.findByEmailIgnoreCase(usuario.getEmail());

        if (usuarioComEsseEmail.isPresent() && !usuarioComEsseEmail.get().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está cadastrado por outro usuário.");
        }

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        return userRep.save(usuarioExistente);
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            return insert(usuario);
        }
        return update(usuario);
    }

    public void delete(Usuario usuario){
        userRep.delete(usuario);
    }

    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }
}
