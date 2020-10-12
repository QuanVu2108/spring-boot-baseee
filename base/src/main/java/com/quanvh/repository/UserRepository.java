package com.quanvh.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.Repository;

import com.quanvh.domain.User;

public interface UserRepository extends Repository<User, Long> {

	User findByLogin(String login);

	boolean existsByLogin(String login);

	@Transactional
	void deleteByLogin(String login);

	void save(User user);

}
