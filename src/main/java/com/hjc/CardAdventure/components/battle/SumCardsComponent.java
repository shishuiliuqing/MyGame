package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.util.AttributeUtil;
import com.hjc.CardAdventure.util.OutUtil;
import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SumCardsComponent extends Component {
    public static int remainingProduce;

    @Override
    public void onAdded() {
        shuffleNum();

        addComponent();
    }

    //刷新出牌数
    public void shuffleNum() {
        remainingProduce = AttributeUtil.mathProduceNum();
    }

    public void addComponent() {
        //生成剩余出牌数文本
        Text text = new Text("<当前剩余出牌数：" + remainingProduce + (remainingProduce >= 10 ? "" : " ") + ">");
        text.setFont(new Font("华文行楷", 30));
        text.setFill(Color.WHITE);
        text.setTranslateX(25);
        text.setTranslateY(770);
        entity.getViewComponent().addChild(text);
    }

    //更新当前可出牌数
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }

    //出牌数警告
    public static void warn() {
        //生成剩余出牌数文本
        Text text = new Text("<当前剩余出牌数：0 >");
        text.setFont(new Font("华文行楷", 30));
        text.setFill(Color.RED);
        text.setTranslateX(25);
        text.setTranslateY(770);
        Entity warn = FXGL.entityBuilder().view(text).buildAndAttach();

        FadeTransition ft = new FadeTransition(Duration.seconds(1), text);
        ft.setToValue(0);
        ft.setOnFinished(e -> {
            warn.removeFromWorld();
        });
        ft.play();
    }

    public void shuffleAndUpdate() {
        shuffleNum();
        update();
    }
}
