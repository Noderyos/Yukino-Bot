package dev.noderyos.yukino.events.commands;

import dev.noderyos.yukino.events.commands.internet.HanimeTv;
import dev.noderyos.yukino.events.commands.internet.TinyUrl;
import dev.noderyos.yukino.utils.Formater;
import dev.noderyos.yukino.utils.RandomColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Other extends ListenerAdapter {

    Map<String,String> config;

    public Other(Map<String,String> c){
        this.config = c;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] message = msg.getContentRaw().split(" ");
        message[0] = message[0].toLowerCase();
        if (!event.getAuthor().isBot()) {
            if(message[0].startsWith(config.get("prefix"))){
                switch (message[0].replaceFirst(config.get("prefix"), "").toLowerCase()){
                    case "ping":
                        event.getChannel().sendMessage("My ping is " + event.getJDA().getGatewayPing() + "ms").queue();
                        break;
                    case "help":
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(Color.decode(RandomColor.random()));
                        Random rd = new Random();
                        String[] codes = {"100","101","102","200","201","202","203","204","206","207","300","301","302","303","304","305","307","308","400","401","402","403","404","405","406","407","408","409","410","411","412","413","414","415","416","417","418","420","421","422","423","424","425","426","429","431","444","450","451","497","498","499","500","501","502","503","504","506","507","508","509","510","511","521","523","525","599"};
                        eb.setAuthor(event.getAuthor().getName(),"https://http.cat/" + codes[rd.nextInt(codes.length)],event.getAuthor().getAvatarUrl());
                        eb.setTitle("Help Command");

                        eb.addField("\nAdmin","",false);
                        eb.addField("Clear","Clear the number of message specified",true);

                        eb.addField("\nInternet","",false);
                        eb.addField("Short","Send requested URL Shorten by TinyUrl",true);
                        eb.addField("Hanimedl","Return m3u8 fluxs of the vid URL",true);
                        eb.addField("Twitter","Twit args of the command",true);

                        eb.addField("\nFun","",false);
                        eb.addField("Ping","Simple ping-pong command",true);
                        eb.addField("Send","Return args of the command",true);

                        eb.addField("Images","",false);
                        eb.addField("From API","Type : " + config.get("prefix") + " + one of the left elements here : https://justpaste.it/imgLinks",true);
                        eb.addField("PP","Return the PP of Pinged User",true);
                        eb.addField("Sheesh","Return a picture (choosen by Sam)",true);
                        eb.addField("Iloveu","Return a fun pic of Me",true);

                        eb.setFooter("At " + new Date());
                        event.getChannel().sendMessageEmbeds(eb.build()).queue();
                        break;
                    case "short":
                        try {
                            event.getChannel().sendMessage(TinyUrl.shorten(message[1])).queue();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "hanimedl":
                        if(message.length == 2){
                            String fina = "";
                            HashMap<Integer,String> gg = HanimeTv.getVideosFlux(message[1]);
                            for(int i : gg.keySet()){
                                fina += i + " => " + gg.get(i) + "\n";
                            }
                            event.getChannel().sendMessage(Formater.unescapeUnicode(fina)).addActionRow(Button.danger("del","Emergency Delete")).queue();

                        }else{
                            event.getChannel().sendMessage("Usage : " + config.get("prefix") + "hanimedl <Video URL>").queue();
                        }
                        break;
                }
            }
        }
    }
}