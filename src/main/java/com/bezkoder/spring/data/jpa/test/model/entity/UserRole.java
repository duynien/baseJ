//package com.bezkoder.spring.data.jpa.test.model.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//@NoArgsConstructor
//@Table(name = "User_Role")
//public class UserRole {
//  @Id
//  @GeneratedValue
//  @Column(name = "Id", nullable = false)
//  private Integer id;
//
//  @Column(name = "USER_ID", nullable = false)
//  private Integer userId;
//
//  @Column(name = "USER_ID", nullable = false)
//  private Integer roleId;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "User_Id", nullable = false)
//  private App_User appUser;
//
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "Role_Id", nullable = false)
//  private App_Role appRole;
//
//  public UserRole(Integer id, Integer userId, Integer roleId) {
//    this.id = id;
//    this.userId = userId;
//    this.roleId = roleId;
//  }
//}
