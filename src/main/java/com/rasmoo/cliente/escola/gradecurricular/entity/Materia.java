package com.rasmoo.cliente.escola.gradecurricular.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "materia")
@Data
@NoArgsConstructor
public class Materia implements Serializable {

  private static final long serialVersionUID = 9103812702133489447L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  @JsonInclude(Include.NON_NULL)
  @Column(name = "id")
  private Long id;

  @JsonInclude(Include.NON_EMPTY)
  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "horas")
  private Integer horas;

  @JsonInclude(Include.NON_EMPTY)
  @Column(name = "codigo")
  private String codigo;

  @Column(name = "frequencia")
  private Integer frequencia;

}
