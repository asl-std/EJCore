package ru.aslcraft.bots.core.vk.api;

import lombok.SneakyThrows;
import ru.aslcraft.bots.core.vk.wclient;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SendMessage {
    @SneakyThrows
    public void send(String text, long id, String keyboard){
        wclient client = new wclient();
        List<String> param = new ArrayList<>();
        param.add("peer_id,"+id);
        param.add("random_id,"+ new Random().nextInt());
        param.add("message,"+ URLEncoder.encode(text, "UTF-8"));
        if (!keyboard.equalsIgnoreCase("")) {
            param.add("keyboard," + URLEncoder.encode(keyboard, "UTF-8"));
        }
        client.body("messages.send", param, false);
    }
}
