package ml.noderyos.yukino;

import ml.noderyos.yukino.events.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;
public class Bot {
    public static void main(String[] args) throws LoginException{
        JDA bot = JDABuilder.createLight(InfosBot.TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Other())
                .addEventListeners(new Moderator())
                .addEventListeners(new Send())
                .setActivity(Activity.playing(">help"))
                .build();
    }
}