package com.linagora.openpaas.backend.storage;

import org.bson.types.ObjectId;
import com.google.common.base.Objects;

public class User {
	private ObjectId _id;
	private String login;
	
	public User() {
		super();
	}

	public User(String login) {
		super();
		this.login = login;
	}

	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}

	@Override
	public int hashCode(){
		return Objects.hashCode(login);
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof User) {
			User that = (User) object;
			return Objects.equal(this.login, that.login);
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("_id", _id)
			.add("login", login)
			.toString();
	}
	
}
