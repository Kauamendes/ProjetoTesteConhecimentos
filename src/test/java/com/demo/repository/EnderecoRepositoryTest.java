package com.demo.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.Entity.EnderecoEntity;
import com.demo.Repository.EnderecoRepository;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
public class EnderecoRepositoryTest {

	@MockBean
	private EnderecoRepository repository;
	
	@Test
	public void devePersistirUmEnderecoNoBancoDeDados() {
		EnderecoEntity e = EnderecoEntity.builder().id(1l)
												   .logradouro("rua teste")
												   .cep("88704-200")
												   .cidade("cidade teste")
												   .numero("200")
												   .enderecoPrincipal(false).build();
		
		Mockito.when(repository.save(Mockito.any(EnderecoEntity.class))).thenReturn(e);
		
		EnderecoEntity enderecoSalvo = repository.save(new EnderecoEntity());
		
		Assertions.assertThat(enderecoSalvo).isNotNull();
		Assertions.assertThat(enderecoSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(enderecoSalvo.getLogradouro()).isEqualTo("rua teste");
		Assertions.assertThat(enderecoSalvo.getCep()).isEqualTo("88704-200");
		Assertions.assertThat(enderecoSalvo.getNumero()).isEqualTo("200");
		Assertions.assertThat(enderecoSalvo.getCidade()).isEqualTo("cidade teste");
		Assertions.assertThat(enderecoSalvo.isEnderecoPrincipal()).isFalse();

	}
	
	
}
