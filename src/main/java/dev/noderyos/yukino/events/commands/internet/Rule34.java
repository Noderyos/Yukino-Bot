package dev.noderyos.yukino.events.commands.internet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rule34 {
    public List<String> getList(String search){
        List<String> results = new ArrayList<>();
        try{
            Document doc = Jsoup.connect("https://rule34.xxx/index.php?page=post&s=list&tags=" + search).get();
            Elements newsHeadlines = doc.select(".preview");
            for (Element headline : newsHeadlines) {
                results.add(headline.absUrl("src"));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return results;
    }
}
