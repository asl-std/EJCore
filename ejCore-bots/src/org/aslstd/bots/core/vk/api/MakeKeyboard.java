package org.aslstd.bots.core.vk.api;

import org.json.JSONArray;
import org.json.JSONObject;

public class MakeKeyboard {

	JSONArray buttons;

	public MakeKeyboard(String row){
		final JSONArray jsonArray = new JSONArray(row);
		buttons = new JSONArray().put(jsonArray);
	}

	public MakeKeyboard(String row, JSONArray keyboard){
		final JSONArray jsonArray = new JSONArray(row);
		keyboard.put(jsonArray);
		buttons = keyboard;
	}

	public MakeKeyboard(JSONArray keyboard){
		buttons = keyboard;
	}

	public String get(boolean one_time, boolean inline){
		return new JSONObject().put("one_time", one_time).put("buttons", buttons).put("inline", inline).toString();
	}

	public JSONArray getButtons(){
		return buttons;
	}
}
