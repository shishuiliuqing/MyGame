package com.hjc.CardAdventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.localization.Language;
import com.hjc.CardAdventure.configuration.PlayerCards;
import com.hjc.CardAdventure.configuration.SeasonMonsterPool;
import com.hjc.CardAdventure.entityFactory.CampEntityFactory;
import com.hjc.CardAdventure.entityFactory.CardEntityFactory;
import com.hjc.CardAdventure.entityFactory.ImgEntityFactory;
import com.hjc.CardAdventure.entityFactory.RoleEntityFactory;
import com.hjc.CardAdventure.pojo.*;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import com.hjc.CardAdventure.pojo.player.Player;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.subScene.RewardSubScene;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class CardAdventureApp extends GameApplication {
    public static final int APP_WITH = 1900;
    public static final int APP_HEIGHT = 980;
    //怪池
    public static SeasonMonsterPool seasonMonsterPool;
    //人物卡组
    public static PlayerCards playerCards;

    //游戏设置
    @Override
    protected void initSettings(GameSettings gameSettings) {
        //设置主菜单是否出现
        gameSettings.setMainMenuEnabled(false);
        //设置宽
        gameSettings.setWidth(APP_WITH);
        //设置高
        gameSettings.setHeight(APP_HEIGHT);
        //设置版本号
        gameSettings.setVersion("0.1");
        //设置标题
        gameSettings.setTitle("卡牌冒险");
        //设置图标
        gameSettings.setAppIcon("enemy/forestWolf.png");
        //设置中文
        gameSettings.getSupportedLanguages().add(Language.CHINESE);
        gameSettings.setDefaultLanguage(Language.CHINESE);
    }

    @Override
    protected void initGame() {
        //初始化环境
        InsideInformation.generateInsideEnvironment();
        //初始化怪池
        seasonMonsterPool = FXGL.getAssetLoader().loadJSON(SeasonMonsterPool.MONSTER_POOL_ADDRESS, SeasonMonsterPool.class).get();
        //System.out.println(seasonMonsterPool.getEnemies(1, TimeStatus.EVENING));

        //获得角色
        PlayerInformation.player = FXGL.getAssetLoader().loadJSON("data/player/soldier.json", Player.class).get();
        //初始化人物卡组
        playerCards = FXGL.getAssetLoader().loadJSON(PlayerCards.PLAYER_CARDS_ADDRESS, PlayerCards.class).get();
        PlayerInformation.cards.addAll(PlayerCards.getCards(playerCards.getInitial()));

        //游戏背景颜色
        FXGL.getGameScene().setBackgroundColor(Color.GRAY);
        //添加实体工厂
        FXGL.getGameWorld().addEntityFactory(new ImgEntityFactory());
        FXGL.getGameWorld().addEntityFactory(new CardEntityFactory());
        FXGL.getGameWorld().addEntityFactory(new RoleEntityFactory());
        FXGL.getGameWorld().addEntityFactory(new CampEntityFactory());
        //初始化所有实体
        Entities.initEntities();
//        for (int i = 0; i < 5; i++) {
//            Card strike = FXGL.getAssetLoader().loadJSON("data/card/soldier/defense.json", Card.class).get();
//            PlayerInformation.cards.add(strike);
//            //System.out.println(Effects.CardEffectsToString(strike));
//        }
        CampEntities.initCampEntities();
        //BattleEntities.initBattleEntities();
    }

    //启动类
    public static void main(String[] args) {
        launch(args);
    }
}
