package com.hjc.CardAdventure.subScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.GoldComponent;
import com.hjc.CardAdventure.configuration.EnemyCards;
import com.hjc.CardAdventure.configuration.PlayerCards;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Entities;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.enemy.EnemyType;
import com.hjc.CardAdventure.pojo.environment.Environment;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.util.Utils;
import javafx.animation.*;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_HEIGHT;
import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_WIDTH;
import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.CARD_BOX_X;


public class RewardSubScene extends SubScene {
    //前进键
    private static final Texture forward = FXGL.texture("forward.png", 200, 200);
    //奖励牌框
    private static final Texture box = FXGL.texture("rewardBox.png", 600, 200);
    //战斗奖励
    public static final ArrayList<Integer> REWARD = new ArrayList<>();
    //人物选卡器
    private static final ArrayList<ArrayList<Card>> CARDS = new ArrayList<>();
    //怪物卡牌选择器
    private static final ArrayList<Card> enemyCards = new ArrayList<>();
    //金币
    private static final ArrayList<Integer> GOLDS = new ArrayList<>();
    //文本描述
    private static final Label LABEL = new Label();

    public RewardSubScene() {
        //背景设置，不允许操作
        Rectangle rectangle = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT);
        rectangle.setFill(Color.rgb(80, 80, 80, 0.5));
        getContentRoot().getChildren().add(rectangle);

        //前进按钮设置
        //forward.setScaleX(-1);
        forward.setTranslateX(1700);
        forward.setTranslateY(800);
        forward.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //System.out.println("真的是你呀");
//            Rectangle r = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_WITH, Color.rgb(0, 0, 0, 0));
//            getContentRoot().getChildren().add(r);
//
//            //加载动画
//            Rectangle load = new Rectangle(1, 1);
//            load.setTranslateX((CardAdventureApp.APP_WITH - 1) / 2.0);
//            load.setTranslateY((CardAdventureApp.APP_HEIGHT - 1) / 2.0);
//            getContentRoot().getChildren().add(load);
//            ScaleTransition st = new ScaleTransition(Duration.seconds(1), load);
//            st.setToX(CardAdventureApp.APP_WITH);
//            st.setToY(CardAdventureApp.APP_HEIGHT);
//            st.setOnFinished(event -> {
//                //关闭该场景
//                FXGL.getSceneService().popSubScene();
//                InsideInformation.turnTimeStatus();
//                //移除所有实体
//                ArrayList<Entity> entities = new ArrayList<>(FXGL.getGameWorld().getEntities());
//                FXGL.getGameWorld().removeEntities(entities);
//                //初始化信息栏
//                Entities.initEntities();
//                //初始化营地
//                CampEntities.initCampEntities();
//            });
//            st.play();
            int experience;
            if (BattleInformation.enemyType == EnemyType.LITTLE_MONSTER) {
                experience = InsideInformation.day;
            } else if (BattleInformation.enemyType == EnemyType.ELITE_MONSTER) {
                experience = InsideInformation.day * 2;
            } else {
                experience = 40 * ((InsideInformation.day - 1) % 6) + 1;
            }
            FXGL.getSceneService().popSubScene();
            FXGL.getSceneService().pushSubScene(new ExperienceSubScene(experience, 0));

        });
        getContentRoot().getChildren().add(forward);

        //战斗胜利文本
        Label winText = new Label("战 斗 胜 利 ！");
        winText.setMaxSize(500, 50);
        winText.setFont(new Font("华文行楷", 83));
        winText.setTextFill(Color.YELLOW);
        winText.setTranslateX((CardAdventureApp.APP_WITH - 500) / 2.0);
        winText.setTranslateY(70);
        getContentRoot().getChildren().add(winText);

        //奖励框
        box.setTranslateX((CardAdventureApp.APP_WITH - 600) / 2.0);
        box.setTranslateY(170);
        getContentRoot().getChildren().add(box);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), box);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> {
            //矩形背景框
            Rectangle rBox = new Rectangle(600, 1);
            rBox.setTranslateX((CardAdventureApp.APP_WITH - 600) / 2.0);
            rBox.setTranslateY(250);
            rBox.setFill(Color.rgb(255, 255, 255, 0.9));
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), rBox);
            tt.setFromY(250);
            tt.setToY(600);
            ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rBox);
            st.setToY(700);
            ParallelTransition pt = new ParallelTransition(tt, st);
            pt.setOnFinished(event -> {
                initReward(true);
            });
            getContentRoot().getChildren().add(rBox);
            pt.play();
        });
        ft.play();
    }

    //初始化奖励
    private void initReward(boolean flag) {
        //System.out.println(getContentRoot().getChildren().size());
        //清除奖励框内容
        for (int i = 5; i < getContentRoot().getChildren().size(); ) {
            getContentRoot().getChildren().remove(i);
        }
        //清空选择卡组
        if (flag) {
            CARDS.clear();
            enemyCards.clear();
        }
        //解析奖励数组
        for (int i = 0; i < REWARD.size(); i++) {
            switch (REWARD.get(i)) {
                case 1: {
                    getCardRewardWhite(i);
                    if (flag) {
                        CARDS.add(PlayerCards.rewardCards(2, CardAdventureApp.playerCards));
                        enemyCards.add(EnemyCards.randomEnemyCard(InsideInformation.day));
                    }
                    break;
                }
                case 4: {
                    if (flag) {
                        Random r = new Random();
                        GOLDS.add(r.nextInt(20) + 30);
                    }
                    getGoldLittle(i);
                }
            }
        }
    }

    //获得金币
    private void getGoldLittle(int num) {
        //矩形背景框
        Rectangle rBox = new Rectangle(560, 70);
        rBox.setFill(Color.valueOf("#F18E69"));

        Pane pane = new Pane(rBox);
        pane.setPrefSize(560, 70);
        pane.setTranslateX((CardAdventureApp.APP_WITH - 560) / 2.0);
        pane.setTranslateY(260 + 80 * num);
        getContentRoot().getChildren().add(pane);
        //生成金币数量
        int i = num - enemyCards.size();
        String gold = String.valueOf(GOLDS.get(i));
        Texture goldTexture = FXGL.texture("gold.png", 60, 60);
        goldTexture.setTranslateX(5);
        goldTexture.setTranslateY(5);
        pane.getChildren().add(goldTexture);

        Text goldText = new Text(gold);
        goldText.setFont(new Font("华文行楷", 35));
        goldText.setFill(Color.YELLOW);
        goldText.setTranslateX(80);
        goldText.setTranslateY(45);
        pane.getChildren().add(goldText);

        //添加点击事件
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            PlayerInformation.gold += GOLDS.get(i);
            Entities.gold.getComponent(GoldComponent.class).update();
            GOLDS.remove(i);
            REWARD.remove(num);
            initReward(false);
        });
    }

    //获得白卡选牌
    private void getCardRewardWhite(int num) {
        //矩形背景框
        Rectangle rBox = new Rectangle(560, 70);
        rBox.setFill(Color.valueOf("#F18E69"));

        Pane pane = new Pane(rBox);
        pane.setPrefSize(560, 70);
        pane.setTranslateX((CardAdventureApp.APP_WITH - 560) / 2.0);
        pane.setTranslateY(260 + 80 * num);
        getContentRoot().getChildren().add(pane);
        //生成牌背
        Texture cardBack = FXGL.texture("cardBack/cardBackWhite.png", 40, 60);
        cardBack.setTranslateX(5);
        cardBack.setTranslateY(5);
        pane.getChildren().add(cardBack);
        //getContentRoot().getChildren().add(cardBack);
        //pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> System.out.println("你好"));
        //生成文本
        Text text = new Text("可从多张卡中选择一张加入牌组");
        text.setFont(new Font("华文行楷", 35));
        text.setFill(Color.WHITE);
        text.setTranslateX(55);
        text.setTranslateY(45);
        pane.getChildren().add(text);
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            selectCards(num);
            //卡牌描述移动
            LABEL.setFont(new Font("华文行楷", 20));
            LABEL.setTextFill(Color.WHITE);
            LABEL.setLayoutX(1350);
            LABEL.setLayoutY(70);
            LABEL.setWrapText(true);
            LABEL.setMaxSize(450, 800);
            getContentRoot().getChildren().add(LABEL);
        });
    }

    //选择卡牌
    private void selectCards(int i) {
        //生成一个大矩形框，不允许其他操作
        Rectangle back = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_WITH - 70, Color.rgb(0, 0, 0, 0.8));
        back.setY(70);
        getContentRoot().getChildren().add(back);

        generateCard(CARDS.get(i).get(0), 0, i);
        generateCard(CARDS.get(i).get(1), 1, i);
        generateCard(enemyCards.get(i), 2, i);

        //生成关闭按钮
        Texture close = FXGL.texture("buttonDark.png", 270 / 1.5, 98 / 1.5);
        StackPane stackPane = new StackPane(close.multiplyColor(Color.RED));
        stackPane.setPrefSize(270 / 1.5, 98 / 1.5);
        stackPane.setTranslateX((CardAdventureApp.APP_WITH - 270 / 1.5) / 2.0);
        stackPane.setTranslateY(600);
        Text text = new Text("关闭");
        text.setFill(Color.WHITE);
        text.setFont(new Font("华文行楷", 50));
        stackPane.getChildren().add(text);
        stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> initReward(false));
        getContentRoot().getChildren().add(stackPane);
    }

    //生成一张卡牌
    private void generateCard(Card card, int n, int i) {
        //生成卡片
        Pane pane = Utils.generateCard(card, 600 + 280 * n, 300);
        getContentRoot().getChildren().add(pane);

        //文本描述
        pane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> LABEL.setText(card.cardToString()));
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            PlayerInformation.cards.add(card);
            CARDS.remove(i);
            enemyCards.remove(i);
            REWARD.remove(i);
            initReward(false);
        });
    }
}
