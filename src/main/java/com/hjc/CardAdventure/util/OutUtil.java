package com.hjc.CardAdventure.util;

import com.hjc.CardAdventure.pojo.player.PlayerInformation;

public class OutUtil {
    private OutUtil() {
    }

    //计算休息回血数值
    public static int getRestValue() {
        int value = PlayerInformation.player.getMaxBlood() / 3;
        return value;
    }
}
