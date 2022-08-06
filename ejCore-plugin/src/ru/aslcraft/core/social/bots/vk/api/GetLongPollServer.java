package ru.aslcraft.core.social.bots.vk.api;

import org.json.JSONObject;

import ru.aslcraft.core.Core;
import ru.aslcraft.core.social.bots.vk.wclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetLongPollServer {
    public Map<String, Object> get(){
        wclient client = new wclient();
        List<String> param = new ArrayList<>();
        param.add("group_id,"+Core.getCfg().getString("vk.group-id", "replace here with group id", true));
        JSONObject object = new JSONObject(client.body("groups.getLongPollServer", param, false)).getJSONObject("response");
        HashMap<String, Object> result = new HashMap<>();
        result.put("server", object.getString("server"));
        result.put("key", object.getString("key"));
        result.put("ts", object.getLong("ts"));
        return result;
    }
}
