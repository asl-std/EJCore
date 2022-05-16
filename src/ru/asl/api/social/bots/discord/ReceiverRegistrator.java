package ru.asl.api.social.bots.discord;

import ru.asl.core.social.bots.discord.BotListener;

/**
 * <p>ReceiverRegistrator class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ReceiverRegistrator {
	
	/**
	 * <p>init.</p>
	 *
	 * @param clazz a {@link java.lang.Object} object
	 */
	public void init(Object clazz) {
		BotListener.classes.add(clazz);
	}
}
