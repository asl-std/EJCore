package ru.asl.core.social.bots.vk.api;

import lombok.SneakyThrows;
import ru.asl.core.social.bots.vk.wclient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class EditMessage {
    @SneakyThrows
    public void send(String text, long id, long messageID){
        wclient client = new wclient();
        List<String> param = new ArrayList<>();
        param.add("peer_id,"+id);
        param.add("messsage_id,"+ (messageID - 1));
        param.add("message,"+ URLEncoder.encode(text, "UTF-8"));
        client.body("messages.edit", param, false);
    }
}
