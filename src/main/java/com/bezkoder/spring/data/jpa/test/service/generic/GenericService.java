package com.bezkoder.spring.data.jpa.test.service.generic;

import com.bezkoder.spring.data.jpa.test.model.entity.PageE;
import com.bezkoder.spring.data.jpa.test.model.entity.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenericService<T,D> {
    List<T> getAll();
    public Result get(PageE pageable);
    public Result getById(int id);
    public Result update(D updated);
    public Result create(D newDomain);
    public void delete(int id);
    public boolean beUnique(int id);
}
