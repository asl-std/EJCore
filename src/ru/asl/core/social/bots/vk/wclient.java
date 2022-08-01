package ru.asl.core.social.bots.vk;

import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.asl.api.bukkit.message.EText;
import ru.asl.core.Core;

import java.util.List;


public class wclient {
    @SneakyThrows
    public String body(String method, List<String> param, boolean longpoll) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            String urlparam = "";
            if (longpoll) {
                urlparam = method+"?";
                for (String p : param) {
                    String[] pp = p.replace(" ,", ",").replace(", ", ",").split(",");
                    urlparam += pp[0] + "=" + pp[1] + "&";
                }
                urlparam += "wait=25";
            } else {
                urlparam = "https://api.vk.com/method/" + method + "?";
                for (String p : param) {
                    String[] pp = p.replace(" ,", ",").replace(", ", ",").split(",");
                    urlparam += pp[0] + "=" + pp[1] + "&";
                }
                urlparam += "access_token=" + Core.getCfg().getString("vk.bot-token", "replace here with token", true) + "&v=5.131";
            }
                HttpGet request = new HttpGet(urlparam);

                CloseableHttpResponse response = httpClient.execute(request);

                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        result = EntityUtils.toString(entity);
                    }

                } finally {
                    response.close();
                }
            } finally{
                httpClient.close();
            }
            return result;
        }

}
