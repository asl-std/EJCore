package ru.asl.api.webserver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;

import ru.asl.core.webserver.Server;

/**
 * <p>ServerAPI class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ServerAPI {
	/**
	 * <p>decode.</p>
	 *
	 * @param encoded a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public static String decode(final String encoded) {
		try {
			return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 is a required encoding", e);
		}
	}
	
	/**
	 * <p>splitQuery.</p>
	 *
	 * @param query a {@link java.lang.String} object
	 * @return a {@link java.util.Map} object
	 * @throws java.io.UnsupportedEncodingException if any.
	 */
	public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

	/**
	 * <p>registerEndPoint.</p>
	 *
	 * @param endPoint a {@link java.lang.String} object
	 * @param handler a {@link com.sun.net.httpserver.HttpHandler} object
	 */
	public static void registerEndPoint(String endPoint, HttpHandler handler) {
		Server.getEJServer().createContext(endPoint,handler);
	}
}
