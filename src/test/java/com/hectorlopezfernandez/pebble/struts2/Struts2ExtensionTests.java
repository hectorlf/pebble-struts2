package com.hectorlopezfernandez.pebble.struts2;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Struts2ExtensionTests {

	private static Server server;
	private static int port;
	private static HttpClient httpClient;

	@BeforeClass
	public static void setup() {
		Server server = new Server(0);
		// 'test' context
		ServletContextHandler testContext = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		testContext.setContextPath("/test");
		testContext.addFilter(StrutsPrepareAndExecuteFilter.class, "/*", EnumSet.of(DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.REQUEST));
		testContext.addServlet(CustomPebbleServlet.class, "*.pebble");
		// 'root' context
		ServletContextHandler rootContext = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		rootContext.setContextPath("/");
		rootContext.addFilter(StrutsPrepareAndExecuteFilter.class, "/*", EnumSet.of(DispatcherType.FORWARD,DispatcherType.INCLUDE,DispatcherType.REQUEST));
		rootContext.addServlet(CustomPebbleServlet.class, "*.pebble");
		// handle context collection
		ContextHandlerCollection chc = new ContextHandlerCollection();
		chc.addHandler(testContext);
		chc.addHandler(rootContext);
		server.setHandler(chc);
		try {
			server.start();
		} catch (Exception e) {
			throw new RuntimeException("Unable to start jetty: " + e.getMessage());
		}
		port = ((ServerConnector)server.getConnectors()[0]).getLocalPort();
		httpClient = new HttpClient();
		try {
			httpClient.start();
		} catch (Exception e) {
			throw new RuntimeException("Unable to start jetty client: " + e.getMessage());
		}
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			httpClient.stop();
		} catch (Exception e1) {
			// life is hard
		}
		try {
			server.stop();
		} catch (Exception e) {
			// life is hard
		}
	}

	@Test
	public void testHelloWorld() throws Exception {
		ContentResponse response = httpClient.newRequest("localhost", port).method(HttpMethod.GET).path("/hello.action").send();
		Assert.assertEquals(200, response.getStatus());
		System.out.println(response.getStatus() + " - " + response.getContentAsString());
	}

}