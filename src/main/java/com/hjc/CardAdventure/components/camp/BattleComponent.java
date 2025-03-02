package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BattleComponent extends Component {
    public static boolean canBattle = true;

    @Override
    public void onAdded() {

    }

    public void GoBattle() {
        Rectangle rectangle = new Rectangle(300,200,canBattle ? Color.RED : Color.GRAY);
    }
}
