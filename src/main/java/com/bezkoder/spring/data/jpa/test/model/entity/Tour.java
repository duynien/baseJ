package com.bezkoder.spring.data.jpa.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tour")
public class Tour implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  private String name;
  private String time;
  private int quantity;
  private double cost;
  private int status;
  private String desc;

  @Column(name = "addressid", nullable = false)
  private int addressid;

  @Column(name = "typetourid", nullable = false)
  private int typetourid;

  //    @JsonIgnore
  @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "typetourid", insertable = false, updatable = false)
  private Type type;

//  @Override
//  public String EntityId() {
//    return String.valueOf(this.id);
//  }
//
//  @Override
//  public Object convertDto(Tour tour) {
////    ModelMapper mapper = new ModelMapper();
////    Tour t = mapper.map(tour, TourD.class);
//    return null;
//  }

}
