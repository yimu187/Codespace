package com.openpayd.task.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class EncodeUtilTest {

    @Test
    public void encodeDecodeTest(){
        StringBuilder builder = new StringBuilder();
        builder.append("a").append("a");
        boolean res = null instanceof String;

        String originalText = "origText";
        String encodedText = EncodeUtil.encode(originalText);

        String decodedText = EncodeUtil.decode(encodedText);

        Assert.isTrue(originalText.equals(decodedText), "Not Equal");
    }
}
