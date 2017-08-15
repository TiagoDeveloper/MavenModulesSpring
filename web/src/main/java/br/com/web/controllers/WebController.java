package br.com.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.service.models.Pessoa;
import br.com.service.services.PessoaService;

@RestController
public class WebController {
	@Autowired
	private PessoaService pessoaService;
	
	
	@RequestMapping(value="/pessoas", method=RequestMethod.GET, produces="application/json")
	public List<Pessoa> getPessoa() {
		return this.pessoaService.findAll();
	}
}
