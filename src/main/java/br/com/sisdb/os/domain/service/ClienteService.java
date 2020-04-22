package br.com.sisdb.os.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sisdb.os.domain.exception.NegocioException;
import br.com.sisdb.os.domain.model.Cliente;
import br.com.sisdb.os.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository cliRepository;
	
	
	public Cliente salvar(Cliente cli) {
		System.out.println("Entrei em salvar service");
		Cliente clienteExistente = cliRepository.findByEmail(cli.getEmail());
		System.out.println("cliente existente :"+clienteExistente);
		if (clienteExistente != null && !clienteExistente.equals(cli)) {
			throw new NegocioException("Já existe um cliente cadastrado com este e-mail");
		}
		
		return cliRepository.save(cli);
	}
	
	public void remover(Long id) {
		cliRepository.deleteById(id);
	}
	
}





