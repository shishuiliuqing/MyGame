package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerCards {
    //人物卡组地址
    public static String PLAYER_CARDS_ADDRESS = "data/configuration/playerCards/soldierCards.json";
    //卡牌所在地址
    public static String CARD_ADDRESS = "data/card/soldier/";

    //初始卡组
    ArrayList<String> initial;
    //白卡
    ArrayList<String> white;
    //蓝卡
    ArrayList<String> blue;
    //金卡
    ArrayList<String> yellow;

    public static ArrayList<Card> getCards(ArrayList<String> cards) {
        ArrayList<Card> result = new ArrayList<>();
        for (String s : cards) {
            Card card = FXGL.getAssetLoader().loadJSON(CARD_ADDRESS + s + ".json", Card.class).get();
            result.add(card);
        }
        return result;
    }
}
