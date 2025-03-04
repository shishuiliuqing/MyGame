package com.hjc.CardAdventure.subScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.CampEntities;
import com.hjc.CardAdventure.pojo.Entities;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import javafx.animation.*;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

public class RewardSubScene extends SubScene {
    //前进键
    private static Texture forward = FXGL.texture("forward.png", 200, 200);
    //奖励牌框
    private static Texture box = FXGL.texture("rewardBox.png", 600, 200);


    public RewardSubScene() {
        //背景设置，不允许操作
        Rectangle rectangle = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT);
        rectangle.setFill(Color.rgb(80, 80, 80, 0.5));
        getContentRoot().getChildren().add(rectangle);

        //前进按钮设置
        //forward.setScaleX(-1);
        forward.setTranslateX(1700);
        forward.setTranslateY(800);
        forward.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Rectangle r = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_WITH, Color.rgb(0, 0, 0, 0));
            getContentRoot().getChildren().add(r);

            //加载动画
            Rectangle load = new Rectangle(1, 1);
            load.setTranslateX((CardAdventureApp.APP_WITH - 1) / 2.0);
            load.setTranslateY((CardAdventureApp.APP_HEIGHT - 1) / 2.0);
            getContentRoot().getChildren().add(load);
            ScaleTransition st = new ScaleTransition(Duration.seconds(1), load);
            st.setToX(CardAdventureApp.APP_WITH);
            st.setToY(CardAdventureApp.APP_HEIGHT);
            st.setOnFinished(event -> {
                //关闭该场景
                FXGL.getSceneService().popSubScene();
                InsideInformation.turnTimeStatus();
                //移除所有实体
                ArrayList<Entity> entities = new ArrayList<>(FXGL.getGameWorld().getEntities());
                FXGL.getGameWorld().removeEntities(entities);
                //初始化信息栏
                Entities.initEntities();
                //初始化营地
                CampEntities.initCampEntities();
            });
            st.play();

        });
        getContentRoot().getChildren().add(forward);

        //战斗胜利文本
        Label winText = new Label("战 斗 胜 利 ！");
        winText.setMaxSize(500, 50);
        winText.setFont(new Font("华文行楷", 83));
        winText.setTextFill(Color.YELLOW);
        winText.setTranslateX((CardAdventureApp.APP_WITH - 500) / 2.0);
        winText.setTranslateY(70);
        getContentRoot().getChildren().add(winText);

        //奖励框
        box.setTranslateX((CardAdventureApp.APP_WITH - 600) / 2.0);
        box.setTranslateY(170);
        getContentRoot().getChildren().add(box);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), box);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> {
            //矩形背景框
            Rectangle rBox = new Rectangle(600, 1);
            rBox.setTranslateX((CardAdventureApp.APP_WITH - 600) / 2.0);
            rBox.setTranslateY(250);
            rBox.setFill(Color.rgb(255, 255, 255, 0.9));
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), rBox);
            tt.setFromY(250);
            tt.setToY(600);
            ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rBox);
            st.setToY(700);
            ParallelTransition pt = new ParallelTransition(tt, st);
            getContentRoot().getChildren().add(rBox);
            pt.play();
        });
        ft.play();
    }
}
