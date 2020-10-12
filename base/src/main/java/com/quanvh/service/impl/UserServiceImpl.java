package com.quanvh.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quanvh.domain.User;
import com.quanvh.exception.CustomException;
import com.quanvh.repository.UserRepository;
import com.quanvh.security.JwtTokenProvider;
import com.quanvh.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public String signin(String login, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
      return jwtTokenProvider.createToken(login, userRepository.findByLogin(login).getRoles());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  @Override
  public String signup(User user) {
    if (!userRepository.existsByLogin(user.getLogin())) {
      user.setEncrytedPassword(passwordEncoder.encode(user.getEncrytedPassword()));
      userRepository.save(user);
      return jwtTokenProvider.createToken(user.getLogin(), user.getRoles());
    } else {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  @Override
  public void delete(String login) {
	  userRepository.deleteByLogin(login);
  }

  @Override
  public User search(String login) {
    User user = userRepository.findByLogin(login);
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  @Override
  public User whoami(HttpServletRequest req) {
    return userRepository.findByLogin(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  @Override
  public String refresh(String login) {
    return jwtTokenProvider.createToken(login, userRepository.findByLogin(login).getRoles());
  }

}

