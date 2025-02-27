package com.hjc.CardAdventure.components.role;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.components.battle.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class EnemyComponent extends Component {
    //指定框移动距离
    private final double TARGET_X_MOVE = 550;
    private final double TARGET_Y_MOVE = 300;
    //怪物图片
    private Texture enemyTexture;
    //所在位置
    private int boxNum;
    //怪物
    private Enemy enemy;

    @Override
    public void onAdded() {
        //是否被选择
        boolean isSelected = entity.getBoolean("isSelected");
        //获得所在位置
        int index = entity.getInt("boxNum");
        boxNum = BattleEntities.enemyGenerateOrder[index];
        enemy = BattleInformation.ENEMIES.get(index);

        //加载指定图标
        Texture targetPicture = FXGL.texture(isSelected ? "targetLight.png" : "targetDark.png", 250, 250);
        targetPicture.setTranslateX(TARGET_X_MOVE + 215 * boxNum);
        targetPicture.setTranslateY(TARGET_Y_MOVE);
        entity.getViewComponent().addChild(targetPicture);


        //血条框制作
        double bloodBoxLen = 180.0;
        double bloodBoxHei = 11.0;

        Rectangle bloodBox = new Rectangle(bloodBoxLen, bloodBoxHei, Color.BLACK);
        bloodBox.setTranslateX(TARGET_X_MOVE + 215 * boxNum + bloodBoxLen / 2 - 55);
        bloodBox.setTranslateY(TARGET_Y_MOVE + 180);
        entity.getViewComponent().addChild(bloodBox);

        //血条制作
        double p = enemy.getBlood() * 1.0 / enemy.getMaxBlood();
        Rectangle blood = new Rectangle(bloodBoxLen * p, bloodBoxHei - 2, Color.RED);
        blood.setTranslateX(TARGET_X_MOVE + 215 * boxNum + bloodBoxLen / 2 - 55);
        blood.setTranslateY(TARGET_Y_MOVE + 181);
        entity.getViewComponent().addChild(blood);

        //血量数字显示
        Text bloodValue = new Text(enemy.getBlood() + "/" + enemy.getMaxBlood());
        bloodValue.setFont(new Font("华文琥珀", 20));
        bloodValue.setFill(Color.WHITE);

        Rectangle rectangle = new Rectangle(bloodBoxLen, bloodBoxHei, Color.valueOf("#FF99CC00"));
        StackPane stackPane = new StackPane(rectangle);
        stackPane.getChildren().add(bloodValue);
        //stackPane.setMaxSize(bloodBoxLen, bloodBoxHei);
        stackPane.setTranslateX(TARGET_X_MOVE + 215 * boxNum + bloodBoxLen / 2 - 55);
        stackPane.setTranslateY(TARGET_Y_MOVE + 178);
        entity.getViewComponent().addChild(stackPane);

        //图片加载
        enemyTexture = FXGL.texture(enemy.getImg(), enemy.getWidth(), enemy.getHeight());
        enemyTexture.setTranslateX(TARGET_X_MOVE + 215 * boxNum + enemy.getX());
        enemyTexture.setTranslateY(TARGET_Y_MOVE + enemy.getY());
        entity.getViewComponent().addChild(enemyTexture);

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            TipBarComponent.update(String.valueOf(boxNum));
        });
        entity.getViewComponent().addOnClickHandler(e -> target());
    }

    //受伤动画
    public void hurt(int value) {
        //受伤数字显示
        Text hurtValue = new Text("- " + value);
        hurtValue.setFill(Color.RED);
        hurtValue.setFont(new Font("华文琥珀", 50));
        //受伤人物显示
        Texture hurtEnemy = enemyTexture.multiplyColor(Color.BLACK);
        Entity hurt = FXGL.entityBuilder().view(hurtValue).view(hurtEnemy).buildAndAttach();


        //令文本移动
        TranslateTransition tNum = new TranslateTransition(Duration.seconds(0.3), hurtValue);
        tNum.setFromX(TARGET_X_MOVE + 215 * boxNum + enemy.getX());
        tNum.setFromY(TARGET_Y_MOVE + enemy.getY());
        tNum.setToX(TARGET_X_MOVE + 215 * boxNum + enemy.getX());
        tNum.setToY(TARGET_Y_MOVE + enemy.getY() - 50);
        //令人物显示移动
        TranslateTransition tHP = new TranslateTransition(Duration.seconds(0.3), hurtEnemy);
        tHP.setFromX(TARGET_X_MOVE + 215 * boxNum + enemy.getX());
        tHP.setFromY(TARGET_Y_MOVE + enemy.getY());
        tHP.setToX(TARGET_X_MOVE + 215 * boxNum + enemy.getX() + 50);
        tHP.setToY(TARGET_Y_MOVE + enemy.getY());

        ParallelTransition pt = new ParallelTransition(tNum, tHP);
        pt.setOnFinished(e -> {
            hurt.removeFromWorld();
            //if (!isExist())
                //entity.getViewComponent().addChild(enemyTexture);
        });
        entity.getViewComponent().removeChild(enemyTexture);
        pt.play();
    }

    //选择该目标
    private void target() {
        if (!TargetComponent.needTarget) return;
        if (TargetComponent.isAll) return;
        if (TargetComponent.isRam) return;
        if (enemy == TargetComponent.target) return;
        //删除上一个指定
        for (int i = 0; i < BattleEntities.enemies.length; i++) {
            Entity e = BattleEntities.enemies[i];
            if (e == null) continue;
            if (e.getBoolean("isSelected")) {
                e.getComponent(EnemyComponent.class).update(false);
            }
        }
        TargetComponent.target = enemy;
        BattleEntities.target.getComponent(TargetComponent.class).update();
        this.update(true);
    }

    //是否被选择
    public void update(boolean isSelected) {
        int boxNum = entity.getInt("boxNum");
        entity.removeFromWorld();
        BattleEntities.enemies[boxNum] = FXGL.spawn("enemy", new SpawnData()
                .put("boxNum", boxNum)
                .put("isSelected", isSelected));
    }

    //判断实体是否被添加
    public boolean isExist() {
        for (Node child : entity.getViewComponent().getChildren()) {
            if (child == enemyTexture) return true;
        }
        return false;
    }
}
