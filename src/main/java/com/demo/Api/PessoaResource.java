package com.demo.Api;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Entity.EnderecoEntity;
import com.demo.Entity.PessoaEntity;
import com.demo.Exceptions.RegraNegocioException;
import com.demo.Service.EnderecoService;
import com.demo.Service.PessoaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@RestController
@RequestMapping("/api/pessoas")
public class PessoaResource {

	private PessoaService service;
	
	
	private PessoaResource(PessoaService service, EnderecoService enderecoService) {
		this.service = service;
	}
	
	@PostMapping(produces = "application/json")
	@ApiOperation(value = "Salvar pessoa")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna a pessoa salva"),
			@ApiResponse(code = 400, message = "Retorna a mensagem de erro")
	})
	public ResponseEntity salvar(@RequestBody PessoaEntity pessoa) {
		PessoaEntity p = PessoaEntity.builder().nome(pessoa.getNome())
											   .dataNascimento(pessoa.getDataNascimento()).build();
		
		try {
			PessoaEntity pessoaSalva = service.salvarPessoa(p);
			return new ResponseEntity(pessoaSalva, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Editar pessoa")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a pessoa editada"),
			@ApiResponse(code = 400, message = "Retorna a mensagem quando não encontra nenhuma pessoa com o id passado")
	})
	public ResponseEntity<PessoaEntity> editar(@PathVariable(value = "id") long id, @RequestBody PessoaEntity newPessoa) {
		Optional<PessoaEntity> oldPessoa = service.buscarPessoa(id);
        if(oldPessoa.isPresent()){
            PessoaEntity pessoa = oldPessoa.get();
            pessoa.setNome(newPessoa.getNome());
            service.salvarPessoa(pessoa);
            return new ResponseEntity<PessoaEntity>(pessoa, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Deletar pessoa")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna ok apos deletar a pessoa"),
			@ApiResponse(code = 400, message = "Retorna a mensagem se não encontrar nenhuma pessoa com o id passado")
	})
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)
    {
        Optional<PessoaEntity> pessoa = service.buscarPessoa(id);
        if(pessoa.isPresent()){
            service.deletarPessoa(pessoa.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Buscar pessoa por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a pessoa do id especificado"),
			@ApiResponse(code = 400, message = "Retorna a mensagem se não achar nenhuma pessoa com o id passado")
	})
	public ResponseEntity<PessoaEntity> GetById(@PathVariable(value = "id") long id)
    {
        Optional<PessoaEntity> pessoa = service.buscarPessoa(id);
        if(pessoa.isPresent())
            return new ResponseEntity<PessoaEntity>(pessoa.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Listar pessoas")
	@ApiResponse(code = 200, message = "Retorna a lista de pessoas")
    public List<PessoaEntity> Get() {
        return service.listarPessoas();
    }
	
}
