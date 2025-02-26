package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

public class BattleEntities {
    //背景
    public static Entity bg;
    //出牌框
    public static Entity cardSelectionBox;
    //抽牌区
    public static Entity drawCards;
    //弃牌区
    public static Entity abandonCards;
    //属性表
    public static Entity attribute;
    //使用按钮
    public static Entity produce;
    //弃牌按钮
    public static Entity abandon;
    //目标指示
    public static Entity target;
    //结束按钮
    public static Entity actionOver;
    //提示框
    public static Entity tipBar;
    //计牌器
    public static Entity sumProduce;
    //人物实体
    public static Entity playerBattle;
    //敌人实体
    public static final Entity[] enemies = new Entity[5];
    //敌人实体生成顺序
    public static final int[] enemyGenerateOrder = {3, 2, 4, 1, 5};
    //行动序列实体
    public static Entity actionBox;

    public static void initBattleEntities() {
        //初始化背景
        //bg = FXGL.spawn("bg");
        for (int i = 0; i < 40; i++) {
            Card strike = FXGL.getAssetLoader().loadJSON("data/card/soldier/strike.json", Card.class).get();
            PlayerInformation.cards.add(strike);
            System.out.println(Effects.CardEffectsToString(strike));
        }
        //初始化战斗信息
        BattleInformation.initBattle();
        //初始化出牌框
        cardSelectionBox = FXGL.spawn("cardSelectionBox");
        //初始化抽牌区
        drawCards = FXGL.spawn("drawCards");
        //初始化弃牌区
        abandonCards = FXGL.spawn("abandonCards");
        //初始化属性区
        attribute = FXGL.spawn("attribute");
        //初始化使用按钮
        produce = FXGL.spawn("produce");
        //初始化弃牌按钮
        abandon = FXGL.spawn("abandon");
        //初始化目标指定文本
        target = FXGL.spawn("target");
        //初始化结束回合按钮
        actionOver = FXGL.spawn("actionOver");
        //初始化提示文本框
        tipBar = FXGL.spawn("tipBar");
        //初始化记牌器
        sumProduce = FXGL.spawn("sumProduce");
        //初始化人物角色
        playerBattle = FXGL.spawn("playerBattle");
        //初始化敌人
        for (int i = 0; i < BattleInformation.ENEMIES.size(); i++) {
            enemies[i] = FXGL.spawn("enemy", new SpawnData()
                    .put("boxNum", i)
                    .put("isSelected", false));
        }
        actionBox = FXGL.spawn("actionBox");

        BattleInformation.battle();
    }
}
