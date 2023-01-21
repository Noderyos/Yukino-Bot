package dev.noderyos.yukino.events.commands.internet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Heroku {
    public static String getImage(String page){
        String img = "";
        try{
            String sURL = "https://hmtai.herokuapp.com/"+ page;
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootobj = root.getAsJsonObject();
            img = rootobj.get("url").getAsString();

        }catch (IOException e){
            e.printStackTrace();
        }
        return img;
    }
}
