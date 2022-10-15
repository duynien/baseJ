package com.bezkoder.spring.data.jpa.test.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "App_User")
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

  @Column(name = "fullname", length = 128, nullable = false)
  private String fullname;

  @Column(name = "phone", length = 128, nullable = false)
  private String phone;

  @Column(name = "email", length = 128, nullable = false)
  @Email
  private String email;

  @Column(name = "city", length = 128, nullable = false)
  private String city;

  @ManyToMany
  @JoinTable(
      name = "User_Role",
      joinColumns = @JoinColumn(name = "USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  private Set<App_Role> roles;
  public App_User(Integer USER_ID, String userName, String password, boolean enabled, String fullname, String phone, String email, String city) {
    this.USER_ID = USER_ID;
    this.userName = userName;
    this.password = password;
    this.enabled = enabled;
    this.fullname = fullname;
    this.phone = phone;
    this.email = email;
    this.city = city;
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

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Set<App_Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<App_Role> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "App_User{" +
            "USER_ID=" + USER_ID +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", enabled=" + enabled +
            ", fullname='" + fullname + '\'' +
            ", phone='" + phone + '\'' +
            ", email='" + email + '\'' +
            ", city='" + city + '\'' +
            ", roles=" + roles +
            '}';
  }
}
