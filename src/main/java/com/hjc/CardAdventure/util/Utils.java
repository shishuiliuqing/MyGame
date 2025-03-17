package com.hjc.CardAdventure.util;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.IntentionType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Utils {
    private Utils() {
    }

    //创建圆心数字
    public static StackPane generateCircleNum(double x, double y, double r, int num, String colorS, Font textFont) {
        //创建一个圆
        Circle circle = new Circle(r, Color.valueOf(colorS));
        //创建数字文本
        Text numText = new Text(String.valueOf(num));
        numText.setFont(textFont);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(circle);
        stackPane.getChildren().add(numText);
        stackPane.setTranslateX(x - r);
        stackPane.setTranslateY(y - r);

        return stackPane;
    }

    //颜色解析
    public static String parseColor(String colorS) {
        if (colorS.equals("#ff0000")) return "Red";
        if (colorS.equals("#76d1ff")) return "Blue";
        return "";
    }

    //意图图标生成器
    public static void intentionImg(Enemy enemy, Entity entity, double x, double y) {
        //System.out.println(enemy.getNowIntention());
        if (enemy.getNowIntention() == null) return;
        //生成攻击意图
        if (enemy.getNowIntention().getIntentionType() == IntentionType.ATTACK) {
            Texture attackTexture = FXGL.texture("intention/attack.png", 40, 40);
            attackTexture.setTranslateX(x - 50);
            attackTexture.setTranslateY(y - 50);
            entity.getViewComponent().addChild(attackTexture);

//            int[] value = IntentionType.getAttackValue(enemy, enemy.getNowIntention().getEffects());
//            String attackValue;
////            value[0] = 20;
////            value[1] = 999;
//            if (value[0] == 0) {
//                attackValue = String.valueOf(value[1]);
//            } else {
//                attackValue = value[1] + "✖" + value[0];
//            }
//            Label label = new Label(attackValue);
//            label.setFont(new Font("微软雅黑", 17));
//            label.setTextFill(Color.RED);
//            label.setTranslateX(x - 10);
//            label.setTranslateY(y - 50);
//            label.setMaxSize(enemy.getWidth() / 2, 40);
//            entity.getViewComponent().addChild(label);
            displayDamage(enemy, entity, x, y);
            //防御意图生成
        } else if (enemy.getNowIntention().getIntentionType() == IntentionType.DEFENSE) {
            Texture armorTexture = FXGL.texture("intention/defense.png", 40, 40);
            armorTexture.setTranslateX(x - 20);
            armorTexture.setTranslateY(y - 50);
            entity.getViewComponent().addChild(armorTexture);
            //强化意图生成
        } else if (enemy.getNowIntention().getIntentionType() == IntentionType.STRENGTHEN) {
            Texture upTexture = FXGL.texture("intention/up.png", 40, 40);
            upTexture.setTranslateX(x - 20);
            upTexture.setTranslateY(y - 50);
            entity.getViewComponent().addChild(upTexture);
            //攻击弱化类
        } else if (enemy.getNowIntention().getIntentionType() == IntentionType.ATTACK_WEAKEN) {
            Texture attackWeaken = FXGL.texture("intention/attackWeaken.png", 40, 40);
            attackWeaken.setTranslateX(x - 50);
            attackWeaken.setTranslateY(y - 50);
            entity.getViewComponent().addChild(attackWeaken);

            displayDamage(enemy, entity, x, y);
        }
    }

    //上海类意图伤害显示
    private static void displayDamage(Enemy enemy, Entity entity, double x, double y) {
        int[] value = IntentionType.getAttackValue(enemy, enemy.getNowIntention().getEffects());
        String attackValue;
//            value[0] = 20;
//            value[1] = 999;
        if (value[0] == 0) {
            attackValue = String.valueOf(value[1]);
        } else {
            attackValue = value[1] + "✖" + value[0];
        }
        Label label = new Label(attackValue);
        label.setFont(new Font("微软雅黑", 17));
        label.setTextFill(Color.RED);
        label.setTranslateX(x - 10);
        label.setTranslateY(y - 50);
        label.setMaxSize(enemy.getWidth() / 2, 40);
        entity.getViewComponent().addChild(label);
    }
}
