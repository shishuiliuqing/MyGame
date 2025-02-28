package com.hjc.CardAdventure.components.role;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;

//人物组件创建
public class PlayerComponent extends Component {

    private static boolean isSelected = false;
    //加载人物图片
    private final Texture playerImg = FXGL.texture(player.getImg(), player.getWidth(), player.getHeight());

    @Override
    public void onAdded() {
        //添加非角色组件
        addOther();
        //添加角色
        addPlayer();
    }

    //更新人物角色
    public void update(boolean isSelected) {
        PlayerComponent.isSelected = isSelected;
        entity.getViewComponent().clearChildren();
        onAdded();
    }

    //受伤动画
    public void hurt(int value) {
        //受伤数字显示
        Text hurtValue = new Text("- " + value);
        hurtValue.setFill(Color.RED);
        hurtValue.setFont(new Font("华文琥珀", 50));
        //受伤人物显示
        Texture hurtPlayer = playerImg.multiplyColor(Color.BLACK);
        Entity hurt = FXGL.entityBuilder().view(hurtValue).view(hurtPlayer).buildAndAttach();

        //令文本移动
        TranslateTransition tNum = new TranslateTransition(Duration.seconds(0.3), hurtValue);
        tNum.setFromX(600);
        tNum.setFromY(350);
        tNum.setToX(600);
        tNum.setToY(250);
        //令人物显示移动
        TranslateTransition tHP = new TranslateTransition(Duration.seconds(0.3), hurtPlayer);
        tHP.setFromX(player.getX());
        tHP.setFromY(player.getY());
        tHP.setToX(player.getX() - 50);
        tHP.setToY(player.getY());

        ParallelTransition pt = new ParallelTransition(tNum, tHP);
        pt.setOnFinished(e -> {
            hurt.removeFromWorld();
            if (!isExist())
                entity.getViewComponent().addChild(playerImg);
        });
        entity.getViewComponent().clearChildren();
        addOther();
        pt.play();
    }

    //添加非人物对象
    private void addOther() {
        //加载指定图标
        //指定图标
        Texture targetPicture = FXGL.texture(isSelected ? "targetLight.png" : "targetDark.png", 250, 250);
        targetPicture.setTranslateX(450);
        targetPicture.setTranslateY(300);
        entity.getViewComponent().addChild(targetPicture);

        //血条框制作
        double bloodBoxLen = 180.0;
        double bloodBoxHei = 11.0;
        //血条框
        Rectangle bloodBox = new Rectangle(bloodBoxLen, bloodBoxHei, Color.BLACK);
        bloodBox.setTranslateX(395 + bloodBoxLen / 2);
        bloodBox.setTranslateY(480);
        entity.getViewComponent().addChild(bloodBox);

        //血条制作
        double p = player.getBlood() * 1.0 / player.getMaxBlood();
        //血条
        Rectangle blood = new Rectangle(bloodBoxLen * p, bloodBoxHei - 2, Color.RED);
        blood.setTranslateX(395 + bloodBoxLen / 2);
        blood.setTranslateY(481);
        entity.getViewComponent().addChild(blood);

        //血量数字显示
        Text bloodValue = new Text(player.getBlood() + "/" + player.getMaxBlood());
        bloodValue.setFont(new Font("华文琥珀", 20));
        bloodValue.setFill(Color.WHITE);

        Rectangle rectangle = new Rectangle(bloodBoxLen, bloodBoxHei, Color.valueOf("#FF99CC00"));
        //血量数字展示
        StackPane stackPane = new StackPane(rectangle);
        stackPane.getChildren().add(bloodValue);
        //stackPane.setMaxSize(bloodBoxLen, bloodBoxHei);
        stackPane.setTranslateX(395 + bloodBoxLen / 2);
        stackPane.setTranslateY(478);
        entity.getViewComponent().addChild(stackPane);
    }

    //添加人物
    private void addPlayer() {
        //人物显示
        playerImg.setTranslateX(player.getX());
        playerImg.setTranslateY(player.getY());
        entity.getViewComponent().addChild(playerImg);
    }


    //判断人物图片是否存在
    private boolean isExist() {
        for (Node child : entity.getViewComponent().getChildren()) {
            if (child == playerImg) return true;
        }
        return false;
    }
}
