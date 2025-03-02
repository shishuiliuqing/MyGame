package com.hjc.CardAdventure.components.role;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.components.battle.ActionOverComponent;
import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.animation.FadeTransition;
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
    //是否被选择
    private boolean isSelected;
    //是否正在受伤
    private boolean isAction = false;

    @Override
    public void onAdded() {
        isSelected = entity.getBoolean("isSelected");

        //获得所在位置
        int index = entity.getInt("boxNum");
        boxNum = BattleEntities.enemyGenerateOrder[index];
        enemy = BattleInformation.ENEMIES.get(index);
        //添加其他组件
        addOther();
        //图片加载
        addEnemy();

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
                TipBarComponent.update(String.valueOf(boxNum))
        );
        entity.getViewComponent().addOnClickHandler(e -> target());
        //enemy.setArmor(60);
        //entity.getViewComponent().addOnClickHandler(e->addArmor());
    }

    @Override
    public void onUpdate(double tpf) {
        //死亡效果
//        if(enemy.getBlood() == 0) {
//
//        }
    }

    //添加其他组件
    private void addOther() {
        //加载指定图标
        Texture targetPicture = FXGL.texture(isSelected ? "targetLight.png" : "targetDark.png", 250, 250);
        targetPicture.setTranslateX(TARGET_X_MOVE + 215 * boxNum);
        targetPicture.setTranslateY(TARGET_Y_MOVE);
        entity.getViewComponent().addChild(targetPicture);


        //血条框制作
        double bloodBoxLen = 120.0;
        double bloodBoxHei = 11.0;

        Rectangle bloodBox = new Rectangle(bloodBoxLen, bloodBoxHei, Color.BLACK);
        bloodBox.setTranslateX(TARGET_X_MOVE + 215 * boxNum + bloodBoxLen / 2 + 5);
        bloodBox.setTranslateY(TARGET_Y_MOVE + 180);
        entity.getViewComponent().addChild(bloodBox);

        //血条制作
        double p = enemy.getBlood() * 1.0 / enemy.getMaxBlood();
        Rectangle blood = new Rectangle(bloodBoxLen * p, bloodBoxHei - 2, Color.RED);
        blood.setTranslateX(TARGET_X_MOVE + 215 * boxNum + bloodBoxLen / 2 + 5);
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
        stackPane.setTranslateX(TARGET_X_MOVE + 215 * boxNum + bloodBoxLen / 2 + 5);
        stackPane.setTranslateY(TARGET_Y_MOVE + 178);
        entity.getViewComponent().addChild(stackPane);
    }

    //添加敌人
    private void addEnemy() {
        if (isAction) return;
        enemyTexture = FXGL.texture(enemy.getImg(), enemy.getWidth(), enemy.getHeight());
        enemyTexture.setTranslateX(TARGET_X_MOVE + 215 * boxNum + enemy.getX());
        enemyTexture.setTranslateY(TARGET_Y_MOVE + enemy.getY());
        entity.getViewComponent().addChild(enemyTexture);
    }

    //受伤动画
    public void hurt(int value) {
        //正在受伤
        isAction = true;
        //受伤数字显示
        Text hurtValue = new Text("- " + value);
        hurtValue.setFill(Color.RED);
        hurtValue.setFont(new Font("华文琥珀", 50));
        //受伤人物显示
        Texture hurtEnemy = FXGL.texture(enemy.getImg(), enemy.getWidth(), enemy.getHeight()).multiplyColor(Color.BLACK);
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
            isAction = false;
            if (!isExist())
                addEnemy();
        });
        entity.getViewComponent().clearChildren();
        addOther();
        pt.play();
    }

    //单段攻击动画
    public void attack(int value) {
        //正在攻击
        isAction = true;

        //攻击人物显示
        Texture attackEnemy = FXGL.texture(enemy.getImg(), enemy.getWidth(), enemy.getHeight());
        Entity attack = FXGL.entityBuilder().view(attackEnemy).buildAndAttach();

        //令人物显示移动
        TranslateTransition tHP = new TranslateTransition(Duration.seconds(0.3), attackEnemy);
        tHP.setFromX(TARGET_X_MOVE + 215 * boxNum + enemy.getX());
        tHP.setFromY(TARGET_Y_MOVE + enemy.getY());
        tHP.setToX(TARGET_X_MOVE + 215 * boxNum + enemy.getX() - 100);
        tHP.setToY(TARGET_Y_MOVE + enemy.getY());

        tHP.setOnFinished(e -> {
            //移除该动画实体
//            attack.removeFromWorld();
//            isAction = false;
//            if (!isExist())
//                addEnemy();
//            //角色受伤
//            PlayerInformation.player.physicalHurt(value);
            attack(attack, value);
        });
        entity.getViewComponent().clearChildren();
        addOther();
        tHP.play();
    }

    //单段攻击动画2
    private void attack(Entity attack, int value) {
        //令人物返回
        TranslateTransition tHP = new TranslateTransition(Duration.seconds(0.3), attack.getViewComponent().getChildren().get(0));
        tHP.setFromX(TARGET_X_MOVE + 215 * boxNum + enemy.getX() - 100);
        tHP.setFromY(TARGET_Y_MOVE + enemy.getY());
        tHP.setToX(TARGET_X_MOVE + 215 * boxNum + enemy.getX());
        tHP.setToY(TARGET_Y_MOVE + enemy.getY());
        tHP.setOnFinished(e -> {
            attack.removeFromWorld();
            isAction = false;
            if (!isExist())
                addEnemy();
            //角色受伤
            PlayerInformation.player.physicalHurt(value);
            //添加回合结束效果
//            BattleInformation.EFFECTS.add(new ActionOver(enemy, enemy, 1));
            //回合结束触发
//            ActionOverComponent.nextStage = true;
            //回合结束

            ActionOverComponent.actionOver(enemy);
        });
        tHP.play();
    }

    //护甲生成动画
    public void addArmor() {
        //创建动画实体
        Texture armorTexture = FXGL.texture("effect/armor.png", enemy.getWidth() + 50, enemy.getWidth() + 50);
        armorTexture.setTranslateX(TARGET_X_MOVE + 215 * boxNum + enemy.getX() - 25);
        armorTexture.setTranslateY(TARGET_Y_MOVE + enemy.getY() - 25);
        Entity armor = FXGL.entityBuilder().view(armorTexture).buildAndAttach();
        //透明动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), armorTexture);
        ft.setToValue(0);

        //移动动画
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), armorTexture);
        tt.setFromY(TARGET_Y_MOVE + enemy.getY() - 25);
        tt.setToY(TARGET_Y_MOVE + enemy.getY() + 25);

        ParallelTransition pt = new ParallelTransition(ft, tt);
        pt.setOnFinished(e -> {
            armor.removeFromWorld();
            updateArmor();
        });
        pt.play();
    }

    //更新护甲
    public void updateArmor() {
        entity.getViewComponent().clearChildren();
        addOther();
        addEnemy();

        Texture armor = FXGL.texture("effect/armorBlood.png", 40, 40);
        armor.setTranslateX(TARGET_X_MOVE + 215 * boxNum + 20 + 5);
        armor.setTranslateY(TARGET_Y_MOVE + 160);
        entity.getViewComponent().addChild(armor);

        Rectangle textBar = new Rectangle(40, 40, Color.rgb(0, 0, 0, 0));
        Text text = new Text(String.valueOf(enemy.getArmor()));
        text.setFont(new Font("微软雅黑", 15));
        StackPane stackPane = new StackPane(textBar);
        stackPane.getChildren().add(text);
        stackPane.setTranslateX(TARGET_X_MOVE + 215 * boxNum + 20 + 5);
        stackPane.setTranslateY(TARGET_Y_MOVE + 160);
        //pane.getChildren().add(stackPane);
        entity.getViewComponent().addChild(stackPane);


        Rectangle rectangle = new Rectangle(120, 11, Color.valueOf("#a7fefeB3"));
        rectangle.setTranslateX(TARGET_X_MOVE + 215 * boxNum + 20 + 5 + 40);
        rectangle.setTranslateY(TARGET_Y_MOVE + 180);
        entity.getViewComponent().addChild(rectangle);
    }

    //死亡动画
    private void deathAnimation() {

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
            if (e.getComponent(EnemyComponent.class).isSelected) {
                e.getComponent(EnemyComponent.class).update(false);
            }
        }
        TargetComponent.target = enemy;
        BattleEntities.target.getComponent(TargetComponent.class).update();
        this.update(true);
    }

    //是否被选择
    public void update(boolean isSelected) {
        this.isSelected = isSelected;
        entity.getViewComponent().clearChildren();
        addOther();
        addEnemy();
    }

    //判断实体是否被添加
    public boolean isExist() {
        for (Node child : entity.getViewComponent().getChildren()) {
            if (child == enemyTexture) return true;
        }
        return false;
    }
}
