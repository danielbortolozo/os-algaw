package br.com.sisdb.os.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.sisdb.os.api.model.OrdemServicoDTO;
import br.com.sisdb.os.api.model.OrdemServicoInput;
import br.com.sisdb.os.domain.model.OrdemServico;
import br.com.sisdb.os.domain.repository.OrdemServicoRepository;
import br.com.sisdb.os.domain.service.GestaoOrdemService;

@RestController
@RequestMapping("/ordem-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemService gestaoOrdemServ;
	
	@Autowired
	private OrdemServicoRepository ordemRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoDTO criar(@Valid @RequestBody OrdemServicoInput ordemSerInput) {	
		OrdemServico ordemServico = toEntity(ordemSerInput);
		return toDTO(gestaoOrdemServ.criar(ordemServico));
	}
	
	@GetMapping
	public List<OrdemServicoDTO> listar() {		
		return toCollectionDTO(ordemRepo.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable Long id){
		
		Optional<OrdemServico> ordem = ordemRepo.findById(id);
		
		if (ordem.isPresent()) {
			OrdemServicoDTO dto = toDTO(ordem.get());
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao") 
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServ.finalizar(ordemServicoId);		
	}

	private OrdemServicoDTO toDTO(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoDTO.class);
	}
	private List<OrdemServicoDTO> toCollectionDTO(List<OrdemServico> ordensServico){
		return ordensServico.stream()
				.map(ordemServico -> toDTO(ordemServico))
				.collect(Collectors.toList());
	}
	private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}











