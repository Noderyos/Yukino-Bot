package ml.noderyos.yukino.events.commands;

import ml.noderyos.yukino.events.commands.internet.*;
import ml.noderyos.yukino.utils.RandomColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;


public class Send extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] message = msg.getContentRaw().split(" ");
        String[] NSFW = {
                "anal","baka","bj","blowjob","boobs",
                "classic","cum","cum_jpg","ero","erofeet",
                "erok","erokemo","eron","eroyuri",
                "feet","feetg","femdom","fox_girl","futanari","gasm",
                "gecg","hentai","holo","holoero","hololewd",
                "kemonomimi","keta","kuni","les",
                "lewd","lewdk","lewdkemo","neko",
                "ngif","nsfw_avatar","nsfw_neko_gif","pat","poke","pussy",
                "pussy_jpg","pwankg","Random_hentai_gif","solo",
                "solog","spank","tickle","tits","trap",
                "waifu","yuri"};
        String[] SFW = {
                "8ball","cuddle","feed","goose","hug","kiss","slap","lizard",
                "meow","smug","avatar","wallpaper","woof"};
        if (!event.getAuthor().isBot()) {
            if(message[0].startsWith(InfosBot.PREFIX)){
                    for (String imgName : SFW) {
                        if (message[0].replaceFirst(InfosBot.PREFIX,"").equals(imgName)) {
                            try {
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setImage(Nekos.getImage(imgName));
                                eb.setColor(Color.decode(RandomColor.random()));
                                eb.setFooter("At " + new Date());
                                eb.setTitle(event.getAuthor().getName());
                                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                                if(event.isFromGuild()){
                                    event.getMessage().delete().queue();
                                }
                            } catch(IOException e){
                                System.out.println(e);
                            }
                        }
                    }
                    for (String imgName : NSFW) {
                        if (message[0].replaceFirst(InfosBot.PREFIX,"").equals(imgName)) {
                            try {
                                if (event.getTextChannel().isNSFW()) {
                                    EmbedBuilder eb = new EmbedBuilder();
                                    eb.setImage(Nekos.getImage(imgName));
                                    eb.setColor(Color.decode(RandomColor.random()));
                                    eb.setFooter("At " + new Date());
                                    eb.setTitle("Requested by " + event.getAuthor().getName());
                                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                                    if(event.isFromGuild()){
                                        event.getMessage().delete().queue();
                                    }
                                }
                                else {
                                    event.getChannel().sendMessage("You are not in NSFW Channel " + event.getAuthor().getName()).queue();
                                    event.getMessage().delete().queue();
                                }
                            } catch(IOException e){
                                System.out.println(e);
                            }
                        }
                    }
                    switch (message[0].replaceFirst(InfosBot.PREFIX,"")){
                        case "send":
                            String newMsg = msg.getContentRaw().replaceFirst(InfosBot.PREFIX + "send", "");
                            event.getChannel().sendMessage(newMsg).queue();
                            event.getMessage().delete().queue();
                            break;
                        case "iloveu":
                            if(event.getTextChannel().isNSFW()) {
                                try{
                                    File file = new File(getClass().getResource("/iloveu.png").toURI());
                                    event.getChannel().sendFile(file).queue();
                                }catch (URISyntaxException e){
                                    e.printStackTrace();
                                }
                            }else{
                                event.getChannel().sendMessage("You are not in NSFW Channel " + event.getAuthor().getName()).queue();
                            }
                            break;
                        case "pp":
                            if(msg.getMentionedMembers().size() == 0) {
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setImage(event.getAuthor().getAvatarUrl());
                                eb.setColor(Color.decode(RandomColor.random()));
                                eb.setFooter("At " + new Date());
                                eb.setTitle("Requested by " + event.getAuthor().getName());
                                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                                if(event.isFromGuild()){
                                    event.getMessage().delete().queue();
                                }
                            }else{
                                User avaUser = event.getMessage().getMentionedMembers().get(0).getUser();
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setImage(avaUser.getAvatarUrl());
                                eb.setColor(Color.decode(RandomColor.random()));
                                eb.setFooter("At " + new Date());
                                eb.setTitle("Requested by " + event.getAuthor().getName());
                                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                                if(event.isFromGuild()){
                                    event.getMessage().delete().queue();
                                }
                            }
                            break;
                        case "tweeter":
                            String twitmsg = msg.getContentRaw().replaceFirst(InfosBot.PREFIX + "twitter", "");
                            if (twitmsg.equals("")) {
                                event.getChannel().sendMessage("Il faut au moins un argument").queue();
                            } else {
                                event.getChannel().sendMessage("Message twitté\n https://twitter.com/Noderyos").queue();
                                Twitter.Twit(twitmsg);
                            }
                            break;
                        case "sheesh":
                            event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/829622450584289280/846824204547522680/image0.png").queue();
                            break;
                    }
            }
        }
    }
}

