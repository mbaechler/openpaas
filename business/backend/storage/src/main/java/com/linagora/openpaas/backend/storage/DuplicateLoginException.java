package com.linagora.openpaas.backend.storage;

import com.google.common.base.Objects;

public class DuplicateLoginException extends RuntimeException {

	private final String login;

	public DuplicateLoginException(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("login", login)
			.toString();
	}
	
}
