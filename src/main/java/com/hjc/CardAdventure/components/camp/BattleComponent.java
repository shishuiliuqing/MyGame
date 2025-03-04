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
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BattleComponent extends Component {
    public static boolean canBattle = true;

    @Override
    public void onAdded() {
        GoBattle();
    }

    public void GoBattle() {
        Rectangle rectangle = new Rectangle(200, 80, Color.RED);
        rectangle.setTranslateX(CardAdventureApp.APP_WITH / 2.0 - 250);
        rectangle.setTranslateY(800);
        entity.getViewComponent().addChild(rectangle);

        Texture img = FXGL.texture("camp/battle.png", 80, 80);
        img.setTranslateX(CardAdventureApp.APP_WITH / 2.0 - 250);
        img.setTranslateY(800);
        entity.getViewComponent().addChild(img);

        Text text = new Text("战斗");
        text.setFont(new Font("华文行楷", 50));
        text.setFill(Color.WHITE);
        text.setTranslateX(CardAdventureApp.APP_WITH / 2.0 - 250 + 80);
        text.setTranslateY(850);
        entity.getViewComponent().addChild(text);

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> battle());
    }

    //战斗爽！
    private void battle() {
        //该战斗为小怪
        BattleInformation.enemyType = EnemyType.LITTLE_MONSTER;
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
            BattleEntities.initBattleEntities();
        });
        st.play();
    }

    //查看战斗键信息
    private void lookInformation() {
        String text = "战斗" + Effect.NEW_LINE;
        text += "寻找怪物（非精英，非boss），并开展战斗，获得卡牌，经验，金币等奖励！" + Effect.NEW_LINE;
        text += "只有fw才会选择休息的，我必须立刻战斗爽！\n\n          --by某位不愿意透露姓名的开发者";
        TipBarComponent.update(text);
    }
}
