package com.linagora.openpaas.backend.webservice;


import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.linagora.openpaas.backend.storage.User;
import com.linagora.openpaas.backend.storage.UserDAO;

@Path("/userService")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	
	private UserDAO userDAO;
	
	@Inject
	private UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
    @POST
    @Path("/user")
    public void createUser(@FormParam(value="login") String login, @FormParam(value="firstname")String firstname,
    		@FormParam(value="lastname") String lastname, @FormParam(value="mail") String mail) {
    	
    	userDAO.create(User.builder()
    			.login(login).firstName(firstname)
    			.lastName(lastname).email(mail)
    			.build());
    }

    @GET
    @Path("/users")
    public Iterable<User> findAll() {
        return userDAO.findAll();
    }
    
    @GET
    @Path("/user/{userid}")
    public User findUser(@PathParam("userid") String id) {
    	User u = userDAO.getUser(new User.Id(id));
        if (u == null) {
        	throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity("user not found").build());
        }
        return u;
    }
    
}
