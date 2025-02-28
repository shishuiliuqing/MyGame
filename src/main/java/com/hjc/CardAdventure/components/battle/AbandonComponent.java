package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.BattleEntities;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AbandonComponent extends Component {
    public static boolean isLight = false;
    //需要的弃牌数
    public static int needAbandon = 0;
    //弃置文本实体
    private Entity tipEntity;


    @Override
    public void onAdded() {
        addButton();
        entity.getViewComponent().addOnClickHandler(e -> abandon());
    }

    //创建按钮
    public void addButton() {
        //创建弃牌按钮
        Texture button = FXGL.texture(isLight ? "buttonLight.png" : "buttonDark.png", 270 / 2.0, 98 / 2.0);
        button.setTranslateX(CardAdventureApp.APP_WITH - 150);
        button.setTranslateY(CardAdventureApp.APP_HEIGHT - 309);
        entity.getViewComponent().addChild(button);
        //创建使用文本
        Text useText = new Text("弃    置");
        useText.setFont(new Font("华文行楷", 29));
        useText.setTranslateX(CardAdventureApp.APP_WITH - 150 + 25);
        useText.setTranslateY(CardAdventureApp.APP_HEIGHT - 309 + 35);
        entity.getViewComponent().addChild(useText);
    }

    //弃牌判断
    public void abandonJudge() {
        System.out.println(needAbandon);
        //弃牌数<=0，无需弃牌
        if (needAbandon <= 0) {
            ActionOverComponent.nextStage = true;
            return;
        }
        //如果需要弃置的牌大于手牌，弃置所有手牌
        if (needAbandon >= CardComponent.HAND_CARDS.size()) {
            while (!CardComponent.HAND_CARDS.isEmpty()) {
                CardComponent cardComponent = CardComponent.HAND_CARDS.get(0);
                cardComponent.abandonAnimation1();
                //手牌区删除此牌
                //CardComponent.HAND_CARDS.remove(cardComponent);
            }

            //进入下一阶段
            ActionOverComponent.nextStage = true;
            return;
        }


        //允许选牌
        CardComponent.selectable = true;
        //将被选择的牌全部放下
        while (!CardComponent.CARD_COMPONENTS.isEmpty()) {
            CardComponent cardComponent = CardComponent.CARD_COMPONENTS.get(0);
            cardComponent.select();
        }
        //自己选择弃牌
        CardComponent.isAbandon = true;
        //生成弃牌提示文本
        generateTip();
    }

    //执行弃牌效果
    private void abandon() {
        //未亮时不允许点击
        if (!isLight) return;
        //不允许选牌
        CardComponent.selectable = false;
        //弃牌键变暗
        resetButton(false);

        while (!CardComponent.CARD_COMPONENTS.isEmpty()) {
            CardComponent cardComponent = CardComponent.CARD_COMPONENTS.get(0);
            cardComponent.abandonAnimation1();
            //选择牌区删除此牌
            CardComponent.CARD_COMPONENTS.remove(0);
            //手牌区删除此牌
            CardComponent.HAND_CARDS.remove(cardComponent);
        }

        //删除文本
        tipEntity.removeFromWorld();
        //进入下一阶段
        ActionOverComponent.nextStage = true;
    }

    //弃牌提示文本
    private void generateTip() {
        Text text = new Text("请选择弃置 " + needAbandon + " 张牌");
        text.setFont(new Font("华文行楷", 50));
        text.setFill(Color.WHITE);
        text.setTranslateX(CardAdventureApp.APP_WITH * 1.0 / 2 - 150);
        text.setTranslateY(CardAdventureApp.APP_HEIGHT * 1.0 - 300);
        tipEntity = FXGL.entityBuilder().view(text).buildAndAttach();
    }

    //重置按钮
    public void resetButton(boolean isLight) {
        AbandonComponent.isLight = isLight;
        entity.getViewComponent().clearChildren();
        addButton();
    }
}
