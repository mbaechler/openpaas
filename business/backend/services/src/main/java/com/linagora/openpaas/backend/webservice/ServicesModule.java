package com.linagora.openpaas.backend.webservice;

import java.net.UnknownHostException;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.linagora.openpaas.backend.storage.MongoUserDAO;
import com.linagora.openpaas.backend.storage.UserDAO;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class ServicesModule extends JerseyServletModule {
	@Override
	protected void configureServlets() {
		// Must configure at least one JAX-RS resource or the
		// server will fail to start.
		bind(UserService.class);
		bind(UserDAO.class).to(MongoUserDAO.class);
		bind(DB.class).toInstance(dbProvider());
		
		// Route all requests through GuiceContainer
		serve("/rest/*").with(GuiceContainer.class, ImmutableMap.of(JSONConfiguration.FEATURE_POJO_MAPPING, "true"));
	}

	private DB dbProvider()  {
		try {
			Mongo mongo = new Mongo();
			DB db = mongo.getDB("test");
			return db;
		} catch (UnknownHostException e) {
			Throwables.propagate(e);
		}
		return null;
	}
}