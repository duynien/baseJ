package com.bezkoder.spring.data.jpa.test.service;

import com.bezkoder.spring.data.jpa.test.model.dto.TypeDto;
import com.bezkoder.spring.data.jpa.test.model.entity.Type;
import com.bezkoder.spring.data.jpa.test.service.generic.GenericService;

import java.util.List;

public interface TypeService extends GenericService<Type, TypeDto> {
  public List<Type> getTypeList();
}
