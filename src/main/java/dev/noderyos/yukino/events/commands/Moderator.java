package dev.noderyos.yukino.events.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Moderator extends ListenerAdapter {

    Map<String,String> config;

    public Moderator(Map<String,String> c){
        this.config = c;
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message msg = event.getMessage();
        String[] message = msg.getContentRaw().split(" ");
        if (!event.getAuthor().isBot()){
            if (message[0].replaceFirst(config.get("prefix"),"").equals("clear")) {
                Member user = msg.getMember();
                if (event.getMessage().isFromGuild() && (user.getPermissions().contains(Permission.MANAGE_CHANNEL) || user.getId().equals(config.get("owner")))){
                    MessageHistory history = new MessageHistory(event.getChannel());
                    List<Message> msgs;
                    String nbstr = msg.getContentRaw().replaceFirst(">clear ", "");
                    msgs = history.retrievePast(Integer.parseInt(nbstr)).complete();

                    ((TextChannel)event.getChannel()).deleteMessages(msgs).queue();
                    event.getChannel().sendMessage("Channel Cleared").queue();
                    try{
                        TimeUnit.SECONDS.sleep(1);
                        ((TextChannel)event.getChannel()).deleteMessages(event.getChannel().getHistory().retrievePast(2).complete()).queue();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }else{
                    event.getChannel().sendMessage("Tu n'as pas le droit de faire ça").queue();
                }
            }
        }
    }
}
