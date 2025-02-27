package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SumCardsComponent extends Component {
    public static int remainingProduce;

    @Override
    public void onAdded() {
        //生成剩余出牌数文本
        Text text = new Text("<当前剩余出牌数：" + remainingProduce + (remainingProduce >= 10 ? "" : " ") + ">");
        text.setFont(new Font("华文行楷",30));
        text.setFill(Color.WHITE);
        text.setTranslateX(25);
        text.setTranslateY(770);
        entity.getViewComponent().addChild(text);
    }

    public void update() {
        entity.getViewComponent().getChildren().clear();
        onAdded();
    }
}
