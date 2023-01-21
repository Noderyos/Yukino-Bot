package dev.noderyos.yukino.events.commands.internet;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Map;

public class Twitter {

    ConfigurationBuilder cb;
    TwitterFactory tf;
    twitter4j.Twitter twitter;

    public Twitter(Map<String,String> c){
        this.cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(c.get("TwitterOAuthConsumerKey"))
                .setOAuthConsumerSecret(c.get("InfosBot.TwitterOAuthConsumerSecret"))
                .setOAuthAccessToken(c.get("InfosBot.TwitterOAuthAccessToken"))
                .setOAuthAccessTokenSecret(c.get("InfosBot.TwitterOAuthAccessTokenSecret"));
        this.tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();

    }

    public Long Twit(String message) {
        try{
            Status status = twitter.updateStatus(message);
            return status.getId();
        }catch (TwitterException e){
            e.printStackTrace();
        }
        return 666L;
    }
}