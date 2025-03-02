package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

//人物属性展示
public class PlayerAttributeComponent extends Component {

    @Override
    public void onAdded() {
        Label label = new Label("当前玩家属性：\n" + PlayerInformation.player.getAttribute().displayAttribute());
        label.setMaxSize(400,400);
        label.setTranslateX(CardAdventureApp.APP_WITH - 400 - 10);
        label.setTranslateY(70);
        label.setFont(new Font("华文行楷",50));

        entity.getViewComponent().addChild(label);
    }
}
