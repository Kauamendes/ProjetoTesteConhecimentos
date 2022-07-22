package com.demo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.Entity.EnderecoEntity;
import com.demo.Entity.PessoaEntity;
import com.demo.Exceptions.RegraNegocioException;
import com.demo.Repository.PessoaRepository;
import com.demo.Service.impl.PessoaServiceImpl;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {

	@SpyBean
	PessoaServiceImpl service;
	
	@MockBean
	PessoaRepository repository;
	
	@Test
	public void deveValidarUsuarioComSucesso() {
		PessoaEntity p = PessoaEntity.builder().nome("teste").dataNascimento("05/01/2004").build();
		service.validarPessoa(p);
	}
	
	@Test
	public void deveRetornarRegraNegocioExceptionAoValidarPessoaComNomeVazioOuNulo() {
		PessoaEntity p = PessoaEntity.builder().nome("").dataNascimento("05/01/2004").build();
		
		Throwable exception = Assertions.catchThrowable(() -> service.validarPessoa(p));
		
		Assertions.assertThat(exception)
			.isInstanceOf(RegraNegocioException.class).hasMessage("O nome da pessoa não pode ser vazio");
	}
	
	@Test
	public void deveRetornarRegraNegocioExceptionAoValidarPessoaComDataDeNascimentoVaziaOuNula() {
		PessoaEntity p = PessoaEntity.builder().nome("teste").dataNascimento("").build();
		
		Throwable exception = Assertions.catchThrowable(() -> service.validarPessoa(p));
		
		Assertions.assertThat(exception)
			.isInstanceOf(RegraNegocioException.class).hasMessage("A data de nascimento da pessoa não pode ser vazia");
	}
	
	@Test
	public void deveSalvarUmaPessoa() {
		Mockito.doNothing().when(service).validarPessoa(Mockito.any());
		PessoaEntity p = PessoaEntity.builder().id(1l).nome("nome").dataNascimento("01/01/2022").build();
		Mockito.when(repository.save(Mockito.any(PessoaEntity.class))).thenReturn(p);
		
		PessoaEntity pessoaSalca = service.salvarPessoa(new PessoaEntity());
		
		Assertions.assertThat(pessoaSalca).isNotNull();
		Assertions.assertThat(pessoaSalca.getId()).isEqualTo(1l);
		Assertions.assertThat(pessoaSalca.getNome()).isEqualTo("nome");
		Assertions.assertThat(pessoaSalca.getDataNascimento()).isEqualTo("01/01/2022");
	}
	
}
