package org.aslstd.bots.core.vk.api;

import com.google.gson.Gson;

public class Button extends ButtonColor {
    ButtonAction action = new ButtonAction();
    public Button(String type, String payload, String label, String color){
        this.color = color;
        action.setType(type);
        action.setPayload(payload);
        action.setLabel(label);
    }

    public String get(){
        String json = new Gson().toJson(this);
        return json;
    }
}
