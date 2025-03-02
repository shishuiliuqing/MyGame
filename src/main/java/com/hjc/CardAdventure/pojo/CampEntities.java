package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class CampEntities {
    //篝火
    public static Entity fire;
    //牌堆
    public static Entity cards;


    //初始化营地各实体
    public static void initCampEntities() {
        fire = FXGL.spawn("fire");
        cards = FXGL.spawn("cards");
    }
}
