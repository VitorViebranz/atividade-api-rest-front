package br.univille.poo2.api.repository;

import br.univille.poo2.api.entity.Emprestimo;
import br.univille.poo2.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}