package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.CampEntities;
import com.hjc.CardAdventure.pojo.effects.Effect;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class RestComponent extends Component {
    public static boolean canRest = true;

    @Override
    public void onAdded() {
        GoRest();
    }

    public void GoRest() {
        Rectangle rectangle = new Rectangle(200, 80, Color.GREEN);
        rectangle.setTranslateX(CardAdventureApp.APP_WITH / 2.0 - 550);
        rectangle.setTranslateY(800);
        entity.getViewComponent().addChild(rectangle);

        Texture img = FXGL.texture("camp/rest.png", 80, 80);
        img.setTranslateX(CardAdventureApp.APP_WITH / 2.0 - 550);
        img.setTranslateY(800);
        entity.getViewComponent().addChild(img);

        Text text = new Text("休息");
        text.setFont(new Font("华文行楷", 50));
        text.setFill(Color.WHITE);
        text.setTranslateX(CardAdventureApp.APP_WITH / 2.0 - 550 + 80);
        text.setTranslateY(850);
        entity.getViewComponent().addChild(text);

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        //entity.getViewComponent().addOnClickHandler(e -> rest());
    }

    //战斗爽！
    private void rest() {
        //加载动画
        Rectangle rectangle = new Rectangle(1, 1, Color.BLACK);
        rectangle.setTranslateX((CardAdventureApp.APP_WITH - 1) / 2.0);
        rectangle.setTranslateY((CardAdventureApp.APP_HEIGHT + 70 - 1) / 2.0);
        Entity load = FXGL.entityBuilder().view(rectangle).buildAndAttach();

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(CardAdventureApp.APP_WITH);
        st.setToY(CardAdventureApp.APP_HEIGHT - 70);

        st.setOnFinished(e -> {
            CampEntities.battle.removeFromWorld();
            CampEntities.playerAttribute.removeFromWorld();
            CampEntities.fire.removeFromWorld();
            CampEntities.cards.removeFromWorld();

            load.removeFromWorld();
            BattleEntities.initBattleEntities();
        });
        st.play();
    }

    //查看休息键信息
    private void lookInformation() {
        String text = "休息" + Effect.NEW_LINE;
        text += "恢复1/3的生命值" + Effect.NEW_LINE;
        text += "年轻就是好，一天到晚想睡就睡\n\n          --by某位不愿意透露姓名的开发者";
        TipBarComponent.update(text);
    }
}
