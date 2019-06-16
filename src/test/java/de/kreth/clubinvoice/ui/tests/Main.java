package de.kreth.clubinvoice.ui.tests;

import java.util.Optional;

import javax.servlet.ServletException;

import de.kreth.clubinvoice.InvoiceMainUI.InvoiceUIServlet;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public class Main {

	private static final String CONTEXT_PATH = "/";

	private static Optional<Undertow> instance = Optional.empty();

	public static void main(String[] args) throws ServletException {
		main();
	}

	public static void main() throws ServletException {
		DeploymentInfo servletBuilder = Servlets.deployment()
				.setClassLoader(Main.class.getClassLoader())
				.setContextPath(CONTEXT_PATH)
				.setDeploymentName("ROOT.war")
				.setDefaultEncoding("UTF-8")
				.addServlets(
						Servlets.servlet(
								InvoiceUIServlet.class.getSimpleName(),
								InvoiceUIServlet.class).addMapping("/*"));

		DeploymentManager manager = Servlets
				.defaultContainer()
				.addDeployment(servletBuilder);

		manager.deploy();

		PathHandler path = Handlers.path(Handlers.redirect(CONTEXT_PATH))
				.addPrefixPath(CONTEXT_PATH, manager.start());

		Undertow undertowServer = Undertow.builder()
				.addHttpListener(8080, "0.0.0.0")
				.setHandler(path)
				.build();

		instance = Optional.of(undertowServer);
		undertowServer.start();
	}

	public static void shutdown() {
		instance.ifPresent(undertow -> undertow.stop());
	}

}
