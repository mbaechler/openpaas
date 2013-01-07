package com.linagora.openpaas.backend.webservice;


import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceServletContextListener extends com.google.inject.servlet.GuiceServletContextListener {

	public GuiceServletContextListener() {
		super();
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServicesModule());
	}
}