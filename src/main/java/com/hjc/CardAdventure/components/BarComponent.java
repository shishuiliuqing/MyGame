package com.hjc.CardAdventure.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.Entities;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.player.Player;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BarComponent extends Component {
    //信息框高度
    public static final double BAR_HEIGHT = 70;

    @Override
    public void onAdded() {
        //获取当前角色
        Player player = PlayerInformation.player;
        //创建一个信息框
        Rectangle informationBar = new Rectangle(CardAdventureApp.APP_WITH, BAR_HEIGHT, Color.valueOf("#aec6cf"));
        entity.getViewComponent().addChild(informationBar);

        //名字文本
        Label nameText = new Label(player.getName());
        //Text nameText = new Text(playerName);
        nameText.setFont(new Font("微软雅黑", 30));
        nameText.setMaxSize(220, 0);
        nameText.setTranslateX(10);
        nameText.setTranslateY(10);
        entity.getViewComponent().addChild(nameText);

        //局内信息文本添加
        Label insideInformation = new Label(InsideInformation.insideEnvironmentToString());
        insideInformation.setFont(new Font("微软雅黑", 30));
        insideInformation.setMaxSize(500, 0);
        insideInformation.setTranslateX(900);
        insideInformation.setTranslateY(10);
        entity.getViewComponent().addChild(insideInformation);

        //当前行动对象框
        Rectangle rectangle = new Rectangle(50,50,Color.BLACK);
        rectangle.setTranslateX(1400);
        rectangle.setTranslateY(10);
        entity.getViewComponent().addChild(rectangle);
    }

    //更新局内信息
    public void update() {
        entity.removeFromWorld();
        Entities.informationBar = FXGL.spawn("informationBar");
    }
}
