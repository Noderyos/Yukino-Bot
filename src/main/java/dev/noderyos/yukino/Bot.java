package dev.noderyos.yukino;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import dev.noderyos.yukino.events.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Bot {
    public static void main(String[] args){
        if(args.length != 1){
            System.err.println("Please provide a config file as parameter");
            System.exit(69);
        }else{
            Map<String,String> config = null;
            try {
                config = new Gson().fromJson(new JsonReader(new FileReader(args[0])), Map.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JDA bot = JDABuilder.createLight(config.get("token"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES,GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new Other(config))
                    .addEventListeners(new Moderator(config))
                    .addEventListeners(new Send(config))
                    .setActivity(Activity.playing(config.get("prefix") + "help"))
                    .build();


        }
    }
}