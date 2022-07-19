package com.demo.Service;

import java.util.List;
import java.util.Optional;

import com.demo.Entity.EnderecoEntity;
import com.demo.Entity.PessoaEntity;


public interface PessoaService {
	
	PessoaEntity salvarPessoa(PessoaEntity pessoa);
	
	PessoaEntity editarPessoa(PessoaEntity pessoa);
	
	void deletarPessoa(PessoaEntity pessoa);
	
	Optional<PessoaEntity> buscarPessoa(Long id);
	
	List<PessoaEntity> listarPessoas();
	
}
