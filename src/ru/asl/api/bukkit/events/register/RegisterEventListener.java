package ru.asl.api.bukkit.events.register;

import org.bukkit.event.Listener;

import lombok.Getter;
import lombok.Setter;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.core.managers.ListenerManager;

/**
 * Класс регистрации EventListener для плагинов
 * @author ZooMMaX
 * @version 1.2.17
 */

public class RegisterEventListener {
	@Getter
	@Setter
	private static ListenerManager listenerManager = null;
	
	public static void init(EJPlugin ejPlugin) {
		setListenerManager(new ListenerManager(ejPlugin));
	}
	
	/**
	 * Метод регистрации.
	 * @param pluginName - название Вашего плагина
	 * @param listener - EventListener для регистрации.
	 * {@code RegisterEventListener.register("my_plugin_name", new EventListener());}
	 * */
	public static void register(String pluginName,Listener listener) {
		getListenerManager().addListener(pluginName, listener);
	}
	
	public static void register (String pluginName,Listener listener,boolean condition) {
		getListenerManager().addListener(pluginName, listener, condition);
	}
}
