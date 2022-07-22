package com.demo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.Entity.EnderecoEntity;
import com.demo.Entity.PessoaEntity;
import com.demo.Repository.EnderecoRepository;
import com.demo.Service.EnderecoService;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EnderecoServiceTest {

	@SpyBean
	private EnderecoService service;
	
	@MockBean
	private EnderecoRepository repository;
	
	
	@Test
	public void deveSalvarUmEndereco() {
		EnderecoEntity e = EnderecoEntity.builder().id(1l)
				   									.logradouro("rua teste")
				   									.cep("88704-200")
				   									.cidade("cidade teste")
				   									.numero("200")
				   									.enderecoPrincipal(false).build();
		
		Mockito.when(repository.save(Mockito.any(EnderecoEntity.class))).thenReturn(e);
		
		EnderecoEntity enderecoSalvo = service.salvarEndereco(new EnderecoEntity());
		
		Assertions.assertThat(enderecoSalvo).isNotNull();
		Assertions.assertThat(enderecoSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(enderecoSalvo.getLogradouro()).isEqualTo("rua teste");
		Assertions.assertThat(enderecoSalvo.getCep()).isEqualTo("88704-200");
		Assertions.assertThat(enderecoSalvo.getCidade()).isEqualTo("cidade teste");
		Assertions.assertThat(enderecoSalvo.getNumero()).isEqualTo("200");
		Assertions.assertThat(enderecoSalvo.isEnderecoPrincipal()).isFalse();
	}
	
	
}
