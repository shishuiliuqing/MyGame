package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class CampEntities {
    //篝火
    public static Entity fire;
    //牌堆
    public static Entity cards;
    //人物属性列表
    public static Entity playerAttribute;
    //战斗
    public static Entity battle;

    //初始化营地各实体
    public static void initCampEntities() {
        fire = FXGL.spawn("fire");
        cards = FXGL.spawn("cards");
        playerAttribute = FXGL.spawn("playerAttribute");
        battle = FXGL.spawn("battle");
    }
}
