package com.bezkoder.spring.data.jpa.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "App_User")
// , //
//        uniqueConstraints = { //
//                @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name") })
public class App_User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "User_Id")
  private Integer USER_ID;

  @Column(name = "User_Name", length = 36, nullable = false , unique = true)
  private String userName;

  @Column(name = "Password", length = 128, nullable = false)
  private String password;

  @Column(name = "Enabled", length = 1, nullable = false)
  private boolean enabled;

  @ManyToMany
  @JoinTable(
      name = "User_Role",
      joinColumns = @JoinColumn(name = "USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  private Set<App_Role> roles;

  public App_User(String userName, String password, boolean enabled) {
    this.userName = userName;
    this.password = password;
    this.enabled = enabled;
  }

  public Integer getUSER_ID() {
    return USER_ID;
  }

  public void setUSER_ID(Integer USER_ID) {
    this.USER_ID = USER_ID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<App_Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<App_Role> roles) {
    this.roles = roles;
  }
}
