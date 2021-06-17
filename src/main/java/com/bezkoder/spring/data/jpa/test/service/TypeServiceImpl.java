package com.bezkoder.spring.data.jpa.test.service;

import com.bezkoder.spring.data.jpa.test.model.dto.TypeDto;
import com.bezkoder.spring.data.jpa.test.model.entity.Type;
import com.bezkoder.spring.data.jpa.test.repository.GenericRepository;
import com.bezkoder.spring.data.jpa.test.repository.TypeRepository;
import com.bezkoder.spring.data.jpa.test.service.generic.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TypeService")
public class TypeServiceImpl extends GenericServiceImpl<Type, TypeDto> implements TypeService {

  @Autowired TypeRepository typeRepository;

  @Override
  public List<Type> getTypeList() {
    return typeRepository.findAll();
  }

  @Override
  protected Class<Type> createT() {
    return Type.class;
  }

  @Override
  protected Class<TypeDto> createD() {
    return TypeDto.class;
  }

  @Override
  protected GenericRepository<Type> getRepository() {
    return typeRepository;
  }
}
