package com.bezkoder.spring.data.jpa.test.service.generic;

import com.bezkoder.spring.data.jpa.test.config.rsql.CustomRsqlVisitor;
import com.bezkoder.spring.data.jpa.test.model.entity.Errors;
import com.bezkoder.spring.data.jpa.test.model.entity.PageE;
import com.bezkoder.spring.data.jpa.test.model.entity.Result;
//import com.bezkoder.spring.data.jpa.test.model.generic.GenericEntity;
import com.bezkoder.spring.data.jpa.test.repository.GenericRepository;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Service
public abstract class GenericServiceImpl<T, D>
    implements GenericService<T, D> {
  @Autowired ModelMapper modelMapper;

  protected abstract Class<T> createT();

  protected abstract Class<D> createD();

  protected abstract GenericRepository<T> getRepository();

  @Override
  public List<T> getAll() {
    return getRepository().findAll();
  }

  public List<Object> valuePage(PageE pageable) {
    try {
      Pageable pg = null;
      List<Object> res = new ArrayList<>();
      if (!(pageable.getFilter() == null)) {
        Sort sort = null;
        if (pageable.getRule().equals(PageE.Rule.ASC)) {
          sort = Sort.by(pageable.getCol()).ascending();
        } else {
          sort = Sort.by(pageable.getCol()).descending();
        }
        pg = PageRequest.of(pageable.getPage(), pageable.getLimit(), sort);
        Node rootNode = new RSQLParser().parse(pageable.getFilter());
        Specification<T> spec = rootNode.accept(new CustomRsqlVisitor<T>());
        List<T> data = getRepository().findAll(spec, pg).getContent();
        for (int i = 0; i < data.size(); i++) {
//          res.add(data.get(i).convertDto(data.get(i)));
        }
      } else {
        Sort sort = null;
        if (pageable.getRule().equals(PageE.Rule.ASC)) {
          sort = Sort.by(pageable.getCol()).ascending();
        } else {
          sort = Sort.by(pageable.getCol()).descending();
        }
        pg = PageRequest.of(pageable.getPage(), pageable.getLimit(), sort);
        List<T> data = getRepository().findAll(pg).getContent();
        for (int i = 0; i < data.size(); i++) {
          res.add(modelMapper.map(data.get(i), createD()));
        }
      }
      return res;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Result get(PageE pageable) {
    Result rs = new Result();
    rs.setData(valuePage(pageable));
    return rs;
  }

  @Override
  public Result getById(int id) {
    Result rs = new Result();
    T data = getRepository().getById(id);
    if (!beUnique(id)) {

    } else {
      rs.setStatus(Result.Status.FAIL);
      List<Errors> errors = new ArrayList<>();
      errors.add(new Errors(404, "Not Found"));
      rs.setError(errors);
    }
    return rs;
  }

  @Override
  public Result update(Object update) {
    try {
      modelMapper = new ModelMapper();
      Result rs = new Result();
      if (update != null) {
        T ob = (T) modelMapper.map(createT(), update.getClass());
//        getRepository().save(getRepository().getById(Integer.parseInt(ob.EntityId())));
        Object updated = modelMapper.map(update, createT().getClass());
        rs.setData(updated);
        return rs;
      } else {
        rs.setStatus(Result.Status.FAIL);
        Errors er = new Errors(400, "Không tồn tại");
        List<Errors> errorList = new ArrayList<>();
        errorList.add(er);
        rs.setError(errorList);
        rs.setData(new ArrayList<>());
        return rs;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Result create(Object newDomain) {
    try {
      Result rs = new Result();
      //      if (beUnique(Integer.parseInt(newDomain.EntityId()))) {
      //        rs.setData(newDomain);
      //        return rs;
      //      } else {
      rs.setStatus(Result.Status.FAIL);
      Errors er = new Errors(400, "Đã tồn tại");
      List<Errors> errorList = new ArrayList<>();
      errorList.add(er);
      rs.setError(errorList);
      rs.setData(new ArrayList<>());
      return rs;
      // }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void delete(int id) {
    // if(!beUnique(id)){
    getRepository().deleteById(id);
    // }
  }

  public boolean beUnique(int id) {
    T obj = getRepository().getById(id);
    if (obj != null) {
      return false;
    } else {
      return true;
    }
  }
}
