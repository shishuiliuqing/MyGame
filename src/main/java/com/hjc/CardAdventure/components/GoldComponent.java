package com.hjc.CardAdventure.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.pojo.Entities;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class GoldComponent extends Component {
    @Override
    public void onAdded() {
        Texture goldTexture = FXGL.texture("gold.png", 35, 35);
        goldTexture.setTranslateX(460);
        goldTexture.setTranslateY(15);
        entity.getViewComponent().addChild(goldTexture);

        Label label = new Label(String.valueOf(PlayerInformation.gold));
        label.setMaxSize(100, 0);
        label.setFont(new Font("微软雅黑", 25));
        label.setTranslateX(505);
        label.setTranslateY(15);
        entity.getViewComponent().addChild(label);
    }

    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}
