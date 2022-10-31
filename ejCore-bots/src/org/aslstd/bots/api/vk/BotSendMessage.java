package org.aslstd.bots.api.vk;

import org.aslstd.bots.core.vk.api.SendMessage;

public class BotSendMessage {
    public void send(String text, String keyboard, long user_id){
        new SendMessage().send(text, user_id, keyboard);
    }
}
