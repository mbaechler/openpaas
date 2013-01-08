package com.linagora.openpaas.backend;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.DefaultServlet;

import com.google.common.base.Throwables;
import com.google.inject.servlet.GuiceFilter;
import com.linagora.openpaas.backend.storage.MongoTestServer;

public class EmbeddedServletContainer {

	private Server server;
	private SelectChannelConnector selectChannelConnector;
	private CountDownLatch serverStartedLatch;
	private final MongoTestServer mongoTestServer;
	
	@Inject
	private EmbeddedServletContainer(MongoTestServer mongoTestServer) throws Exception {
        this.mongoTestServer = mongoTestServer;
        mongoTestServer.start();
        
		serverStartedLatch = new CountDownLatch(1);
		server = new Server();
        Context root = new Context(server, "/", Context.SESSIONS);
        selectChannelConnector = new SelectChannelConnector();
        server.setConnectors(new Connector[] {selectChannelConnector});
        root.addFilter(GuiceFilter.class, "/*", 0);
        root.addServlet(DefaultServlet.class, "/");
        root.addEventListener(new ServletContextListener() {
			
			@Override
			public void contextInitialized(ServletContextEvent sce) {
				serverStartedLatch.countDown();
			}
			
			@Override
			public void contextDestroyed(ServletContextEvent sce) {
			}
		});
        server.start();
	}

	 public int getPort() {
         try {
                 if (serverStartedLatch.await(1, TimeUnit.MINUTES)) {
                         return selectChannelConnector.getLocalPort();
                 }
         } catch (InterruptedException e) {
                 Throwables.propagate(e);
         }
         throw new IllegalStateException("Could not get server's listening port. Illegal concurrent state.");
	 }

	public void shutdown() throws Exception {
		mongoTestServer.stop();
		server.stop();
	}
}
