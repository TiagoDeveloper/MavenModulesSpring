package br.com.service.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.service.models.Pessoa;

@Repository
public interface PessoaService extends JpaRepository<Pessoa, Long>{

}
