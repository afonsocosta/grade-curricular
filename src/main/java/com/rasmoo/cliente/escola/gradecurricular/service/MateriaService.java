package com.rasmoo.cliente.escola.gradecurricular.service;

import com.rasmoo.cliente.escola.gradecurricular.controller.MateriaController;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDto;
import com.rasmoo.cliente.escola.gradecurricular.entity.Materia;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.MateriaRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "materia")
public class MateriaService {

  private final MateriaRepository materiaRepository;

  @CachePut(unless = "#result.size() < 3")
  public List<Materia> findAll() {
    ModelMapper mapper = new ModelMapper();
    List<MateriaDto> materiaDtoList = mapper.map(materiaRepository.findAll(), new TypeToken<List<MateriaDto>>(){}.getType());
    materiaDtoList.forEach(materiaDto -> {
      materiaDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).getMateria(materiaDto.getId())).withSelfRel());
    });
    return materiaRepository.findAll();
  }

  @CachePut(key = "#id")
  public Materia findById(Long id) {
    try {
      Optional<Materia> materiaOptional = materiaRepository.findById(id);
      if(materiaOptional.isPresent()){
        return materiaOptional.get();
      }
      throw new MateriaException("Materia nao encontrada", HttpStatus.NOT_FOUND);
    }catch (MateriaException m){
      log.error("Error while finding materia", m);
      throw m;
    }catch (Exception e){
      throw new MateriaException("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public Boolean updateMateria(MateriaDto materiaDto) {
    try {
      this.findById(materiaDto.getId());
      ModelMapper mapper = new ModelMapper();
      Materia materia = mapper.map(materiaDto, Materia.class);
      this.materiaRepository.save(materia);
      return Boolean.TRUE;
    }catch (MateriaException m){
      throw m;
    }catch (Exception e){
      throw e;
    }
  }

  public Boolean save(MateriaDto materiaDto) {
    try {
      ModelMapper mapper = new ModelMapper();
      Materia materia = mapper.map(materiaDto, Materia.class);
      materiaRepository.save(materia);
      return Boolean.TRUE;
    }catch (Exception e){
      throw new MateriaException("Erro interno no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public void delete(Long id){
    try {
      this.findById(id);
      materiaRepository.deleteById(id);
    }catch (MateriaException m){
      throw m;
    }catch (Exception e){
      throw e;
    }
  }
}
