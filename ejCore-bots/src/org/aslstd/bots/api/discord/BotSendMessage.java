package org.aslstd.bots.api.discord;

import java.util.Arrays;
import java.util.List;

import org.aslstd.bots.core.discord.DSBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * <p>BotSendMessage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BotSendMessage {

	static JDA jda = DSBot.getJda();

	/**
	 * <p>send2chat.</p>
	 *
	 * @param text a {@link java.lang.String} object, текст отправляемый ботом
	 * @param chatName a {@link java.lang.String} object, название чата на сервере, куда отправляется сообщение
	 */
	public static void send2chat(String text,String chatName){
		final MessageChannel channel = jda.getTextChannelsByName(chatName, false).get(0);
		channel.sendMessage(text).queue();
	}

	/**
	 * <p>send2chat.</p>
	 *
	 * @param text a {@link java.lang.String} object, текст отправляемый ботом
	 * @param chatName a {@link java.lang.String} object, название чата на сервере, куда отправляется сообщение
	 * @param buttons a {@link net.dv8tion.jda.api.interactions.components.ActionRow} object, список кнопок
	 */
	public static void send2chat(String text,String chatName, List<ActionRow> buttons){
		final MessageChannel channel = jda.getTextChannelsByName(chatName, false).get(0);
		channel.sendMessage(text).setActionRows(buttons).queue();
	}

	/**
	 * <p>send2chatEmbed.</p>
	 *
	 * @param eb a {@link net.dv8tion.jda.api.EmbedBuilder} object, embed сообщение
	 * @param chatName a {@link java.lang.String} object, название чата на сервере, куда отправляется сообщение
	 */
	public static void send2chatEmbed(EmbedBuilder eb,String chatName){
		final MessageChannel channel = jda.getTextChannelsByName(chatName, false).get(0);
		channel.sendMessageEmbeds(eb.build()).queue();
	}

	/**
	 * <p>send2pm.</p>
	 *
	 * @param text a {@link java.lang.String} object, текст сообщения
	 * @param userID a {@link java.lang.String} object, id пользователя
	 */
	public static void send2pm(String text, String userID){
		final User user = jda.getUserById(userID);
		user.openPrivateChannel().queue(privateChannel -> {
			privateChannel.sendMessage(text).queue();
		});
	}

	/**
	 * <p>send2pm.</p>
	 * <code>
	 *     ActionRow(
	 *               Button.primary("hello", "Click Me"),
	 *               Button.success("info", "INFO")
	 *               )
	 * </code>
	 * @param text a {@link java.lang.String} object, текст сообщения
	 * @param userID a {@link java.lang.String} object, id пользователя
	 * @param buttons a {@link net.dv8tion.jda.api.interactions.components.ActionRow} object, список кнопок
	 */
	public static void send2pm(String text, String userID, List<ActionRow> buttons){
		final User user = jda.getUserById(userID);
		user.openPrivateChannel().queue(privateChannel -> {
			privateChannel.sendMessage(text).setActionRows(buttons).queue();
		});
	}

	/**
	 * <p>rowBuilder</p>
	 * Метод принимает на вход массив из кнопок (не более пяти штук) и возвращает объект представляющий линию с кнопками.
	 * @param buttons {@link Button} array
	 * @return {@link ActionRow}
	 */
	public static ActionRow rowBuilder(Button[] buttons){
		final List<ItemComponent> components = Arrays.asList(buttons);
		return ActionRow.of(components);
	}

	/**
	 * <p>actionRowsBuilder</p>
	 * Метод предназначен для создания кнопок в несколько рядов.
	 * На вход принимает массив кнопок и лист в котором задается количество кнопок для каждого ряда.<br><br>
	 * <code>
	 *     List{@literal <Integer>} buttonsPerLine = new ArrayList{@literal <>}();<br>
	 *     buttonsPerLine.add(2); //в первой линии будут первые две кнопки из массива кнопок.<br>
	 *     buttonsPerLine.add(4); //во второй линии будут третья, четвертая и пятая кнопки из массива кнопок.<br>
	 *     buttonsPerLine.add(1); //в третьей линии будет шестая кнопка из массива кнопок.<br>
	 * </code>
	 * @param buttons {@link Button} array
	 * @param format {@link List} of {@link Integer}
	 * @return {@link List} of {@link ActionRow}
	 */
	public static List<ActionRow> actionRowsBuilder(Button[] buttons, List<Integer> format){
		//HashMap<Integer, ActionRow> rowHashMap = new HashMap<>();
		final ActionRow[] actionRows = new ActionRow[format.size()];
		int j = 0;
		for (int x = 0; x < format.size(); x++){
			final int butCountPerRow = format.get(x);
			final Button[] tmpBut = new Button[butCountPerRow];
			for (int y = 0; y< butCountPerRow; y++){
				tmpBut[y] = buttons[j+y];
			}
			j += butCountPerRow;
			final List<ItemComponent> itemComponents = Arrays.asList(tmpBut);
			actionRows[x] = ActionRow.of(itemComponents);
		}
		return Arrays.asList(actionRows);
	}
}
