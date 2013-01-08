package com.linagora.openpaas.backend;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import com.linagora.openpaas.backend.storage.User;

import cucumber.api.java.After;
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

	@After
	public void teardown() throws Exception {
		servletContainer.shutdown();
	}
    
	@Given("^the following users exist$")
	public void the_following_users_exist(List<User> users) throws Throwable {
		for (User user: users) {
			userSupport.createUser(user);
		}
		initial = userSupport.findNumberUsers();
	}
	
    @When("^I create a user with login \"(.+)\" and email \"(.+)\"$")
    public void i_create_a_user(String login, String email) throws Throwable {
        userSupport.createUser(User.builder().login(login).email(email).build());
    }

    @When("^I create a user with login and email \"(.+)\"$")
    public void i_create_a_user(String login) throws Throwable {
        userSupport.createUser(User.builder().login(login).email(login).build());
    }
    
    @Then("^there is one more user in the system$")
    public void oneMoreMessage() throws Throwable {
        int actualnumber = userSupport.findNumberUsers();
        assertThat(actualnumber).isEqualTo(initial + 1);
    }
 
    @Then("^there is an error saying \"(.+)\" login is already used$")
    public void there_is_an_error_saying_login_is_already_used(String login) throws Throwable {
    	userSupport.assertDuplicateUser(login);
    }

    @Then("^there is no new user in the system$")
    public void there_is_no_new_user_in_the_system() throws Throwable {
    	int actualnumber = userSupport.findNumberUsers();
        assertThat(actualnumber).isEqualTo(initial);
    }
    
}
    

