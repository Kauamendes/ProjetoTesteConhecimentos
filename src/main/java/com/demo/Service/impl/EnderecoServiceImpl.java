package com.demo.Service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.Entity.EnderecoEntity;
import com.demo.Entity.PessoaEntity;
import com.demo.Repository.EnderecoRepository;
import com.demo.Service.EnderecoService;

@Service
public class EnderecoServiceImpl implements EnderecoService {

	private EnderecoRepository repository;
	
	public EnderecoServiceImpl(EnderecoRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public EnderecoEntity salvarEndereco(EnderecoEntity endereco) {
		return repository.save(endereco);
	}

	@Override
	@Transactional
	public Optional<EnderecoEntity> buscarEnderecoPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public List<EnderecoEntity> listarEnderecos() {
		return repository.findAll();
	}

	@Override
	@Transactional
	public void deletarEndereco(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	@Transactional
	public EnderecoEntity editarEndereco(EnderecoEntity endereco) {
		return repository.save(endereco);
	}

	@Override
	public List<EnderecoEntity> listarEnderecosPessoa(Long id) {
		EnderecoEntity e = new EnderecoEntity().builder().pessoa(new PessoaEntity().builder().id(id).build()).build();                                                 
		Example<EnderecoEntity> example = Example.of(e);
		return repository.findAll(example);
	}

}
