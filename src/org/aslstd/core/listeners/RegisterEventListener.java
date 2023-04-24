package org.aslstd.core.listeners;

import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.core.managers.ListenerManager;
import org.bukkit.event.Listener;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс регистрации EventListener для плагинов
 *
 * @author ZooMMaX
 * @version 1.2.17
 */
@Deprecated(since = "1.2.19", forRemoval = true)
public class RegisterEventListener {
	@Getter @Setter private static ListenerManager listenerManager = null;

	/**
	 * <p>init.</p>
	 *
	 * @param ejPlugin a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 */
	public static void init(EJPlugin ejPlugin) {
		setListenerManager(new ListenerManager(ejPlugin));
	}

	/**
	 * Метод регистрации.
	 *
	 * @param pluginName - название Вашего плагина
	 * @param listener - EventListener для регистрации.
	 * {@code RegisterEventListener.register("my_plugin_name", new EventListener());}
	 */
	public static void register(String pluginName,Listener listener) {
		getListenerManager().addListener(pluginName, listener);
	}

	/**
	 * <p>register.</p>
	 *
	 * @param pluginName a {@link java.lang.String} object
	 * @param listener a {@link org.bukkit.event.Listener} object
	 * @param condition a boolean
	 */
	public static void register(String pluginName,Listener listener,boolean condition) {
		getListenerManager().addListener(pluginName, listener, condition);
	}

	public static void unregister(String pluginName) {
		getListenerManager().removeListener(pluginName);
	}

}
