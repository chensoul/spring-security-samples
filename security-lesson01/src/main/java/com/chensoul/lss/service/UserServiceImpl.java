package com.chensoul.lss.service;

import com.chensoul.lss.persistence.UserRepository;
import com.chensoul.lss.web.model.User;
import com.chensoul.lss.web.validation.UsernameExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
class UserServiceImpl implements UserService {
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final UserRepository repository;

	@Override
	public User findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("user not exists"));
	}

	@Override
	public User createUser(final User user) {
		if (usernameExist(user.getUsername())) {
			throw new UsernameExistsException("There is an account with the username: " + user.getUsername());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}

	private boolean usernameExist(String username) {
		final User user = repository.findByUsername(username);
		return user != null;
	}

	@Override
	public User updateUser(User user) {
		final Long id = user.getId();
		final String username = user.getUsername();
		final User usernameOwner = repository.findByUsername(username);
		if (usernameOwner != null && !id.equals(usernameOwner.getId())) {
			throw new UsernameExistsException("Email not available.");
		}
		if (user.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		return repository.save(user);
	}

}