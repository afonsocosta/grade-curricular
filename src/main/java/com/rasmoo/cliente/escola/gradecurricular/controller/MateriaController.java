package com.rasmoo.cliente.escola.gradecurricular.controller;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rasmoo.cliente.escola.gradecurricular.entity.Materia;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.service.MateriaService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materia")
@RequiredArgsConstructor
public class MateriaController {

  private final MateriaService materiaService;

  private static final String DELETE = "DELETE";
  private static final String UPDATE = "UPDATE";

  @GetMapping
  public ResponseEntity<List<Materia>> getMaterias() {
    return ResponseEntity.status(HttpStatus.OK).body(materiaService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<Materia>> getMateria(@PathVariable Long id) {
    Response<Materia> response = new Response<>();
    response.setData(materiaService.findById(id));
    response.setStatusCode(HttpStatus.OK.value());
    response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).getMateria(id)).withSelfRel());
    response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).deleteMateria(id)).withRel(DELETE));
    response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).updateMateria(new MateriaDto())).withRel(UPDATE).withType("GET"));
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Materia> deleteMateria(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(materiaService.findById(id));
  }

  @PostMapping
  public ResponseEntity<Boolean> saveMateria(@RequestBody @Valid MateriaDto materia) {
    return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.save(materia));
  }

  @PutMapping
  public ResponseEntity<Boolean> updateMateria(@RequestBody @Valid MateriaDto materia) {
    return ResponseEntity.status(HttpStatus.CREATED).body(materiaService.updateMateria(materia));
  }


}
