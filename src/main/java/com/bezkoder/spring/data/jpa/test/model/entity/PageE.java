package com.bezkoder.spring.data.jpa.test.model.entity;

import lombok.Data;

@Data
public class PageE {
  private int limit = 10;
  private int page = 0;
  private String filter;
  private String col = "id";
  private Rule rule = Rule.ASC;

  public enum Rule {
    ASC,
    DESC;
  }
}
