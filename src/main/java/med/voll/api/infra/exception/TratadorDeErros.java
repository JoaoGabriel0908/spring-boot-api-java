package med.voll.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

//Classe Java que não é carregada automaticamente pelo Spring, precisamos da anotação
//Classe responsável para lidar com excessões personalizadas

@RestControllerAdvice
public class TratadorDeErros {
	
//	Quando alguma controller dar erro "EntityNotFound" ele chama esse método
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> tratarErros404() {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> tratarErros400(MethodArgumentNotValidException exception) {
		var erros = exception.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErrorsValidations::new).toList());
	}
	
	private record DadosErrorsValidations(String campo, String message) {
		public DadosErrorsValidations(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
	
}
