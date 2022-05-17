package ru.asl.core.social.bots.discord;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import ru.asl.api.bukkit.message.EText;

/**
 * <p>BotListener class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BotListener extends ListenerAdapter{
	
	/** Constant <code>classes</code> */
	public static ArrayList<Object> classes = new ArrayList<>();
	
	/** {@inheritDoc} */
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            MessageChannel channel = event.getChannel();
            String messageText = event.getMessage().getContentRaw();
            String messageAuthor = event.getAuthor().getName();
            String authorID = event.getAuthor().getId();
            String messageID = event.getMessageId();
            msg2class(channel,messageText,messageAuthor,authorID,messageID);
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        btn2class(event);
        event.reply(".").queue();
    }



    private void msg2class(MessageChannel channel, String messageText, String messageAuthor, String authorID, String messageID){
        for (Object obj : classes) {
            if (obj != null) {
                Class clazz = obj.getClass();
                try {
                    clazz.getMethod("onMessageReceived", MessageChannel.class,String.class,String.class,String.class,String.class)
                    	.invoke(obj, channel,messageText,messageAuthor,authorID,messageID);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void btn2class(ButtonInteractionEvent event){
        for (Object obj : classes) {
            if (obj != null) {
                Class clazz = obj.getClass();
                try {
                    clazz.getMethod("onButtonClick", ButtonInteractionEvent.class)
                            .invoke(obj, event);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
