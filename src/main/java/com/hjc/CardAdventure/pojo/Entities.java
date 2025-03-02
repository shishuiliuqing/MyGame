package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;


import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;

public class Entities {
    //信息框
    public static Entity informationBar;
    //玩家血量
    public static Entity playerBlood;
    //金币
    public static Entity gold;
    //药水
    public static Entity medicine;
    //提示文本框
    public static Entity tipBar;


    public static void initEntities() {


//        FXGL.spawn("card", new SpawnData()
//                .put("boxNum", 1)
//                .put("colorS", playerColorS)
//        );
//        FXGL.spawn("card", new SpawnData()
//                .put("boxNum", 4)
//                .put("colorS", playerColorS)
//        );
//        FXGL.spawn("card", new SpawnData()
//                .put("boxNum", 2)
//                .put("colorS", playerColorS)
//        );
        informationBar = FXGL.spawn("informationBar");
        playerBlood = FXGL.spawn("playerBlood");
        gold = FXGL.spawn("gold");
        medicine = FXGL.spawn("medicine");
        // System.out.println(player.getAttribute().displayAttribute());
        //初始化提示文本框
        tipBar = FXGL.spawn("tipBar");
        System.out.println(InsideInformation.insideEnvironmentToString());
    }
}
