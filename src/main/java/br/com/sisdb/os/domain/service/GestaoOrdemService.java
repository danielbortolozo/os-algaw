package br.com.sisdb.os.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sisdb.os.domain.exception.EntidadeNaoEncontradaException;
import br.com.sisdb.os.domain.exception.NegocioException;
import br.com.sisdb.os.domain.model.Cliente;
import br.com.sisdb.os.domain.model.Comentario;
import br.com.sisdb.os.domain.model.OrdemServico;
import br.com.sisdb.os.domain.model.StatusOrdemSer;
import br.com.sisdb.os.domain.repository.ClienteRepository;
import br.com.sisdb.os.domain.repository.ComentarioRepository;
import br.com.sisdb.os.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemService {

	@Autowired
	private OrdemServicoRepository ordemRepo;
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private ComentarioRepository comentRepo;
	
	
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordem = buscarOrdemServico(ordemServicoId);
		
		ordem.finalizar();
		
		ordemRepo.save(ordem);
	}
	
	
	public OrdemServico criar(OrdemServico ordemServico) {		
		Cliente cliente = clienteRepo.findById(ordemServico.getCliente().getId())
		                             .orElseThrow(() -> new NegocioException("Cliente não encontrado!!!"));		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemSer.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		//ordemServico.setDataFinalizacao(LocalDateTime.now());		
		return ordemRepo.save(ordemServico);
	}

	
	public Comentario adicionarComentario(Long ordemId, String descricao) {
		OrdemServico ordemServico = buscarOrdemServico(ordemId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		return comentRepo.save(comentario);
	}


	private OrdemServico buscarOrdemServico(Long ordemId) {
		return ordemRepo.findById(ordemId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem serciço não encontrado!!!"));
	}
	
}




