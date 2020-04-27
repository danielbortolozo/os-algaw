package br.com.sisdb.os.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.sisdb.os.domain.exception.EntidadeNaoEncontradaException;
import br.com.sisdb.os.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(NegocioException e, WebRequest request) {
		HttpStatus status =  HttpStatus.NOT_FOUND;
		Problema prob = new Problema();
		prob.setStatus(status.value());
		prob.setTitulo(e.getMessage());
		prob.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(e, prob, new HttpHeaders(), status, request);
	}
	
	
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException e, WebRequest request) {
		HttpStatus status =  HttpStatus.BAD_REQUEST;
		Problema prob = new Problema();
		prob.setStatus(status.value());
		prob.setTitulo(e.getMessage());
		prob.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(e, prob, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Problema problema = new Problema();
		List<Problema.Campo> campos = new ArrayList<>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			System.out.println("entrei no error");
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			System.out.println("Mensagem :"+ mensagem);
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto.");
		problema.setCampos(campos);

		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	} 
}
