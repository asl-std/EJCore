package ru.asl.api.social.bots.discord;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * <p>MessageReceiver interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface MessageReceiver {
	/**
	 * <p>onMessageReceived.</p>
	 *
	 * @param channel a {@link net.dv8tion.jda.api.entities.MessageChannel} object
	 * @param messageText a {@link java.lang.String} object
	 * @param messageAuthor a {@link java.lang.String} object
	 * @param authorID a {@link java.lang.String} object
	 * @param messageID a {@link java.lang.String} object
	 */
	void onMessageReceived(MessageChannel channel,String messageText,String messageAuthor,String authorID,String messageID);

	/**
	 *
	 * @param event {@link ButtonInteractionEvent} object
	 */
	void onButtonClick(ButtonInteractionEvent event);
}
