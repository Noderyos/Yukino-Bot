package ml.noderyos.yukino.events.commands;

import ml.noderyos.yukino.events.commands.internet.DiscordWebhook;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Moderator extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message msg = event.getMessage();
        String[] message = msg.getContentRaw().split(" ");
        String badWord = "pute,connard,chocolatine,fdp,ntm,ftg,ferme ta gueule,nique,enculé,salope";
        String[] badWordList = badWord.split(",");
        for(String word : badWordList){
            if(msg.toString().toLowerCase().contains(word)){
                event.getMessage().delete().queue();
                TextChannel reportChannel = event.getGuild().getTextChannelById("800392386148696084");
                try {
                    DiscordWebhook badWordMessage = new DiscordWebhook("https://discord.com/api/webhooks/811299231653560400/-2v1X-fFV9AacmDcHCzvlGWZ3Y0rE86aP4xcrq6IKrtwrYYufZIBfYTcfGUvC3MAphKZ");
                    badWordMessage.setUsername(event.getAuthor().getName());
                    badWordMessage.setAvatarUrl(event.getAuthor().getAvatarUrl());
                    badWordMessage.setContent("Tu n'as pas le droit de dire cela :( !");
                    badWordMessage.execute();
                } catch (IOException e){
                    System.out.println(e);
                }
                reportChannel.sendMessage(event.getAuthor().getName() + " ( Son ID : " + msg.getAuthor().getId() + " ) a dit " + word + " dans le channel " + event.getChannel().getName()).queue();
            }
        }
        if (!event.getAuthor().isBot()){
            if (message[0].replaceFirst(InfosBot.PREFIX,"").equals("clear")) {
                Member user = msg.getMember();
                if (user.getPermissions().contains(Permission.MANAGE_CHANNEL)){
                    MessageHistory history = new MessageHistory(event.getTextChannel());
                    List<Message> msgs;
                    String nbstr = msg.getContentRaw().replaceFirst(">clear ", "");
                    msgs = history.retrievePast(Integer.parseInt(nbstr) - 1).complete();
                    event.getTextChannel().deleteMessages(msgs).queue();
                    event.getChannel().sendMessage("Channel Cleared").queue();
                    try{
                        TimeUnit.SECONDS.sleep(1);
                        event.getTextChannel().deleteMessages(event.getTextChannel().getHistory().retrievePast(2).complete()).queue();
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
