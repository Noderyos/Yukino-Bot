package dev.noderyos.yukino.utils;


import java.util.Random;

public class RandomColor {
    public static String random(){
        Random r = new Random();
        String[] chars = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
        StringBuilder out = new StringBuilder("#");
        for(int i = 0;i < 6;i++){
            out.append(chars[r.nextInt(16)]);
        }
        return out.toString();
    }
}
