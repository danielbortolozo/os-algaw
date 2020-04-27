package br.com.sisdb.os.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.sisdb.os.api.model.ComentarioDTO;
import br.com.sisdb.os.api.model.ComentarioInput;
import br.com.sisdb.os.domain.exception.EntidadeNaoEncontradaException;
import br.com.sisdb.os.domain.model.Comentario;
import br.com.sisdb.os.domain.model.OrdemServico;
import br.com.sisdb.os.domain.repository.OrdemServicoRepository;
import br.com.sisdb.os.domain.service.GestaoOrdemService;

@RestController
@RequestMapping("/ordem-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

	
	@Autowired
	private GestaoOrdemService ordemServico;
	
	@Autowired
	private OrdemServicoRepository ordemRepo;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioDTO adicionar(@PathVariable Long ordemServicoId, @RequestBody 
			                                       @Valid ComentarioInput comentarioInput){
		
		Comentario comentario = ordemServico.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
		
		return toModel(comentario);
	}
	
	@GetMapping
	private List<ComentarioDTO> listar(@PathVariable Long ordemServicoId) {
		OrdemServico ordemServico = ordemRepo.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrado!!!") );
		
		return toCollectionDTO(ordemServico.getComentarios());
	}

	private List<ComentarioDTO> toCollectionDTO(List<Comentario> comentarios) {
				
		return comentarios.stream().map(obj -> toModel(obj))
				          .collect(Collectors.toList());		
	}

	private ComentarioDTO toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioDTO.class );
	}
	
	
}
















