package ru.aslcraft.webserver.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import lombok.Getter;

/**
 * <p>Server class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("restriction")
public class Server extends Thread {

	@Getter private static HttpServer EJServer;


	/**
	 * <p>Constructor for Server.</p>
	 */
	public Server() {}

	/** {@inheritDoc} */
	@Override
	public void run() {
		EJServer.createContext("/about", (exchange -> {
			final String respText = "It's ejServer!";
			exchange.sendResponseHeaders( 200, respText.getBytes().length );
			final OutputStream output = exchange.getResponseBody();
			output.write( respText.getBytes() );
			output.flush();
			exchange.close();
		}));

		EJServer.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
		EJServer.start();
	}

	/**
	 * <p>createServer.</p>
	 *
	 * @return a boolean
	 */
	public static boolean createServer() {
		if (EWS.getCfg().getBoolean("ej-server.server-enabled", false, true))
			try {
				EJServer = HttpServer.create(
						new InetSocketAddress(EWS.getCfg().getInt("ej-server.server-port", 8080, true)), 0
						);
				return true;
			} catch (final IOException e) { e.printStackTrace(); }
		return false;
	}

}
