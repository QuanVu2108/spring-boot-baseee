package com.quanvh.dto;

import java.util.List;

import com.quanvh.domain.Role;


public class UserResponseDTO {

  private Integer id;

  private String login;

  private String email;

  List<Role> roles;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLogin() {
	return login;
}

public void setLogin(String login) {
	this.login = login;
}

public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

}
