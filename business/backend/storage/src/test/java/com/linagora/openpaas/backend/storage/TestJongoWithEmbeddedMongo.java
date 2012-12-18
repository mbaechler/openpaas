package com.linagora.openpaas.backend.storage;

import static org.fest.assertions.api.Assertions.assertThat;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.JacksonMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.MapperFeature;

public class TestJongoWithEmbeddedMongo {

	private MongoTestServer mongoTestServer;
    
    @Before
    public void setUp() throws Exception {
        mongoTestServer = new MongoTestServer();
        mongoTestServer.start();
    }

    @After
    public void tearDown() throws Exception {
    	mongoTestServer.stop();
    }

    @Test
	public void testCreateUser() throws UnknownHostException {
		Jongo jongo = new Jongo(mongoTestServer.client().getDB("matthieu"), 
				new JacksonMapper.Builder()
					.enable(MapperFeature.USE_ANNOTATIONS)
					.build());
		MongoCollection users = jongo.getCollection("users");
		User user = User.builder().login("matthieu").build();
		users.save(user);
		Iterable<User> list = users.find().as(User.class);
		assertThat(list).containsOnly(user);
	}
	
}
