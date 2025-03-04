package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.BarComponent;
import com.hjc.CardAdventure.components.BloodComponent;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.CampEntities;
import com.hjc.CardAdventure.pojo.Entities;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.RestoreBlood;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.util.OutUtil;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
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
        entity.getViewComponent().addOnClickHandler(e -> rest());
    }

    //休息
    private void rest() {
        //回血数值
        int value = OutUtil.getRestValue();
        BattleInformation.EFFECTS.add(new RestoreBlood(PlayerInformation.player, PlayerInformation.player, value));
        BattleInformation.effectExecution();

        //透明全屏背景，防止玩家继续点击操作
        Rectangle back = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_WITH, Color.rgb(0, 0, 0, 0));
        Entity rest = FXGL.entityBuilder().view(back).buildAndAttach();

        Rectangle rectangle = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT - 70);
        rectangle.setTranslateY(70);
        rest.getViewComponent().addChild(rectangle);

        FadeTransition ft = new FadeTransition(Duration.seconds(1), rectangle);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> {
            rest.removeFromWorld();
            //不允许休息
            canRest = false;
            //更新玩家血量
            Entities.playerBlood.getComponent(BloodComponent.class).update();
            //时间状态更新
            InsideInformation.turnTimeStatus();
            //更新信息栏
            Entities.informationBar.getComponent(BarComponent.class).update();
            //删除营地
            CampEntities.clearCampEntities();
            //更新营地
            CampEntities.initCampEntities();
            //张眼动画
            rest1();
        });
        ft.play();
    }

    private void rest1() {
        Rectangle back = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_WITH, Color.rgb(0, 0, 0, 0));
        Entity rest = FXGL.entityBuilder().view(back).buildAndAttach();

        Rectangle rectangle = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT - 70);
        rectangle.setTranslateY(70);
        rest.getViewComponent().addChild(rectangle);

        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(0);
        st.setToY(0);

        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), rectangle);
        ft.setToValue(0);

        ParallelTransition pt = new ParallelTransition(st, ft);
        pt.setOnFinished(e -> rest.removeFromWorld());
        pt.play();
    }

    //查看休息键信息
    private void lookInformation() {
        String text = "休息" + Effect.NEW_LINE;
        text += "恢复1/3的生命值（纯洁属性可加成）" + Effect.NEW_LINE;
        text += "年轻就是好，一天到晚想睡就睡\n\n          --by某位不愿意透露姓名的开发者";
        TipBarComponent.update(text);
    }
}
