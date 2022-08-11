package ru.aslcraft.bots.core.vk.api;

import org.json.JSONArray;
import org.json.JSONObject;

public class MakeRow {
    public String get(String button){
        JSONObject jsonObject = new JSONObject(button);
        JSONArray jsonArray = new JSONArray().put(jsonObject);
        return jsonArray.toString();
    }

    public String get(String button, String row){
        JSONObject jsonObject = new JSONObject(button);
        JSONArray jsonArray = new JSONArray(row);
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }
}
