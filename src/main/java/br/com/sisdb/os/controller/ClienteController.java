package br.com.sisdb.os.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.sisdb.os.domain.model.Cliente;
import br.com.sisdb.os.domain.repository.ClienteRepository;
import br.com.sisdb.os.domain.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private ClienteService service;
	
	@GetMapping
	public List<Cliente> listar() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id) {
		Optional<Cliente> cli = repo.findById(id);
		
		if (cli.isPresent()) {
			return ResponseEntity.ok(cli.get());
		}		
		return ResponseEntity.notFound().build();		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {		
		return service.salvar(cliente);
	}
	
	@PutMapping("{id}") 
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cliente cli){
		
		if (!repo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cli.setId(id);
		cli = service.salvar(cli);
		return ResponseEntity.ok(cli);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if (!repo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
