package ru.asl.api.social.bots.vk;

import ru.asl.core.social.bots.vk.api.SendMessage;

public class BotSendMessage {
    public void send(String text, String keyboard, long user_id){
        new SendMessage().send(text, user_id, keyboard);
    }
}
