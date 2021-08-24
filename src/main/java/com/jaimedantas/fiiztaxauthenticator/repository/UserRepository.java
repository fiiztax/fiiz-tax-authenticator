package com.jaimedantas.fiiztaxauthenticator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaimedantas.fiiztaxauthenticator.table.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  List<User> findAllById(long id);
}
