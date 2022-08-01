package ru.asl.api.social.bots;

import net.dv8tion.jda.api.JDA;
import ru.asl.core.social.bots.Classes;
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
	 * {@code ReceiverRegistrator.init(new BotListener());}
	 * @param clazz a {@link java.lang.Object} object
	 */
	public static void init(Object clazz) {
		Classes.classes.add(clazz);
	}

	/**
	 * Получение экземпляра {@link JDA} для работы с ботом дискорд вне ejCore
	 * @return {@link JDA}
	 */
	public static JDA getJDA(){
		return BotMain.getJda();
	}
}
