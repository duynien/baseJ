package com.bezkoder.spring.data.jpa.test.model.entity;

import com.bezkoder.spring.data.jpa.test.model.dto.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "App_Role")
public class App_Role {
  @Id
  @GeneratedValue
  @Column(name = "Role_Id", nullable = false)
  private Integer ROLE_ID;

  @Enumerated(EnumType.STRING)
  @Column(name = "Role_Name", length = 30, nullable = false)
  private ERole roleName;

  @ManyToMany(mappedBy = "roles")
  private Set<App_User> users;



  public Integer getROLE_ID() {
    return ROLE_ID;
  }

  public void setROLE_ID(Integer ROLE_ID) {
    this.ROLE_ID = ROLE_ID;
  }

  public ERole getRoleName() {
    return roleName;
  }

  public void setRoleName(ERole roleName) {
    this.roleName = roleName;
  }

  public Set<App_User> getUsers() {
    return users;
  }

  public void setUsers(Set<App_User> users) {
    this.users = users;
  }
}
