package com.rasmoo.cliente.escola.gradecurricular.handler;

import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorResponse;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorResponse.ErrorResponseBuilder;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceHandler {

  @ExceptionHandler(MateriaException.class)
  public ResponseEntity<Response<String>> handlerMateriaException(MateriaException materiaException) {
    Response<String> response = new Response<>();
    response.setStatusCode(materiaException.getHttpStatus().value());
    response.setData(materiaException.getMessage());
    return ResponseEntity.status(materiaException.getHttpStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    ErrorResponseBuilder builder = ErrorResponse.builder();
    builder.httpStatus(HttpStatus.BAD_GATEWAY.value());
    builder.mensagem(e.getMessage());
    builder.timeStamp(System.currentTimeMillis());
    e.getBindingResult().getAllErrors().forEach(error -> {
      String campo = ((FieldError) error).getField();
      String mensagem = error.getDefaultMessage();
      errors.put(campo, mensagem);
    });
    builder.errors(errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.build());
  }


}
