package com.demo.Service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.Entity.EnderecoEntity;
import com.demo.Entity.PessoaEntity;
import com.demo.Exceptions.RegraNegocioException;
import com.demo.Repository.PessoaRepository;
import com.demo.Service.PessoaService;



@Service
public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository repository;
	
	public PessoaServiceImpl(PessoaRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public PessoaEntity salvarPessoa(PessoaEntity pessoa) {
		validarPessoa(pessoa);
		return repository.save(pessoa);
	}

	@Override
	@Transactional
	public PessoaEntity editarPessoa(PessoaEntity pessoa) {
		Objects.requireNonNull(pessoa.getId());
		validarPessoa(pessoa);
		return repository.save(pessoa);
	}

	@Override
	@Transactional
	public void deletarPessoa(PessoaEntity pessoa) {
		Objects.requireNonNull(pessoa.getId());
		repository.delete(pessoa);
	}

	@Override
	public Optional<PessoaEntity> buscarPessoa(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<PessoaEntity> listarPessoas() {
		return repository.findAll();
	}
	
	public void validarPessoa(PessoaEntity p) {
		
		if(p.getNome() == null || p.getNome().equals("")) throw new RegraNegocioException("O nome da pessoa não pode ser vazio");
		
		if(p.getDataNascimento() == null || p.getDataNascimento().equals("")) throw new RegraNegocioException("A data de nascimento da pessoa não pode ser vazia");	
		
	}

}
