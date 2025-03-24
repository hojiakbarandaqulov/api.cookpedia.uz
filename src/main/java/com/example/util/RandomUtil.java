package com.example.util;

import java.util.Random;

public class RandomUtil {

    public static String getRandomCode(){
        Random random = new Random();
        return String.valueOf(random.nextInt(1000,9999));
    }
}
