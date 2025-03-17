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
public class EnemyCards {
    public static String CARD_ADDRESS = "data/card/enemy/";
    public static String ENEMY_CARDS_ADDRESS = "data/configuration/enemyCards/";
    //1阶段
    private ArrayList<String> stageOne;
    //2阶段
    private ArrayList<String> stageTwo;
    //3阶段
    private ArrayList<String> stageThree;
    //4阶段
    private ArrayList<String> stageFour;

    //根据天数获得对应卡牌数组
    private ArrayList<Card> getCardsByDay(int day) {
        ArrayList<String> cardName = byDay(day);
        ArrayList<Card> cards = new ArrayList<>();
        for (String s : cardName) {
            Card card = FXGL.getAssetLoader().loadJSON(CARD_ADDRESS + s + ".json", Card.class).get();
            cards.add(card);
        }
        return cards;
    }

    //根据天数获得对应阶段的数组
    private ArrayList<String> byDay(int day) {
        return switch ((day - 1) % 6 + 1) {
            case 1 -> stageOne;
            case 2 -> stageTwo;
            case 3 -> stageThree;
            default -> stageFour;
        };
    }

    //随机获得一张怪物卡牌
    public static Card randomEnemyCard(int day) {
        ArrayList<Card> cards = getCardsByName(day);
        Random r = new Random();
        return cards.get(r.nextInt(cards.size()));
    }

    //根据怪物名字数组（英文）获取怪物卡牌集合，需要天数
    private static ArrayList<Card> getCardsByName(int day) {
        ArrayList<Card> cards = new ArrayList<>();
        for (String enemy : SeasonMonsterPool.enemyNames) {
            EnemyCards enemyCards = getEnemyCards(enemy);
            cards.addAll(enemyCards.getCardsByDay(day));
        }
        return cards;
    }

    //根据怪物名字（英文）获取怪物卡组对象
    private static EnemyCards getEnemyCards(String name) {
        return FXGL.getAssetLoader().loadJSON(ENEMY_CARDS_ADDRESS + name + "Cards.json", EnemyCards.class).get();
    }
}
