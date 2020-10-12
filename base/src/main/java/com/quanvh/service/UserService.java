package com.quanvh.service;

import javax.servlet.http.HttpServletRequest;

import com.quanvh.domain.User;

public interface UserService {

	String signin(String login, String password);

	String signup(User user);

	void delete(String login);

	User search(String login);

	User whoami(HttpServletRequest req);

	String refresh(String login);

}
