package com.hjc.CardAdventure.configuration;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.card.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Random;

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

    //初始化玩家牌组
    public static ArrayList<Card> getCards(ArrayList<String> cards) {
        ArrayList<Card> result = new ArrayList<>();
        for (String s : cards) {
            Card card = FXGL.getAssetLoader().loadJSON(CARD_ADDRESS + s + ".json", Card.class).get();
            result.add(card);
        }
        return result;
    }

    //日常战斗，60%白，30蓝，10%金
    //根据数量随机获得人物卡牌
    public static ArrayList<Card> rewardCards(int num, PlayerCards playerCards) {
        Random r = new Random();
        ArrayList<Card> cards = new ArrayList<>();
        String name;
        for (int i = 0; i < num; i++) {
            int n = r.nextInt(100) + 1;
            if (n <= 60) {
                name = playerCards.getWhite().get(r.nextInt(playerCards.getWhite().size()));
                cards.add(FXGL.getAssetLoader().loadJSON(CARD_ADDRESS + name + ".json", Card.class).get());
            } else if (n <= 90) {
                name = playerCards.getBlue().get(r.nextInt(playerCards.getBlue().size()));
                cards.add(FXGL.getAssetLoader().loadJSON(CARD_ADDRESS + name + ".json", Card.class).get());
            } else {
                name = playerCards.getYellow().get(r.nextInt(playerCards.getYellow().size()));
                cards.add(FXGL.getAssetLoader().loadJSON(CARD_ADDRESS + name + ".json", Card.class).get());
            }
        }
        return cards;
    }
}
