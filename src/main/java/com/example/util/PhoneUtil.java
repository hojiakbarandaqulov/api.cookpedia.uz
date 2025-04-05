package com.example.util;

import java.util.regex.Pattern;

public class PhoneUtil {

    public static boolean isPhone(String phone) {
        String phoneRegex = "^998\\d{9}$";
        return Pattern.matches(phoneRegex, phone);
    }


}
