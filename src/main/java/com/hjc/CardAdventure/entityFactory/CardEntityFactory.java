package com.hjc.CardAdventure.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.hjc.CardAdventure.components.battle.*;

public class CardEntityFactory implements EntityFactory {
    //卡牌宽比例
    public static final double CARD_X = 575;
    //卡牌高比例
    public static final double CARD_Y = 700;
    //卡牌相对框x偏移量比例
    public static final double CARD_MOVE_X = 30;
    //卡牌相对框y偏移量比例
    public static final double CARD_MOVE_Y = 75;

    //绘制卡牌
    @Spawns("card")
    public Entity newCard(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new CardComponent(data.get("boxNum"), data.get("card")))
                .build();
    }

    //抽牌
    @Spawns("draw")
    public Entity newDraw(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new DrawComponent(data.get("boxNum"), data.get("card")))
                .build();
    }

    //使用
    @Spawns("produce")
    public Entity newProduce(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new ProduceComponent())
                .neverUpdated()
                .build();
    }

    //弃牌
    @Spawns("abandon")
    public Entity newAbandon(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new AbandonComponent())
                .neverUpdated()
                .build();
    }

    //目标指定
    @Spawns("target")
    public Entity newTarget(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new TargetComponent())
                .build();
    }

    //回合结束
    @Spawns("actionOver")
    public Entity newActionOver(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new ActionOverComponent())
                //.neverUpdated()
                .build();
    }
}
