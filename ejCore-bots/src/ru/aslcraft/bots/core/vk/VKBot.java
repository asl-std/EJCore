package ru.aslcraft.bots.core.vk;

import java.util.Map;

import org.json.JSONObject;

import lombok.SneakyThrows;
import ru.aslcraft.bots.core.vk.api.GetLongPollHistory;
import ru.aslcraft.bots.core.vk.api.GetLongPollServer;

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
