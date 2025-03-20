package com.hjc.CardAdventure.util;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.IntentionType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_HEIGHT;
import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_WIDTH;

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

        }
        //防御类
        else if (enemy.getNowIntention().getIntentionType() == IntentionType.DEFENSE) {
            Texture armorTexture = FXGL.texture("intention/defense.png", 40, 40);
            armorTexture.setTranslateX(x - 20);
            armorTexture.setTranslateY(y - 50);
            entity.getViewComponent().addChild(armorTexture);
        }
        //强化类
        else if (enemy.getNowIntention().getIntentionType() == IntentionType.STRENGTHEN) {
            Texture upTexture = FXGL.texture("intention/up.png", 40, 40);
            upTexture.setTranslateX(x - 20);
            upTexture.setTranslateY(y - 50);
            entity.getViewComponent().addChild(upTexture);
        }
        //弱化类
        else if (enemy.getNowIntention().getIntentionType() == IntentionType.WEAKEN) {
            Texture weakenTexture = FXGL.texture("intention/weaken.jpg",40,40);
            weakenTexture.setTranslateX(x - 20);
            weakenTexture.setTranslateY(y - 50);
            entity.getViewComponent().addChild(weakenTexture);
        }
        //攻击弱化类
        else if (enemy.getNowIntention().getIntentionType() == IntentionType.ATTACK_WEAKEN) {
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

    //生成一张卡牌
    public static Pane generateCard(Card card, double x, double y) {
        Pane pane = new Pane();
        pane.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        pane.setTranslateX(x);
        pane.setTranslateY(y);
        //制作卡牌（矩形
        Rectangle cardBack = new Rectangle(CARD_WIDTH, CARD_HEIGHT, Color.valueOf(card.getColorS()));
        pane.getChildren().add(cardBack);

        //卡牌品质框
        Rectangle cardQuality = new Rectangle(CARD_WIDTH, CARD_HEIGHT / 5, card.getCardQuality().getColor());
        //卡牌名文本
        Text cardName = new Text(card.getCardName());
        cardName.setFill(Color.BLACK);
        cardName.setFont(new Font("华文行楷", 20));
        //卡牌品质框与卡牌名居中
        StackPane stackPane = new StackPane(cardQuality);
        stackPane.getChildren().add(cardName);
        pane.getChildren().add(stackPane);

        //卡牌描述
        //背景框
        StackPane cardDescriptionBack = new StackPane(new Rectangle(CARD_WIDTH - 10, CARD_HEIGHT / 2 - 5, Color.valueOf("#696969")));
        cardDescriptionBack.setTranslateX(5);
        cardDescriptionBack.setTranslateY(CARD_HEIGHT / 2);
        pane.getChildren().add(cardDescriptionBack);

        Label cardDescription = new Label(Effects.CardEffectsToString(card));
        //卡牌描述字体设置
        cardDescription.setFont(new Font("微软雅黑", 12));
        //卡牌描述大小设置
        cardDescription.setMaxSize(CARD_WIDTH - 10, CARD_HEIGHT / 2 - 5);
        //卡牌描述背景，字体颜色
        cardDescription.setStyle("-fx-background-color: #696969;-fx-text-fill: WHITE");
        //卡牌描述自动换行
        cardDescription.setWrapText(true);
        cardDescription.setTranslateX(5);
        cardDescription.setTranslateY(CARD_HEIGHT / 2);
        pane.getChildren().add(cardDescription);

        //卡牌属性要求打印
        Label cardAttribute = new Label(card.getAttribute().displayAttribute());
        cardAttribute.setFont(new Font("华文仿宋", 11.3));
        cardAttribute.setTextFill(Color.WHITE);
        cardAttribute.setTranslateX(5);
        cardAttribute.setTranslateY(CARD_HEIGHT / 5);
        cardAttribute.setMaxSize(CARD_WIDTH - 10, CARD_HEIGHT * 3 / 10);
        pane.getChildren().add(cardAttribute);

        return pane;
    }
}
