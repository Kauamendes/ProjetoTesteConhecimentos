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

@RestController
@RequestMapping("/api/endereco")
public class EnderecoResource {

private EnderecoService service;

	@Autowired
	private PessoaService pessoaservice;

	private EnderecoResource(EnderecoService service) {
		this.service = service;
	}
	
	@PostMapping(value = "/pessoa/{id}")
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
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id)  {
		
        Optional<EnderecoEntity> endereco = service.buscarEnderecoPorId(id);
        if(endereco.isPresent()){
            service.deletarEndereco(endereco.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EnderecoEntity> GetById(@PathVariable(value = "id") long id)
    {
        Optional<EnderecoEntity> endereco = service.buscarEnderecoPorId(id);
        if(endereco.isPresent())
            return new ResponseEntity<EnderecoEntity>(endereco.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value = "pessoa/{id}", method = RequestMethod.GET)
    public List<EnderecoEntity> GetEnderecosPessoaById(@PathVariable(value = "id") long id) {
        return service.listarEnderecosPessoa(id);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public List<EnderecoEntity> GetEnderecosPessoaById() {
        return service.listarEnderecos();
	}
	
}
