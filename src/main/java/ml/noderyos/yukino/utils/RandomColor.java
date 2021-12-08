package ml.noderyos.yukino.utils;


import java.util.Random;

public class RandomColor {
    public static final String random(){
        Random r = new Random();
        String[] chars = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        String out = "#";
        for(int i = 0;i < 6;i++){
            out += chars[r.nextInt(16)];
        }
        return out;
    }
}
