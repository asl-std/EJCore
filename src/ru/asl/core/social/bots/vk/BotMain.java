package ru.asl.core.social.bots.vk;

import lombok.SneakyThrows;
import org.json.JSONObject;
import ru.asl.core.social.bots.vk.api.GetLongPollHistory;
import ru.asl.core.social.bots.vk.api.GetLongPollServer;

import java.util.Map;

public class BotMain implements Runnable{
    @SneakyThrows
    @Override
    public void run() {
        Map<String, Object> server = new GetLongPollServer().get();
        while (true) {
            JSONObject jsonObject = new JSONObject(new GetLongPollHistory().get((String) server.get("server"), (Long) server.get("ts"), (String) server.get("key")));
            server.put("ts", Long.parseLong(jsonObject.getString("ts")));
            new BotListener().parse(jsonObject);
        }

    }

}
