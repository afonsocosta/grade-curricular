package com.rasmoo.cliente.escola.gradecurricular.model;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

  private String mensagem;
  private int httpStatus;
  private long timeStamp;
  private Map<String, String> errors;
}
