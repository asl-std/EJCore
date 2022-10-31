package org.aslstd.bots.core.vk.api;

import java.util.ArrayList;
import java.util.List;

import org.aslstd.bots.core.vk.wclient;

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
