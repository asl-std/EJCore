package ru.aslcraft.core.social.bots.vk;


import java.lang.reflect.InvocationTargetException;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.aslcraft.core.social.bots.Classes;

public class BotListener extends Classes {
	public void parse(JSONObject jsonObject){
		final JSONArray jArr = jsonObject.getJSONArray("updates");
		for (int x = 0; x < jArr.length(); x++){
			JSONObject jObj = jArr.getJSONObject(x);
			if (jObj.getString("type").equals("message_new")){
				jObj = jObj.getJSONObject("object").getJSONObject("message");
				final long peer_id = jObj.getLong("peer_id");
				final String text = jObj.getString("text");
				if (jObj.has("payload")){
					final Object payload = jObj.get("payload");
					btn2class(peer_id, payload);
				}else {
					msg2class(text, peer_id);
				}
			}else if (jObj.getString("type").equals("message_event")){
				jObj = jObj.getJSONObject("object");
				final long user_id = jObj.getLong("user_id");
				final Object payload = jObj.get("payload");
				btn2class(user_id, payload);
			}
		}
	}


	private void msg2class(String text, long peer_id){
		for (final Object obj : classes) {
			if (obj != null) {
				final Class<?> clazz = obj.getClass();
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
		for (final Object obj : classes) {
			if (obj != null) {
				final Class<?> clazz = obj.getClass();
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
