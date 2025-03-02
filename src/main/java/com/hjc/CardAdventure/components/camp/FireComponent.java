package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import javafx.scene.paint.Color;

public class FireComponent extends Component {

    @Override
    public void onAdded() {
        //添加篝火
        Texture fireTexture = FXGL.texture("camp/fire.png", 600, 600);
        fireTexture.setTranslateX((CardAdventureApp.APP_WITH - 600) * 1.0 / 2);
        fireTexture.setTranslateY((CardAdventureApp.APP_HEIGHT - 600) * 1.0 / 2);
        entity.getViewComponent().addChild(fireTexture);
    }
}
