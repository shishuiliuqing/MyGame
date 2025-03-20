package com.hjc.CardAdventure.util;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.battle.AbandonCardsComponent;
import com.hjc.CardAdventure.components.battle.DrawCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_HEIGHT;
import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_WIDTH;

//特效生成类
public class EffectUtil {

    private EffectUtil() {
    }

    //获取角色位置
    private static double[] getLocal(Role role) {
        double[] local = new double[2];
        if (role == PlayerInformation.player) {
            local[0] = 450;
            local[1] = 300;
        } else {

            int num = Integer.parseInt(BattleInformation.ROLE_LOCATION.get(role));
            local[0] = 550 + 215 * num;
            local[1] = 300;
        }
        return local;
    }

    //虚弱特效生成
    public static void generateWeaken(Role role) {
        //获取角色位置
        double[] local = getLocal(role);

        Entity entity = FXGL.entityBuilder().buildAndAttach();

        //获取特效图片
        AnimationChannel ac = new AnimationChannel(FXGL.image("effect/weaken.png"), Duration.seconds(0.7), 21);
        AnimatedTexture at = new AnimatedTexture(ac);
        at.setTranslateX(local[0] + 45);
        at.setTranslateY(local[1] - 40);
        at.setScaleX(1.2);
        at.setScaleY(1.2);
        at.setOnCycleFinished(entity::removeFromWorld);
        entity.getViewComponent().addChild(at);

        //生成文字
        Text text = new Text("虚弱效果⬆");
        text.setFill(Color.GREEN);
        text.setFont(new Font("华文行楷", 40));
        entity.getViewComponent().addChild(text);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.7), text);
        tt.setFromX(local[0] + 20);
        tt.setFromY(local[1] + 50);
        tt.setToY(local[1] - 150);
        tt.play();
        at.play();
    }

    public static final int WEAKEN_INTENTION = 4;

    //敌人弱化类意图特效
    public static void weakenIntention(Enemy enemy) {
        //获取敌人位置
        int num = Integer.parseInt(BattleInformation.ROLE_LOCATION.get(enemy));

        Texture texture = FXGL.texture("intention/weaken.jpg", 150, 150);
        texture.setScaleX(0);
        texture.setScaleY(0);
        texture.setTranslateX(550 + 215 * num + 35);
        texture.setTranslateY(300);
        Entity entity = FXGL.entityBuilder().view(texture).buildAndAttach();

        ScaleTransition st = new ScaleTransition(Duration.seconds(WEAKEN_INTENTION / 10.0), texture);
        st.setFromX(0);
        st.setFromY(0);
        st.setToX(2);
        st.setToY(2);


        FadeTransition ft = new FadeTransition(Duration.seconds(WEAKEN_INTENTION / 10.0), texture);
        ft.setFromValue(1);
        ft.setToValue(0);

        ParallelTransition pt = new ParallelTransition(st, ft);
        pt.setOnFinished(e -> {
            entity.removeFromWorld();
        });
        pt.play();
    }

    //牌堆进入状态牌
    public static void cardsAddStatus(ArrayList<Card> statusCards, ArrayList<Card> cards) {
        //判断牌堆位置
        int[] cardsLocal = cardsLocal(cards);

        for (int i = 0; i < statusCards.size(); i++) {
            Card card = statusCards.get(i);
            Entity entity = FXGL.entityBuilder().buildAndAttach();

            double x_move;
            if (i == 0) x_move = -(CARD_WIDTH / 2.0);
            else if (i % 2 != 0) {
                x_move = -(CARD_WIDTH / 2.0) - ((i >> 1) + 1) * (10 + CARD_WIDTH);
            } else {
                x_move = CARD_WIDTH / 2.0 + 10 + ((i >> 1) - 1) * (10 + CARD_WIDTH);
            }
            double x = CardAdventureApp.APP_WITH / 2.0 + x_move;
            double y = CardAdventureApp.APP_HEIGHT/ 2.0;

            //生成卡牌
            Pane pane = Utils.generateCard(card, x, y);
            entity.getViewComponent().addChild(pane);

            //滞留2秒
            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), pane);
            tt1.setOnFinished(e1 -> {
                //缩放动画
                ScaleTransition st = new ScaleTransition(Duration.seconds(0.2), pane);
                st.setToX(0);
                st.setToY(0);
                st.setOnFinished(e2 -> {
                    Circle c = new Circle(20, Color.valueOf(card.getColorS()));
                    c.setCenterX(x + CARD_WIDTH / 2.0);
                    c.setCenterY(y + CARD_HEIGHT / 2.0);
                    entity.getViewComponent().addChild(c);

                    TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.3), c);
                    tt2.setToX(cardsLocal[0] - (x + CARD_WIDTH / 2.0 - 20));
                    tt2.setToY(cardsLocal[1] - (y + CARD_HEIGHT / 2.0 - 20));
                    tt2.setOnFinished(e3 -> {
                        entity.removeFromWorld();
                        if (cards == BattleInformation.DRAW_CARDS) {
                            BattleEntities.drawCards.getComponent(DrawCardsComponent.class).update();
                        } else {
                            BattleEntities.abandonCards.getComponent(AbandonCardsComponent.class).update();
                        }
                    });
                    tt2.play();
                });
                st.play();
            });
            tt1.play();
        }
    }

    private static int[] cardsLocal(ArrayList<Card> cards) {
        int[] cardsLocal = new int[2];
        if (cards == BattleInformation.DRAW_CARDS) {
            cardsLocal[0] = 80;
            cardsLocal[1] = 850;
        } else {
            cardsLocal[0] = CardAdventureApp.APP_WITH - 80;
            cardsLocal[1] = 850;
        }
        return cardsLocal;
    }
}
