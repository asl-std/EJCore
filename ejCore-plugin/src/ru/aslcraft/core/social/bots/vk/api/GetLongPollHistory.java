package ru.aslcraft.core.social.bots.vk.api;

import java.util.ArrayList;
import java.util.List;

import ru.aslcraft.core.social.bots.vk.wclient;

public class GetLongPollHistory {
    public String get(String server, long ts, String key){
        wclient client = new wclient();
        List<String> param = new ArrayList<>();
        param.add("ts ,"+ts);
        param.add("key," + key);
        param.add("act,a_check");
        return client.body(server, param, true);
    }
}
