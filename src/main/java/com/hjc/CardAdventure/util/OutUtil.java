package com.hjc.CardAdventure.util;

import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.ArrayList;
import java.util.Random;

public class OutUtil {
    private OutUtil() {
    }

    //计算休息回血数值
    public static int getRestValue() {
        int value = PlayerInformation.player.getMaxBlood() / 3;
        return value;
    }

    //数组打乱
    public static void disruptCards(ArrayList<Card> cards) {
        Random r = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int n = r.nextInt(cards.size());
            Card t = cards.get(i);
            cards.set(i, cards.get(n));
            cards.set(n, t);
        }
    }
}
