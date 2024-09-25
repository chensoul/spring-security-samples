package com.chensoul.lss.service;

import com.chensoul.lss.web.model.User;

public interface UserService {

  User findById(Long id);

  User createUser(User user);

  User updateUser(User user);

}
