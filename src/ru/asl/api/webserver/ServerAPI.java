package ru.asl.api.webserver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpHandler;

import ru.asl.api.bukkit.message.EText;
import ru.asl.core.webserver.Server;

public class ServerAPI {
	public static String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is a required encoding", e);
        }
    }
    
    public static void registerEndPoint(String endPoint,HttpHandler handler) {
		Server.ejServer.createContext(endPoint,handler);
	}
}
