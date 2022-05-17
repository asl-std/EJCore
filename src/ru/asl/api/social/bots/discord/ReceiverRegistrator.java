package ru.asl.api.social.bots.discord;

import jdk.nashorn.internal.objects.annotations.Getter;
import net.dv8tion.jda.api.JDA;
import ru.asl.core.social.bots.discord.BotListener;
import ru.asl.core.social.bots.discord.BotMain;

/**
 * <p>ReceiverRegistrator class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ReceiverRegistrator {
	
	/**
	 * <p>init.</p>
	 * Тут происходит регистрация для слушателя бота дискорд.<br><br>
	 * {@code new ReceiverRegistrator().init(BotListener.class);}
	 * @param clazz a {@link java.lang.Object} object
	 */
	public static void init(Object clazz) {
		BotListener.classes.add(clazz);
	}

	public static JDA getJDA(){
		return BotMain.getJda();
	}
}
