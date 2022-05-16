package ru.asl.api.social.bots.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import ru.asl.core.social.bots.discord.BotMain;

/**
 * <p>BotSendMessage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BotSendMessage {
	
	static JDA jda = BotMain.getJda();
	
	/**
	 * <p>send2chat.</p>
	 *
	 * @param text a {@link java.lang.String} object
	 * @param chatName a {@link java.lang.String} object
	 */
	public static void send2chat(String text,String chatName){
        MessageChannel channel = jda.getTextChannelsByName(chatName, false).get(0);
        channel.sendMessage(text).queue();
    }

    /**
     * <p>send2chatEmbed.</p>
     *
     * @param eb a {@link net.dv8tion.jda.api.EmbedBuilder} object
     * @param chatName a {@link java.lang.String} object
     */
    public static void send2chatEmbed(EmbedBuilder eb,String chatName){
        MessageChannel channel = jda.getTextChannelsByName(chatName, false).get(0);
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    /**
     * <p>send2pm.</p>
     *
     * @param text a {@link java.lang.String} object
     * @param userID a {@link java.lang.String} object
     */
    public static void send2pm(String text, String userID){
        User user = jda.getUserById(userID);
        user.openPrivateChannel().queue(privateChannel -> {
            privateChannel.sendMessage(text).queue();
        });
    }
}
