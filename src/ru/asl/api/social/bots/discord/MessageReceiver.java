package ru.asl.api.social.bots.discord;

import net.dv8tion.jda.api.entities.MessageChannel;

public interface MessageReceiver {
	void onMessageReceived(MessageChannel channel,String messageText,String messageAuthor,String authorID,String messageID);
}
