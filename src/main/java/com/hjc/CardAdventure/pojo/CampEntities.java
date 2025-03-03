package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.components.camp.AdventureComponent;
import com.hjc.CardAdventure.components.camp.RestComponent;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;

public class CampEntities {
    //篝火
    public static Entity fire;
    //牌堆
    public static Entity cards;
    //人物属性列表
    public static Entity playerAttribute;
    //战斗
    public static Entity battle;
    //休息
    public static Entity rest;
    //探险
    public static Entity adventure;
    //事件
    public static Entity event;

    //初始化营地各实体
    public static void initCampEntities() {
        if (InsideInformation.timeStatus == TimeStatus.DAY) {
            RestComponent.canRest = true;
        }

        fire = FXGL.spawn("fire");
        cards = FXGL.spawn("cards");
        playerAttribute = FXGL.spawn("playerAttribute");
        battle = FXGL.spawn("battle");
        if (RestComponent.canRest) rest = FXGL.spawn("rest");
        if (AdventureComponent.adventureNum < 4) adventure = FXGL.spawn("adventure");
        event = FXGL.spawn("event");
    }

    //清除所有实体
    public static void clearCampEntities() {
        fire.removeFromWorld();
        cards.removeFromWorld();
        playerAttribute.removeFromWorld();
        battle.removeFromWorld();
        rest.removeFromWorld();
        adventure.removeFromWorld();
        event.removeFromWorld();
    }
}
