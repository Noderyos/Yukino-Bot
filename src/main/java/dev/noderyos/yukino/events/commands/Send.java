package dev.noderyos.yukino.events.commands;

import dev.noderyos.yukino.events.commands.internet.Heroku;
import dev.noderyos.yukino.events.commands.internet.Rule34;
import dev.noderyos.yukino.events.commands.internet.Twitter;
import dev.noderyos.yukino.utils.RandomColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;


public class Send extends ListenerAdapter {

    Map<String,String> config;
    Twitter twitter;

    public Send(Map<String,String> c){
        this.config = c;
        this.twitter = new Twitter(c);
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String[] message = msg.getContentRaw().split(" ");
        String[] NSFW = {
                "ass","anal","bdsm","classic","cum","creampie","manga","femdom",
                "hentai","incest","masturbation","public","ero","orgy","elves","yuri",
                "pantsu","pussy","glasses","cuckold","blowjob","boobjob","handjob","footjob",
                "boobs","thighs","ahegao","uniform","gangbang","tentacles","gif","nsfwNeko",
                "nsfwMobileWallpaper","zettaiRyouiki"};
        String[] SFW = {
                "wave","tea","punch","poke","pat","kiss","feed","hug","cuddle","cry","slap",
                "lick","bite","dance","boop","sleep","like","kill","tickle","nosebleed",
                "threaten","depression","jahy_arts","neko_arts","coffee_arts","wallpaper",
                "mobileWallpaper"};
        if (!event.getAuthor().isBot()) {
            if(message[0].startsWith(config.get("prefix"))){
                    for (String imgName : SFW) {
                        if (message[0].replaceFirst(config.get("prefix"),"").equals(imgName)) {
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setImage(Heroku.getImage("sfw/" + imgName));
                            eb.setColor(Color.decode(RandomColor.random()));
                            String url = Heroku.getImage("sfw/" + imgName);
                            eb.setImage(url);
                            eb.setTitle(event.getAuthor().getName() + " requested " + imgName,url);
                            event.getChannel().sendMessageEmbeds(eb.build()).queue();
                        }
                    }
                    for (String imgName : NSFW) {
                        if (message[0].replaceFirst(config.get("prefix"),"").equals(imgName)) {
                            if (!event.getMessage().isFromGuild() || ((TextChannel)event.getChannel()).isNSFW()) {
                                EmbedBuilder eb = new EmbedBuilder();
                                String url = Heroku.getImage("nsfw/" + imgName);
                                eb.setImage(url);
                                eb.setColor(Color.decode(RandomColor.random()));
                                eb.setFooter("At " + new Date());
                                eb.setTitle(event.getAuthor().getName() + " requested " + imgName,url);
                                event.getChannel().sendMessageEmbeds(eb.build()).addActionRow(Button.danger("del","Emergency Delete")).queue();
                            }
                            else {
                                event.getChannel().sendMessage("You are not in NSFW Channel " + event.getAuthor().getName()).queue();
                            }
                        }
                    }
                    switch (message[0].replaceFirst(config.get("prefix"),"")){
                        case "rule34":
                        case "r34":
                            if(!event.getMessage().isFromGuild() || ((TextChannel)event.getChannel()).isNSFW()) {
                                if(message.length > 1){
                                    List<String> res = new Rule34().getList(msg.getContentRaw().substring(message[0].length()));
                                    String rand = res.get(new Random().nextInt(res.size()));
                                    EmbedBuilder eb = new EmbedBuilder();
                                    eb.setImage(rand);
                                    eb.setColor(Color.decode(RandomColor.random()));
                                    eb.setFooter("At " + new Date());
                                    eb.setTitle("Requested by " + event.getAuthor().getName());
                                    event.getChannel().sendMessageEmbeds(eb.build()).addActionRow(Button.danger("del","Emergency Delete")).queue();
                                }else{
                                    msg.getChannel().sendMessage("Usage : " + message[0] + " [search]").queue();
                                }
                            }else{
                                event.getChannel().sendMessage("You are not in NSFW Channel " + event.getAuthor().getName()).queue();
                            }
                            break;
                        case "send":
                            String newMsg = msg.getContentRaw().replaceFirst(config.get("prefix") + "send", "");
                            event.getChannel().sendMessage(newMsg).queue();
                            if(event.isFromGuild()){
                                event.getMessage().delete().queue();
                            }
                            break;
                        case "iloveu":
                            if(!event.getMessage().isFromGuild() || ((TextChannel)event.getChannel()).isNSFW()) {
                                try{
                                    File file = new File(getClass().getResource("/iloveu.png").toURI());
                                    event.getChannel().sendFiles(FileUpload.fromData(file)).addActionRow(Button.danger("del","Emergency Delete")).queue();
                                }catch (URISyntaxException e){
                                    e.printStackTrace();
                                }
                            }else{
                                event.getChannel().sendMessage("You are not in NSFW Channel " + event.getAuthor().getName()).queue();
                            }
                            break;
                        case "pp":
                            if(msg.getMentions().getMentions().size() == 0) {
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setImage(event.getAuthor().getAvatarUrl());
                                eb.setColor(Color.decode(RandomColor.random()));
                                eb.setFooter("At " + new Date());
                                eb.setTitle("Requested by " + event.getAuthor().getName());
                                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                            }else{
                                User avaUser = event.getMessage().getMentions().getUsers().get(0);
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setImage(avaUser.getAvatarUrl());
                                eb.setColor(Color.decode(RandomColor.random()));
                                eb.setFooter("At " + new Date());
                                eb.setTitle("Requested by " + event.getAuthor().getName());
                                event.getChannel().sendMessageEmbeds(eb.build()).queue();
                            }
                            break;
                        case "twitter":
                            if(event.getChannel().getIdLong() == 797118433732329483L){
                                String twitmsg = msg.getContentRaw().replaceFirst(config.get("prefix") + "twitter", "");
                                if (twitmsg.equals("")) {
                                    event.getChannel().sendMessage("Il faut au moins un argument").queue();
                                } else {
                                    Long code = twitter.Twit(twitmsg);
                                    event.getChannel().sendMessage("Message twitté\n https://twitter.com/Noderyos/status/" + code).queue();
                                }
                            }else{
                                event.getChannel().sendMessage("Tu n'es pas dans le bon channel " + event.getAuthor().getName()).queue();
                            }
                            break;
                        case "sheesh":
                            event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/829622450584289280/846824204547522680/image0.png").queue();
                            break;
                    }
            }
        }
    }
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getButton().getId().equals("del")){
            if(event.getUser().getId().equals(config.get("owner"))){
                event.getMessage().delete().complete();
            }else{
                InteractionHook h =  event.reply("Seul le grand chef a le droit de faire ça <@" + event.getUser().getId() + ">").complete();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        h.deleteOriginal().complete();
                    }
                },10000);
            }
        }
    }
}