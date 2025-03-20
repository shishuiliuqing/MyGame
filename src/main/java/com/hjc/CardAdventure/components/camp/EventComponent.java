package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.pojo.CampEntities;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.event.Event;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EventComponent extends Component {
    public static boolean canEvent = true;

    @Override
    public void onAdded() {
        GoEvent();
    }

    public void GoEvent() {
        Rectangle rectangle = new Rectangle(200, 80, Color.BLUE);
        rectangle.setTranslateX(CardAdventureApp.APP_WITH / 2.0 + 350);
        rectangle.setTranslateY(800);
        entity.getViewComponent().addChild(rectangle);

        Texture img = FXGL.texture("camp/event.png", 80, 80);
        img.setTranslateX(CardAdventureApp.APP_WITH / 2.0 + 350);
        img.setTranslateY(800);
        entity.getViewComponent().addChild(img);

        Text text = new Text("事件");
        text.setFont(new Font("华文行楷", 50));
        text.setFill(Color.WHITE);
        text.setTranslateX(CardAdventureApp.APP_WITH / 2.0 + 430);
        text.setTranslateY(850);
        entity.getViewComponent().addChild(text);

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        //entity.getViewComponent().addOnClickHandler(e -> rest());
        entity.getViewComponent().addOnClickHandler(e -> goEvent());
    }

    //战斗爽！
    private void goEvent() {
        //加载动画
        Rectangle rectangle = new Rectangle(1, 1, Color.BLACK);
        rectangle.setTranslateX((CardAdventureApp.APP_WITH - 1) / 2.0);
        rectangle.setTranslateY((CardAdventureApp.APP_HEIGHT + 70 - 1) / 2.0);
        Entity load = FXGL.entityBuilder().view(rectangle).buildAndAttach();

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(CardAdventureApp.APP_WITH);
        st.setToY(CardAdventureApp.APP_HEIGHT - 70);

        st.setOnFinished(e -> {
            CampEntities.clearCampEntities();

            load.removeFromWorld();
            Event event = FXGL.getAssetLoader().loadJSON("data/event/peachBlossomGrove.json", Event.class).get();
            Event.createTextEvent(event);
        });
        st.play();
    }

    //查看事件键信息
    private void lookInformation() {
        String text = "事件" + Effect.NEW_LINE;
        text += "有事件触发了，包括但不限于战斗，商店，祝福，受伤。。。" + Effect.NEW_LINE;
        text += "我让你点我，你是盐津虾啊，还是尔多隆呀！！！\n\n          --by某位不愿意透露姓名的开发者";
        TipBarComponent.update(text);
    }
}
