package br.com.sisdb.os.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.sisdb.os.domain.model.StatusOrdemSer;

public class OrdemServicoDTO {

	
	private Long id;
	private String nomeCliente;
	private ClienteDTO cliente;
	private String descricao;
	private BigDecimal preco;
	private StatusOrdemSer status;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFechamento;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public StatusOrdemSer getStatus() {
		return status;
	}
	public void setStatus(StatusOrdemSer status) {
		this.status = status;
	}
	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}
	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	public OffsetDateTime getDataFechamento() {
		return dataFechamento;
	}
	public void setDataFechamento(OffsetDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	public ClienteDTO getCliente() {
		return cliente;
	}
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	
	
	
	
	
	
}
