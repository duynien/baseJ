package com.bezkoder.spring.data.jpa.test.controller;

import com.bezkoder.spring.data.jpa.test.model.entity.PageE;
import com.bezkoder.spring.data.jpa.test.model.entity.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface BaseController<T,D> {
  @GetMapping("")
  public ResponseEntity<Result> getPage(PageE pageEntity);

  @GetMapping("/{id}")
  public ResponseEntity<Result> getOne(@PathVariable(value = "id", required = false) String id);

  @PutMapping("")
  public ResponseEntity<Result> update(@RequestBody D updated);

  @PostMapping("")
  public ResponseEntity<Result> create(@RequestBody D created);

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable int id);
}
