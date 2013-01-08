package com.linagora.openpaas.backend;

import static com.jayway.restassured.RestAssured.expect;

import java.util.List;

import javax.inject.Inject;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.linagora.openpaas.backend.storage.User;

public class UserSupport {
	
	private RequestSpecification spec;
	private int servletContainerPort;
	
	@Inject
	private UserSupport(EmbeddedServletContainer servletContainer) {
		this.servletContainerPort = servletContainer.getPort();
	}

	public void createUser(User user) {
		createNewSpec();
		String path = "/user";
		spec.given().formParam("login", user.getLogin())
					.formParam("firstname", user.getFirstname())
					.formParam("lastname", user.getLastname())
					.formParam("mail", user.getEmail())
					.when()
					.post(path); 
	}

	public int findNumberUsers() {
		createNewSpec();
		String path = "/users";
		Response rp = expect().statusCode(200).when().get(path);
		List<Object> list = rp.getBody().jsonPath().getList("");
		return list.size();
	}
	
	public void assertStatusOk() {
		spec.expect().statusCode(204);
	}

	public void assertDuplicateUser(String login) {
		spec.expect().statusCode(409);
	}
	
	private void createNewSpec() {
		RestAssured.basePath = "/rest/userService";
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = servletContainerPort;
		spec = RestAssured.with();
	}
}
