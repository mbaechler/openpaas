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
		
		public Id(String id) {
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
		private String firstname;
		private String lastname;
		private String email;

		public Builder() {
		}
		
		public Builder login(String login) {
			this.login = login;
			return this;
		}

		public Builder firstName(String firstname) {
			this.firstname = firstname;
			return this;
		}

		public Builder lastName(String lastname) {
			this.lastname = lastname;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public User build() {
			return new User(null, login, firstname, lastname, email);
		}

	}

	private ObjectId _id;
	@JsonProperty private String login;
	@JsonProperty private String firstname;
	@JsonProperty private String lastname;
	@JsonProperty private String email;
	
	private User() {}
	
	private User(Id _id, String login, String firstname, String lastname, String email) {
		super();
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public String getEmail() {
		return email;
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
