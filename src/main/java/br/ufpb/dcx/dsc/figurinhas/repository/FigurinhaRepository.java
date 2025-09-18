package br.ufpb.dcx.dsc.figurinhas.repository;

import br.ufpb.dcx.dsc.figurinhas.models.Figurinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FigurinhaRepository extends JpaRepository<Figurinha, Long> {
    List<Figurinha> findBySelecaoContainingIgnoreCase(String selecao);
}