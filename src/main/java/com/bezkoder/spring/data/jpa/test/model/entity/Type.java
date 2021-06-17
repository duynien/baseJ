package com.bezkoder.spring.data.jpa.test.model.entity;

import com.bezkoder.spring.data.jpa.test.model.dto.TypeDto;
//import com.bezkoder.spring.data.jpa.test.model.generic.GenericEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "typetour")
public class Type {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "image")
  private String image;

  @OneToMany(mappedBy = "type", cascade = CascadeType.MERGE)
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @JsonIgnore
  private Collection<Tour> tour;


  public Type(int id, String name, String image) {
    this.id = id;
    this.name = name;
    this.image = image;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return "Type{" + "id=" + id + ", name='" + name + '\'' + ", image='" + image + '\'' + '}';
  }
//
//  @Override
//  public String EntityId() {
//    return String.valueOf(id);
//  }

//  @Override
//  public Object convertDto(Type type) {
//    ModelMapper mapper = new ModelMapper();
//    TypeDto typeDto = mapper.map(type,new TypeDto().getClass());
//    return typeDto;
//  }
}
