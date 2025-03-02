package com.hjc.CardAdventure.components.role;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
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
    private Texture playerImg;
    //人物是否在受伤
    private boolean isAction = false;
    //护盾组件
    private Pane pane;

    @Override
    public void onAdded() {
        //添加非角色组件
        addOther();
        //添加角色
        addPlayer();

        entity.getViewComponent().addOnClickHandler(e -> addArmor());
    }

    //更新人物角色
    public void update(boolean isSelected) {
        PlayerComponent.isSelected = isSelected;
        entity.getViewComponent().clearChildren();
        addOther();
        addPlayer();
    }

    //受伤动画
    public void hurt(int value) {
        //人物正在受伤
        isAction = true;
        //受伤数字显示
        Text hurtValue = new Text("- " + value);
        hurtValue.setFill(Color.RED);
        hurtValue.setFont(new Font("华文琥珀", 50));
        //受伤人物显示
        Texture hurtPlayer = FXGL.texture(player.getImg(), player.getWidth(), player.getHeight()).multiplyColor(Color.BLACK);
        Entity hurt = FXGL.entityBuilder().view(hurtValue).view(hurtPlayer).buildAndAttach();

        //令文本移动
        TranslateTransition tNum = new TranslateTransition(Duration.seconds(0.2), hurtValue);
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
            isAction = false;
            if (!isExist())
                addPlayer();
        });
        entity.getViewComponent().clearChildren();
        addOther();
        pt.play();
    }

    //护甲生成动画
    public void addArmor() {
        //创建动画实体
        Texture armorTexture = FXGL.texture("effect/armor.png", player.getWidth() + 50, player.getWidth() + 50);
        System.out.println(armorTexture);
        armorTexture.setTranslateX(player.getX() - 25);
        armorTexture.setTranslateY(player.getY() - 25);
        Entity armor = FXGL.entityBuilder().view(armorTexture).buildAndAttach();
        //透明动画
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), armorTexture);
        ft.setToValue(0);

        //移动动画
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), armorTexture);
        tt.setFromY(player.getY() - 25);
        tt.setToY(player.getY() + 25);

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
        addPlayer();

        Texture armor = FXGL.texture("effect/armorBlood.png", 40, 40);
        armor.setTranslateX(445);
        armor.setTranslateY(465);
        entity.getViewComponent().addChild(armor);

        Rectangle textBar = new Rectangle(40, 40, Color.rgb(0, 0, 0, 0));
        Text text = new Text(String.valueOf(PlayerInformation.playerArmor));
        text.setFont(new Font("微软雅黑", 15));
        StackPane stackPane = new StackPane(textBar);
        stackPane.getChildren().add(text);
        stackPane.setTranslateX(445);
        stackPane.setTranslateY(465);
        entity.getViewComponent().addChild(stackPane);

        Rectangle rectangle = new Rectangle(185, 11, Color.valueOf("#a7fefeB3"));
        rectangle.setTranslateX(485);
        rectangle.setTranslateY(480);
        entity.getViewComponent().addChild(rectangle);
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
        if (isAction) return;
        //人物显示
        playerImg = FXGL.texture(player.getImg(), player.getWidth(), player.getHeight());
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
