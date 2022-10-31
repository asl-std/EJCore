package org.aslstd.bots.core.vk;

import java.util.Map;

import org.aslstd.bots.core.vk.api.GetLongPollHistory;
import org.aslstd.bots.core.vk.api.GetLongPollServer;
import org.json.JSONObject;

import lombok.SneakyThrows;

public class VKBot implements Runnable {
	@SneakyThrows
	@Override
	public void run() {
		Map<String, Object> server = new GetLongPollServer().get();
		while (true) {
			JSONObject jsonObject = new JSONObject(new GetLongPollHistory().get((String) server.get("server"), (Long) server.get("ts"), (String) server.get("key")));
			server.put("ts", Long.parseLong(jsonObject.getString("ts")));
			new BotListener().parse(jsonObject);
		}
	}

}
