package com.bezkoder.spring.data.jpa.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Status status=Status.SUCCESS;
    private Object data;
    private List<Errors> error = new ArrayList<>();

    public enum Status{
        SUCCESS,FAIL;
    }
}
