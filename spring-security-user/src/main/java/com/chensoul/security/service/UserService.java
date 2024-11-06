package com.chensoul.security.service;

import com.chensoul.security.persistence.User;

public interface UserService {

  User findById(Long id);

  User createUser(User user);

  User updateUser(User user);

}
