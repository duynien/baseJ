package com.bezkoder.spring.data.jpa.test.controller;

import com.bezkoder.spring.data.jpa.test.model.entity.PageE;
import com.bezkoder.spring.data.jpa.test.model.entity.Result;
import com.bezkoder.spring.data.jpa.test.service.generic.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseControllerImpl<T,D> implements BaseController<T,D> {

  protected abstract GenericService<T,D> getService();

  // @GetMapping("")
  //    @ApiImplicitParams({
  //            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
  //                    value = "Results page you want to retrieve (0..N)"),
  //            @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
  //                    value = "Number of records per page."),
  //            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string",
  // paramType = "query",
  //                    value = "Sorting criteria in the format: property(,asc|desc). " +
  //                            "Default sort order is ascending. " +
  //                            "Multiple sort criteria are supported.")
  //    })
  //    public ResponseEntity<Result> getPage(@ApiIgnore @PageableDefault(size = 10) Pageable
  // pageable, @RequestParam(value = "filter",required = false) String filter){
  //        return service().getPage(pageable,filter);
  //    }
  @GetMapping("")
  public ResponseEntity<Result> getPage(PageE pageEntity) {
    return ResponseEntity.ok(getService().get(pageEntity));
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Result> getOne(@PathVariable(value = "id", required = false) String id) {
    return ResponseEntity.ok(getService().getById(Integer.parseInt(id)));
  }

  @PutMapping("")
  public ResponseEntity<Result> update(@RequestBody D updated) {
    return ResponseEntity.ok(getService().update(updated));
  }

  @PostMapping("")
  public ResponseEntity<Result> create(@RequestBody D created) {
    return ResponseEntity.status(HttpStatus.CREATED).body(getService().create(created));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable int id) {
    getService().delete(id);
    return ResponseEntity.status(202).body("DELETE");
  }
}
