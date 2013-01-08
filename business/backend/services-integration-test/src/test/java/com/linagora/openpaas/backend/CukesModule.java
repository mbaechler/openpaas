package com.linagora.openpaas.backend;

import java.io.IOException;

import com.google.common.base.Throwables;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.linagora.openpaas.backend.storage.MongoTestServer;
import com.linagora.openpaas.backend.webservice.ServicesModule;
import com.mongodb.DB;

public class CukesModule extends AbstractModule {

	@Override
	protected void configure() {
		install(
				Modules.override(new ServicesModule()).with(new OverrideModule()));
	}
	
	private static class OverrideModule extends AbstractModule {
		@Override
		protected void configure() {
			try {
				MongoTestServer mongoTestServer = new MongoTestServer();
				bind(MongoTestServer.class).toInstance(mongoTestServer);
				bind(DB.class).toInstance(mongoTestServer.client().getDB("integration-test"));
			} catch (IOException e) {
				Throwables.propagate(e);
			}
		}
	}
	
}
