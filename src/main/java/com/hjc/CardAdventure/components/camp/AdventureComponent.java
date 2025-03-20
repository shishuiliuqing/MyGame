package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.CampEntities;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.enemy.EnemyType;
import com.hjc.CardAdventure.subScene.ExperienceSubScene;
import com.hjc.CardAdventure.subScene.RewardSubScene;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AdventureComponent extends Component {
    public static int adventureNum = 0;

    @Override
    public void onAdded() {
        GoAdventure();
    }

    public void GoAdventure() {
        Rectangle rectangle = new Rectangle(200, 80, Color.ORANGE);
        rectangle.setTranslateX(CardAdventureApp.APP_WITH / 2.0 + 50);
        rectangle.setTranslateY(800);
        entity.getViewComponent().addChild(rectangle);

        Texture img = FXGL.texture("camp/adventure.png", 80, 80);
        img.setTranslateX(CardAdventureApp.APP_WITH / 2.0 + 50);
        img.setTranslateY(800);
        entity.getViewComponent().addChild(img);

        Text text = new Text("探险");
        text.setFont(new Font("华文行楷", 50));
        text.setFill(Color.WHITE);
        text.setTranslateX(CardAdventureApp.APP_WITH / 2.0 + 130);
        text.setTranslateY(850);
        entity.getViewComponent().addChild(text);

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        //entity.getViewComponent().addOnClickHandler(e -> rest());
        //entity.getViewComponent().addOnClickHandler(e->FXGL.getSceneService().pushSubScene(new RewardSubScene()));
        //entity.getViewComponent().addOnClickHandler(e -> FXGL.getSceneService().pushSubScene(new ExperienceSubScene(1, 0)));
        entity.getViewComponent().addOnClickHandler(e -> adventure());
    }

    //战斗爽！
    private void adventure() {
        //加载动画
        Rectangle rectangle = new Rectangle(1, 1, Color.BLACK);
        rectangle.setTranslateX((CardAdventureApp.APP_WITH - 1) / 2.0);
        rectangle.setTranslateY((CardAdventureApp.APP_HEIGHT + 70 - 1) / 2.0);
        Entity load = FXGL.entityBuilder().view(rectangle).buildAndAttach();

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(CardAdventureApp.APP_WITH);
        st.setToY(CardAdventureApp.APP_HEIGHT - 70);

        st.setOnFinished(e -> {
            //移除营地实体
            CampEntities.clearCampEntities();
            //移除加载动画
            load.removeFromWorld();
            //加载文本
            loadText();
        });
        st.play();
    }

    //文本创建
    private void loadText() {

    }

    //查看探险键信息
    private void lookInformation() {
        String text = "休息" + Effect.NEW_LINE;
        text += "将出发进行探险，可以解锁某些事件（使其在之后可以触发），会遇到精英怪，每个场景最多探险4次" + Effect.NEW_LINE;
        text += "这你也敢探？看来你很勇嘛！！！\n\n          --by某位不愿意透露姓名的开发者";
        TipBarComponent.update(text);
    }
}
