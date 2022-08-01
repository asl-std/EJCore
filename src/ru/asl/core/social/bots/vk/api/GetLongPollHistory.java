package ru.asl.core.social.bots.vk.api;

import ru.asl.core.social.bots.vk.wclient;

import java.util.ArrayList;
import java.util.List;

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
