package ru.aslcraft.core.social.bots.vk.api;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

public class MakeKeyboard {

    JSONArray buttons;

    public MakeKeyboard(String row){
        JSONArray jsonArray = new JSONArray(row);
        this.buttons = new JSONArray().put(jsonArray);
    }

    public MakeKeyboard(String row, JSONArray keyboard){
        JSONArray jsonArray = new JSONArray(row);
        keyboard.put(jsonArray);
        this.buttons = keyboard;
    }

    public MakeKeyboard(JSONArray keyboard){
        this.buttons = keyboard;
    }

    public String get(boolean one_time, boolean inline){
        return new JSONObject().put("one_time", one_time).put("buttons", buttons).put("inline", inline).toString();
    }

    public JSONArray getButtons(){
        return buttons;
    }
}
