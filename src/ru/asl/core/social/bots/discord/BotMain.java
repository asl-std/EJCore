package ru.asl.core.social.bots.discord;

import javax.security.auth.login.LoginException;

import lombok.Getter;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import ru.asl.core.Core;
/**
 * <p>BotMain class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BotMain implements Runnable{
	
	@Getter private static JDA jda;
	
	
	/**
	 * <p>Constructor for BotMain.</p>
	 */
	public BotMain() {
		
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		try {
            jda = JDABuilder.create(Core.getCfg().getString("discord.bot-token", "replace here with token", true),
            		GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES)
					.setChunkingFilter(ChunkingFilter.ALL) // enable member chunking for all guilds
					.setMemberCachePolicy(MemberCachePolicy.ALL)
                    //Устанавливаем метод прослушки сообщений
                    .addEventListeners(new BotListener())
                    //Задаем подпись боту (в левом меню)
                    .setActivity(Activity.watching("EJDiscordBot"))
                    .build();
            jda.awaitReady();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
	}

}
