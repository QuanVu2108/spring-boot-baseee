package com.quanvh.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quanvh.domain.User;
import com.quanvh.repository.UserRepository;


@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    final User user = usersRepository.findByLogin(login);

    if (user == null) {
      throw new UsernameNotFoundException("User '" + login + "' not found");
    }

    return org.springframework.security.core.userdetails.User//
        .withUsername(login)//
        .password(user.getEncrytedPassword())//
        .authorities(user.getRoles())//
        .accountExpired(false)//
        .accountLocked(false)//
        .credentialsExpired(false)//
        .disabled(false)//
        .build();
  }

}
