package com.demo.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.Entity.EnderecoEntity;
import com.demo.Entity.PessoaEntity;
import com.demo.Repository.PessoaRepository;
import com.demo.service.PessoaServiceTest;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
public class PessoaRepositoryTest {

	@MockBean
	private PessoaRepository repository;
	
	@Test
	public void devePersistirUmaPessoaNoBancoDeDados() {
		PessoaEntity p = PessoaEntity.builder().id(1l).nome("nome").dataNascimento("01/01/2001").build();
		
		Mockito.when(repository.save(Mockito.any(PessoaEntity.class))).thenReturn(p);
		
		PessoaEntity pessoaSalva = repository.save(new PessoaEntity());
		
		Assertions.assertThat(pessoaSalva).isNotNull();
		Assertions.assertThat(pessoaSalva.getId()).isEqualTo(1l);
		Assertions.assertThat(pessoaSalva.getNome()).isEqualTo("nome");
		Assertions.assertThat(pessoaSalva.getDataNascimento()).isEqualTo("01/01/2001");

	}
	
}
