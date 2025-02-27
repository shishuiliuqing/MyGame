package com.hjc.CardAdventure.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.*;
import com.hjc.CardAdventure.components.battle.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//图片实体创建
public class ImgEntityFactory implements EntityFactory {
    //图片原宽
    public static final double PICTURE_X = 640;
    //卡牌框的x值
    public static final double CARD_BOX_X = 140;
    //卡牌框的y值
    public static final double CARD_BOX_Y = 189;

    @Spawns("cardSelectionBox")
    public Entity newCardSelectionBox(SpawnData data) {
        Entity cardSelectionBox = FXGL.entityBuilder(data)
                .neverUpdated()
                .build();

        ViewComponent cardSelectionBoxViewComponent = cardSelectionBox.getViewComponent();
        //人物属性区x偏移量
        double xpMoveNum = CARD_BOX_X + 10;
        //出牌区y偏移量
        double yMoveNum = CardAdventureApp.APP_HEIGHT - CARD_BOX_Y;
        //第一个选牌框的x偏移量
        double xcMoveNum = CARD_BOX_Y + xpMoveNum;
        //出牌区黑色矩形边框大小
        double boxBig = 5;

        //出牌区边框
        Rectangle box = new Rectangle(CardAdventureApp.APP_WITH, CARD_BOX_Y + boxBig);
        box.setFill(Color.BLACK);
        //box.setX(xpMoveNum - boxBig);
        box.setY(yMoveNum - boxBig);
        cardSelectionBoxViewComponent.addDevChild(box);

        //人物属性区界面
        Texture playerAttributeBox = FXGL.texture("playerAttributeBox.png", CARD_BOX_Y, CARD_BOX_Y);
        playerAttributeBox.setTranslateX(xpMoveNum);
        playerAttributeBox.setTranslateY(yMoveNum);
        cardSelectionBoxViewComponent.addDevChild(playerAttributeBox);

        //出牌区界面

        //第一个选牌框
        Texture cardBox = FXGL.texture("cardBox.png", CARD_BOX_X, CARD_BOX_Y);
        //System.out.println(cardBox[0].getWidth());
        cardBox.setTranslateX(xcMoveNum);
        cardBox.setTranslateY(yMoveNum);
        cardSelectionBoxViewComponent.addDevChild(cardBox);

        for (int i = 1; i < 10; i++) {
            Texture cardBoxCopy = cardBox.copy();
            cardBoxCopy.setTranslateX(cardBox.getWidth() * i + xcMoveNum);
            cardBoxCopy.setTranslateY(yMoveNum);
            cardSelectionBoxViewComponent.addDevChild(cardBoxCopy);
        }


        return cardSelectionBox;
    }

    //背景加载
    @Spawns("bg")
    public Entity newBg(SpawnData data) {
        Texture bg = FXGL.texture("bg/DarkForest.jpg", CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT);

        return FXGL.entityBuilder(data)
                .view(bg)
                .neverUpdated()
                .build();
    }

    //抽牌区
    @Spawns("drawCards")
    public Entity newDrawCards(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new DrawCardsComponent())
                .neverUpdated()
                .build();
    }

    //弃牌区
    @Spawns("abandonCards")
    public Entity newAbandonCards(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new AbandonCardsComponent())
                .neverUpdated()
                .build();
    }

    //游戏信息栏
    @Spawns("informationBar")
    public Entity newInformationBar(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new BarComponent())
                .neverUpdated()
                .build();
    }

    //人物属性栏
    @Spawns("attribute")
    public Entity newAttribute(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new AttributeComponent())
                .neverUpdated()
                .build();
    }

    //人物血量显示
    @Spawns("playerBlood")
    public Entity newPlayerBlood(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new BloodComponent())
                .neverUpdated()
                .build();
    }

    //金币显示
    @Spawns("gold")
    public Entity newGold(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new GoldComponent())
                .neverUpdated()
                .build();
    }

    //药水显示
    @Spawns("medicine")
    public Entity newMedicine(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new MedicineComponent())
                .neverUpdated()
                .build();
    }

    //提示文本框
    @Spawns("tipBar")
    public Entity newTipBar(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new TipBarComponent())
                .neverUpdated()
                .build();
    }

    //计牌器
    @Spawns("sumProduce")
    public Entity newProduce(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new SumCardsComponent())
                .neverUpdated()
                .build();
    }

    //行动顺序框
    @Spawns("actionBox")
    public Entity newAction(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new ActionComponent())
                .neverUpdated()
                .build();
    }
}
