package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import com.hjc.CardAdventure.util.Utils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ConsumeCardsComponent extends Component {
    private int num;

    @Override
    public void onAdded() {
        //添加组件
        addComponents();

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> lookAbandonCards());
    }

    private void addComponents() {
        num = BattleInformation.CONSUME_CARDS.size();
        //生成一个简单的圆
        StackPane stackPane = Utils.generateCircleNum(1825, 630, 25, num, "#7700bb", new Font("微软雅黑", 15));
        entity.getViewComponent().addChild(stackPane);
    }

    //消耗牌堆信息
    private void lookInformation() {
        TipBarComponent.update("消耗牌堆，牌数：" + this.num);
    }

    //查看消耗牌堆
    private void lookAbandonCards() {
        LookCardsSubScene.cards = BattleInformation.CONSUME_CARDS;
        LookCardsSubScene.cardsType = "消耗区";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }

    public void update() {
        entity.getViewComponent().clearChildren();
        addComponents();
    }
}
