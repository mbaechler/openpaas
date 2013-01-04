package com.linagora.openpaas.backend.storage;


public interface UserDAO {

	public User getUser(User.Id userId);
	public User create(User user);
	public Iterable<User> findAll();
	
}
