package ru.aslcraft.bots.core.vk.api;

import org.json.JSONObject;

import ru.aslcraft.api.bukkit.yaml.EJConf;
import ru.aslcraft.bots.core.vk.wclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetLongPollServer {
    private static EJConf ejConf;
    public Map<String, Object> get(){
        wclient client = new wclient();
        List<String> param = new ArrayList<>();
        param.add("group_id,"+ejConf.getString("vk.group-id", "replace here with group id", true));
        JSONObject object = new JSONObject(client.body("groups.getLongPollServer", param, false)).getJSONObject("response");
        HashMap<String, Object> result = new HashMap<>();
        result.put("server", object.getString("server"));
        result.put("key", object.getString("key"));
        result.put("ts", object.getLong("ts"));
        return result;
    }
}
