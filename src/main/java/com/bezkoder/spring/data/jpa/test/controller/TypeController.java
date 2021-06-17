package com.bezkoder.spring.data.jpa.test.controller;

import com.bezkoder.spring.data.jpa.test.model.dto.TypeDto;
import com.bezkoder.spring.data.jpa.test.model.entity.Type;
import com.bezkoder.spring.data.jpa.test.service.TypeService;
import com.bezkoder.spring.data.jpa.test.service.generic.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("type-tour")
public class TypeController extends BaseControllerImpl<Type, TypeDto>
    implements BaseController<Type, TypeDto> {

  @Autowired private TypeService typeService;

  @Override
  protected GenericService<Type, TypeDto> getService() {
    return typeService;
  }

  @GetMapping("/abcs")
  public List<Type> abc() {
    return typeService.getTypeList();
  }
}
