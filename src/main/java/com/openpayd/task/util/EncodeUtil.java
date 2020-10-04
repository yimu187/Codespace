package com.openpayd.task.util;

import java.util.Base64;

public class EncodeUtil {

    public static String encode(String originalText){
        String encodedString = Base64.getEncoder().encodeToString(originalText.getBytes());
        return encodedString;
    }

    public static String decode(String encodedText){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        String decodedText = new String(decodedBytes);
        return decodedText;
    }
}
