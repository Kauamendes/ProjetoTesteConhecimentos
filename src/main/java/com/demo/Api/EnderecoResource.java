package com.demo.Api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/endereco")
public class EnderecoResource {

private EnderecoService service;

	@Autowired
	private PessoaService pessoaservice;

	private EnderecoResource(EnderecoService service) {
		this.service = service;
	}
	
	@PostMapping(value = "/pessoa/{id}", produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Salvar endereco de uma pessoa")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Retorna o endereço salva"),
			@ApiResponse(code = 400, message = "Retorna a mensagem de erro")
	})
	public ResponseEntity salvar(@RequestBody EnderecoEntity endereco, @PathVariable(value = "id") long id) {
		Optional<PessoaEntity> p = pessoaservice.buscarPessoa(id);
		EnderecoEntity end = new EnderecoEntity().builder().logradouro(endereco.getLogradouro())
															.cep(endereco.getCep())
															.cidade(endereco.getCidade())
															.pessoa(p.get()).build();
		
		try {
			EnderecoEntity enderecoSalvo = service.salvarEndereco(end);
			return new ResponseEntity(enderecoSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Editar endereço")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o endereço editada"),
			@ApiResponse(code = 404, message = "Retorna a mensagem quando não encontra nenhuma pessoa com o id passado")
	})
	public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)  {
		
        Optional<EnderecoEntity> endereco = service.buscarEnderecoPorId(id);
        if(endereco.isPresent()){
            service.deletarEndereco(endereco.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Buscar endereço por id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o endereço com o id passado"),
			@ApiResponse(code = 400, message = "Retorna a mensagem quando não encontra nenhuma pessoa com o id passado")
	})
	public ResponseEntity<EnderecoEntity> GetById(@PathVariable(value = "id") long id)
    {
        Optional<EnderecoEntity> endereco = service.buscarEnderecoPorId(id);
        if(endereco.isPresent())
            return new ResponseEntity<EnderecoEntity>(endereco.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value = "pessoa/{id}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	@ApiOperation(value = "Listar endereços de uma pessoa")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna os endereços da pessoa"),
			@ApiResponse(code = 400, message = "Retorna a mensagem quando não encontra nenhuma pessoa com o id passado")
	})
	public List<EnderecoEntity> GetEnderecosPessoaById(@PathVariable(value = "id") long id) {
        return service.listarEnderecosPessoa(id);
	}
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Listar endereços")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de enderecos"),
			@ApiResponse(code = 400, message = "Retorna a mensagem quando não encontra nenhuma pessoa com o id especificado")
	})
	public List<EnderecoEntity> GetEnderecos() {
        return service.listarEnderecos();
	}
	
}
