package dev.noderyos.yukino.events.commands.internet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class HanimeTv {
    public static HashMap<Integer,String> getVideosFlux(String page) {
        HashMap<Integer,String> vids = new HashMap<>();
        try{
            Document doc = Jsoup.connect(page).get();
            String[] json = doc.getElementsByTag("script").get(10).html().substring(16).split(",");
            for(int a = 0;a< json.length;a++){
                if(json[a].split(":")[0].equals("\"url\"")){
                    if(json[a].substring(7).substring(0,json[a].substring(7).length()-1).contains("m3u8")){
                        vids.put(Integer.valueOf(json[a-4].split(":")[1].substring(1).substring(0,json[a-4].split(":")[1].substring(1).length()-1)),json[a].substring(7).substring(0,json[a].substring(7).length()-1));
                    }
                }
            }
            return vids;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}