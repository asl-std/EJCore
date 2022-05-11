package ru.asl.core.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import ru.asl.core.Core;

import com.sun.net.httpserver.HttpContext;

public class Server extends Thread{
	
	public static HttpServer ejServer;
	
	public static Object[] endpoints;
	
	public Server() {
	
		if (Core.getCfg().getBoolean("ej-server-enabled")) {
			start();
		}
	}

	@Override
    public void run() {
		ejServer.createContext("/about", (exchange -> {
            String respText = "It's ejServer!";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
		}));
        ejServer.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
		ejServer.start();
    }
	
	public static void createServer() {
		try {
			ejServer = HttpServer.create(new InetSocketAddress(Core.getCfg().getInt("ej-server-port")), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
