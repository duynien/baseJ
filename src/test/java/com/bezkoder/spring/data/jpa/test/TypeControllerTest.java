package com.bezkoder.spring.data.jpa.test;

import com.bezkoder.spring.data.jpa.test.controller.TypeController;
import com.bezkoder.spring.data.jpa.test.model.entity.PageE;
import com.bezkoder.spring.data.jpa.test.model.entity.Result;
import com.bezkoder.spring.data.jpa.test.model.entity.Type;
import com.bezkoder.spring.data.jpa.test.service.TypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TypeController.class)
// @EnableSpringDataWebSupport
// @AutoConfigureMockMvc
public class TypeControllerTest {
  List<Type> data = new ArrayList<>();
  int size;
  int sizePage;
  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper mapper;
  @MockBean private TypeService typeService;

  @Before
  public void setUp() throws Exception {
    for (int i = 0; i < 23; i++) {
      Type t = new Type(i, "String" + i, "String" + i);
      data.add(t);
    }
    when(typeService.getAll()).thenReturn(data);
    size = data.size();
    sizePage = (int) Math.ceil((double) data.size() / (double) 10);
  }

  @Test
  public void testGetNoRule() throws Exception {
    PageE pageEntity = new PageE();
    pageEntity.setPage(0);
    pageEntity.setLimit(10);
    int offset = pageEntity.getPage() * pageEntity.getLimit();
    List<Object> res = new ArrayList<>();
    Result rs = new Result();
    rs.setData(data.subList(offset, offset + pageEntity.getLimit()));

    given(typeService.get(pageEntity)).willReturn(rs);

    mvc.perform(
            get("/type-tour")
                .param("page", "0")
                .param("limit", "10")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[0].id", is(0)));
  }
  //
  @Test
  public void testGetSortAsc() throws Exception {
    PageE pageEntity = new PageE();
    pageEntity.setPage(1);
    pageEntity.setLimit(10);
    pageEntity.setRule(PageE.Rule.ASC);
    int offset = pageEntity.getPage() * pageEntity.getLimit();
    List<Object> res = new ArrayList<>();
    Result rs = new Result();
    rs.setData(data.subList(offset, offset + pageEntity.getLimit()));

    given(typeService.get(pageEntity)).willReturn(rs);

    mvc.perform(
            get("/type-tour")
                    .param("page", "1")
                    .param("limit", "10")
                    .param("rule","ASC")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].id", is(0)));
  }

  @Test
  public void testGetSortDesc() throws Exception {
    Collections.reverse(data);
    PageE pageEntity = new PageE();
    pageEntity.setRule(PageE.Rule.DESC);
    pageEntity.setPage(1);
    pageEntity.setLimit(10);
    int offset = pageEntity.getPage() * pageEntity.getLimit();
    List<Object> res = new ArrayList<>();
    Result rs = new Result();
    rs.setData(data.subList(offset, offset + pageEntity.getLimit()));

    given(typeService.get(pageEntity)).willReturn(rs);

    mvc.perform(
            get("/type-tour")
                    .param("page", "1")
                    .param("limit", "10")
                    .param("rule","DESC")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].id", is(0)));
  }

  @Test
  public void testGetFilter() throws Exception {
    String filter = "id>1";
    PageE pageEntity = new PageE();
    pageEntity.setPage(0);
    pageEntity.setLimit(10);
    pageEntity.setFilter(filter);
    List<Object> datares = new ArrayList<>();

    for (int i = 2; i < 10; i++) {
      Type t = new Type(i, "String" + i, "String" + i);
      Object ob = new Object();
      datares.add(t);
    }

    Result rs = new Result();
    rs.setData(datares);
    given(typeService.get(pageEntity)).willReturn(rs);
    mvc.perform(
            get("/type-tour")
                    .param("page", "0")
                    .param("limit", "10")
                    .param("filter","id>1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].id", is(2)));
  }

  @Test
  public void testGetById() throws Exception {
    int id = 10;
    Result rs = new Result();
    rs.setData(data.get(id));

    given(typeService.getById(id)).willReturn(rs);

    mvc.perform(get("/type-tour/get/10").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.id", is(7)));
  }

      @Test
      public void testPostType() throws Exception {
          Type t = new Type(24, "String", "String ");
          Result rs = new Result();
          rs.setData(t);
          String json = mapper.writeValueAsString(t);
          given(typeService.create(ArgumentMatchers.any()))
                  .willReturn(rs);
          mvc.perform(
                  post("/type-tour")
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON)
                          .content(json)
                          .characterEncoding("utf-8"))
                  .andExpect(status().isCreated())
                  .andExpect(jsonPath("$.status", is("SUCCESS")))
                  .andExpect(jsonPath("$.data.name", is("String")));
      }

      @Test
      public void testPutType() throws Exception {
          Type t = new Type(7, "String8", "String8");
          Result rs = new Result();
          rs.setData(t);
          String json = mapper.writeValueAsString(t);
          given(typeService.update(ArgumentMatchers.any()))
                  .willReturn(rs);
          mvc.perform(
                  put("/type-tour")
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON)
                          .content(json)
                          .characterEncoding("utf-8"))
                  .andExpect(jsonPath("$.status", is("SUCCESS")))
                  .andExpect(jsonPath("$.data.name", is("String7")));
      }

  @Test
  public void testDeleteType() throws Exception {
    mvc.perform(
            delete("/type-tour/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andExpect(status().isAccepted());
  }
}
