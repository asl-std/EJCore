package ru.asl.api.social.bots.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import ru.asl.core.social.bots.discord.BotMain;

public class BotSendMessage {
	
	static JDA jda = BotMain.getJda();
	
	public static void send2chat(String text,String chatName){
        MessageChannel channel = jda.getTextChannelsByName(chatName, false).get(0);
        channel.sendMessage(text).queue();
    }

    public static void send2chatEmbed(EmbedBuilder eb,String chatName){
        MessageChannel channel = jda.getTextChannelsByName(chatName, false).get(0);
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    public static void send2pm(String text, String userID){
        User user = jda.getUserById(userID);
        user.openPrivateChannel().queue(privateChannel -> {
            privateChannel.sendMessage(text).queue();
        });
    }
}
