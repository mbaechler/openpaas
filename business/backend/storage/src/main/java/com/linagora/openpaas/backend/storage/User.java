package com.linagora.openpaas.backend.storage;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class User {
	
	public static class Id {
		private final ObjectId id;

		private Id(ObjectId id) {
			this.id = id;
		}
		
		private Id(String id) {
			this.id = ObjectId.massageToObjectId(id);
		}
		
		public ObjectId getId() {
			return id;
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private String login;

		public Builder() {
		}
		
		public Builder login(String login) {
			this.login = login;
			return this;
		}
		
		public User build() {
			return new User(null, login);
		}
	}

	private ObjectId _id;
	@JsonProperty private String login;
	
	private User() {}
	
	private User(Id _id, String login) {
		super();
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

	public Id getId() {
		return new Id(_id);
	}
	
}
