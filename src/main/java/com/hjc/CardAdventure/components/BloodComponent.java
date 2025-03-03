package com.hjc.CardAdventure.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.pojo.Entities;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;

public class BloodComponent extends Component {

    @Override
    public void onAdded() {
        //血条图片
        Texture bloodTexture = FXGL.texture("playerBlood.png", 30, 30);
        bloodTexture.setTranslateX(270);
        bloodTexture.setTranslateY(15);
        entity.getViewComponent().addChild(bloodTexture);
        //血量文本显示
        Label label = new Label(player.getBlood() + " / " + player.getMaxBlood());
        label.setFont(new Font("微软雅黑", 25));
        label.setMaxSize(100, 0);
        label.setTranslateX(315);
        label.setTranslateY(15);
        entity.getViewComponent().addChild(label);
    }

    //更新血量
    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}
