package com.bezkoder.spring.data.jpa.test;

import com.bezkoder.spring.data.jpa.test.model.entity.Type;
import com.bezkoder.spring.data.jpa.test.repository.TypeRepository;
import com.bezkoder.spring.data.jpa.test.service.TypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
// @ExtendWith(MockitoExtension.class)
// @ExtendWith(SpringExtension.class)
public class TypeServiceTest {

    @MockBean
    TypeRepository typeRepository;

    @Autowired private TypeService typeService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCountAll() {
        List<Type> list = new ArrayList<>();
        Type typeOne = new Type(1, "String", "String");
        Type typeTwo = new Type(2, "String", "String");
        Type typeThree = new Type(3, "String", "String");
        list.add(typeOne);
        list.add(typeTwo);
        list.add(typeThree);

        when(typeRepository.findAll()).thenReturn(list);

        List<Type> empList = typeService.getTypeList();

        assertEquals(3, (long) (empList.size()));
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    public void testGetByid() {

        when(typeRepository.getById(1)).thenReturn(new Type(1, "StringExp", "StringExp"));

        Type tTwo = (Type) typeService.getById(1).getData();

        assertEquals(tTwo.getId(), 1);
        assertEquals(tTwo.getName(), "String");
    }

//    @Test
//    public void testCreate() {
//        TypeEntity type = new TypeEntity(1, "String", "String");
//        when(typeRepository.save(type)).thenReturn(type);
//        ResponseEntity<Result> typeCreate = (ResponseEntity<Result>) typeService.create(type);
//        assertEquals(typeCreate.getBody().getData(), type);
//        //verify(typeRepository, times(1)).save(type);
//    }
//    @Test
//    public void testUpdate() {
//        TypeEntity type = new TypeEntity(1, "String", "String", null);
//        when(typeRepository.save(type)).thenReturn(type);
//        when(typeRepository.getById((int)type.getId())).thenReturn(type);
//        ResponseEntity<Result> typeUpdate = (ResponseEntity<Result>) typeService.update(type);
//        assertEquals(typeUpdate, type);
//        // verify(typeRepository, times(1)).save(type);
//    }

    @Test
    public void testDelete()
    {
        Type type = new Type(1, "String", "String");

        typeService.delete(type.getId());

        verify(typeRepository, times(1)).deleteById(type.getId());
    }

    @Test
    public void testBeUnique() {
        Type type = new Type(1, "String", "String");
        when(typeRepository.getById(type.getId())).thenReturn(null);
        boolean result = typeService.beUnique(type.getId());
        assertTrue(result);
    }
}
