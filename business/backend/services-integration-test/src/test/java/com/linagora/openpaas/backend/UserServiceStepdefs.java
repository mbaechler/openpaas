package com.linagora.openpaas.backend;

import static org.fest.assertions.api.Assertions.assertThat;

import javax.inject.Inject;

import com.jayway.restassured.RestAssured;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserServiceStepdefs {

	private final EmbeddedServletContainer servletContainer;
    private final UserSupport userSupport;
    private int initial;

    @Inject
    public UserServiceStepdefs(UserSupport userSupport, EmbeddedServletContainer servletContainer) {
		this.userSupport = userSupport;
		this.servletContainer = servletContainer;
    }

	@Before
	public void setup() throws Exception {
        RestAssured.baseURI = "http://localhost";
		RestAssured.port = servletContainer.getPort();
		RestAssured.basePath = "/rest/userService";
	}
	
	@After
	public void teardown() throws Exception {
		servletContainer.shutdown();
	}
    
    @Given("^we have a number of user in the system$")
    public void user() throws Throwable {
    	initial = userSupport.findNumberUsers();
    }

    @When("^we create one user which does not exist$")
    public void createOneUser() throws Throwable {
        userSupport.createUser();
    }

    @Then("^there is one more user in the system$")
    public void oneMoreMessage() throws Throwable {
        int actualnumber = userSupport.findNumberUsers();
        assertThat(actualnumber).isEqualTo(initial +1);
    }
    
}
    

