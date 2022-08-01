package ru.asl.core.social.bots.vk;


import org.json.JSONArray;
import org.json.JSONObject;
import ru.asl.api.bukkit.message.EText;
import ru.asl.core.social.bots.Classes;
import ru.asl.core.social.bots.vk.api.EditMessage;

import java.lang.reflect.InvocationTargetException;

public class BotListener extends Classes {
    public void parse(JSONObject jsonObject){
        JSONArray jArr = jsonObject.getJSONArray("updates");
        for (int x = 0; x < jArr.length(); x++){
            JSONObject jObj = jArr.getJSONObject(x);
            if (jObj.getString("type").equals("message_new")){
                jObj = jObj.getJSONObject("object").getJSONObject("message");
                long peer_id = jObj.getLong("peer_id");
                String text = jObj.getString("text");
                if (jObj.has("payload")){
                    Object payload = jObj.get("payload");
                    btn2class(peer_id, payload);
                }else {
                    msg2class(text, peer_id);
                }
            }else if (jObj.getString("type").equals("message_event")){
                jObj = jObj.getJSONObject("object");
                long user_id = jObj.getLong("user_id");
                Object payload = jObj.get("payload");
                btn2class(user_id, payload);
            }
        }
    }


    private void msg2class(String text, long peer_id){
        for (Object obj : classes) {
            if (obj != null) {
                Class clazz = obj.getClass();
                try {
                    clazz.getMethod("onMessageReceivedVK",String.class,long.class)
                            .invoke(obj,text,peer_id);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void btn2class(long user_id, Object payload){
        for (Object obj : classes) {
            if (obj != null) {
                Class clazz = obj.getClass();
                try {
                    clazz.getMethod("onButtonClickVK", long.class, Object.class)
                            .invoke(obj, user_id, payload);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
