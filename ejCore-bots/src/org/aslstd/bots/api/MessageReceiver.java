package org.aslstd.bots.api;

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
	 * <p>onMessageReceivedDiscord.</p>
	 *
	 * @param channel a {@link net.dv8tion.jda.api.entities.MessageChannel} object
	 * @param messageText a {@link java.lang.String} object
	 * @param messageAuthor a {@link java.lang.String} object
	 * @param authorID a {@link java.lang.String} object
	 * @param messageID a {@link java.lang.String} object
	 */
	void onMessageReceivedDiscord(MessageChannel channel,String messageText,String messageAuthor,String authorID,String messageID);

	/**
	 *
	 * @param event {@link ButtonInteractionEvent} object
	 */
	void onButtonClickDiscord(ButtonInteractionEvent event);

	/**
	 * <p>onMessageReceivedVK.</p>
	 *
	 * @param text a {@link java.lang.String} object
	 * @param user_id a {@link java.lang.Long} object
	 */
	void onMessageReceivedVK(String text, long user_id);

	/**
	 * @param user_id a {@link java.lang.Long} object
	 * @param payload a {@link java.lang.Object} object
	 */
	void onButtonClickVK(long user_id, Object payload);
}
