package com.example.finalproject_cooktutor.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Common {
    public static final String MSG_REGISTER_ERROR = "register wrong！";
    public static final String MSG_REGISTER_EXIST = "user already exist！";
    public static final String MSG_REGISTER_SUCCESS= "register success！";

    public static Map<String,String> getHead(){
        String appKey = "810294cde267b0b8c16a310759088a95";
        String appSecret = "11461e6eca26";
        String nonce = getRandomCode();
        String curTime = String.valueOf((new Date()).getTime()/1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret,nonce,curTime);
        Map<String, String> head = new HashMap<>();
        head.put("AppKey",appKey);
        head.put("Nonce", nonce);
        head.put("CurTime",curTime);
        head.put("CheckSum",checkSum);
        head.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        return head;
    }

    public static String getRandomCode() {
        String[] code = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "s", "y", "z"
        };
        String randomCode = "";
        for (int i = 0; i < 20; i++) {
            int random = (int) (Math.random() * 36);
            randomCode += code[random];
        }
        return randomCode;
    }
}
