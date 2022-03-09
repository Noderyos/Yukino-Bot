package ml.noderyos.yukino.events.commands.internet;

import ml.noderyos.yukino.events.commands.InfosBot;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Twitter {
    public static void Twit(String message) {
        try{
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey(InfosBot.TwitterOAuthConsumerKey)
                    .setOAuthConsumerSecret(InfosBot.TwitterOAuthConsumerSecret)
                    .setOAuthAccessToken(InfosBot.TwitterOAuthAccessToken)
                    .setOAuthAccessTokenSecret(InfosBot.TwitterOAuthAccessTokenSecret);
            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter4j.Twitter twitter = tf.getInstance();
            twitter.updateStatus(message);
        }catch (TwitterException e){
            e.printStackTrace();
        }
    }
}