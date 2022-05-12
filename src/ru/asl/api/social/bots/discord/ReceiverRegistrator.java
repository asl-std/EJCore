package ru.asl.api.social.bots.discord;

import ru.asl.core.social.bots.discord.BotListener;

public class ReceiverRegistrator {
	
	public void init(Object clazz) {
		BotListener.classes.add(clazz);
	}
}
