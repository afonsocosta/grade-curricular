package com.rasmoo.cliente.escola.gradecurricular.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MateriaDto extends RepresentationModel<MateriaDto> {

  private Long id;

  @NotBlank(message = "Informe o nome da materia")
  private String nome;

  @Min(value = 34, message = "Permitido o minimo de 34 horas por materia")
  @Max(value = 120, message = "Permitido o maximo de 120 horas por materia")
  private Integer horas;

  @NotBlank(message = "Informe o codigo da materia")
  private String codigo;

  @Min(value = 1, message = "Permitido o minimo de 1 vez por ano")
  @Max(value = 1, message = "Permitido o maximo de 2 vez por ano")
  private Integer frequencia;

}
