package com.linagora.openpaas.backend.storage;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.linagora.openpaas.backend.storage.User.Id;
import com.mongodb.DB;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class MongoUserDAO implements UserDAO {

	private static final String USERS_COLLECTION = "users"; 
	private final Jongo jongo;

	public MongoUserDAO(DB db) {
		this.jongo = new Jongo(db);
		users().ensureIndex("{login:1}", "{unique:true}");
	}

	@Override
	public User create(User user) {
		try {
			WriteResult result = users().save(user);
			assertNoPendingError(result);
		} catch (MongoException.DuplicateKey e) {
			throw new DuplicateLoginException(user.getLogin());
		}
		return user;
	}

	private void assertNoPendingError(WriteResult result) {
		MongoException exception = result.getLastError().getException();
		if (exception != null) {
			throw exception;
		}
	}

	@Override
	public Iterable<User> findAll() {
		return users().find().as(User.class);
	}
	
	@Override
	public User getUser(Id userId) {
		return users().findOne(userId.getId()).as(User.class);
	}
	
	private MongoCollection users() {
		return jongo.getCollection(USERS_COLLECTION);
	}

}
