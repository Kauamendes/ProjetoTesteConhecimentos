package com.demo.Service;

import java.util.List;
import java.util.Optional;

import com.demo.Entity.EnderecoEntity;

public interface EnderecoService {

	EnderecoEntity salvarEndereco(EnderecoEntity endereco);
	
	Optional<EnderecoEntity> buscarEnderecoPorId(Long id);
	
	List<EnderecoEntity> listarEnderecos();
	
	void deletarEndereco(Long id);
	
	EnderecoEntity editarEndereco(EnderecoEntity endereco);
	
	List<EnderecoEntity> listarEnderecosPessoa(Long id);
	
}
