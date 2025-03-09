package com.hjc.CardAdventure.subScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effects;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_HEIGHT;
import static com.hjc.CardAdventure.components.battle.CardComponent.CARD_WIDTH;

public class LookCardsSubScene extends SubScene {
    //要展示的牌堆
    public static ArrayList<Card> cards;
    //返回键
    private static final Texture back = FXGL.texture("back.png", 200, 200);
    //x偏移量
    private final int X_MOVE = 10;
    //y偏移量
    private final int Y_MOVE = 10;
    //牌堆类型
    public static String cardsType = "你好呀";
    //卡牌描述
    public static final Label LABEL = new Label();

    public LookCardsSubScene() {
        //卡牌描述移动
        LABEL.setFont(new Font("华文行楷", 20));
        LABEL.setTextFill(Color.WHITE);
        LABEL.setTranslateX(1350);
        LABEL.setTranslateY(70);
        LABEL.setWrapText(true);
        LABEL.setMaxSize(450, 800);
        getContentRoot().getChildren().add(LABEL);

        //牌堆类型文本
        Label label = new Label(cardsType);
        label.setWrapText(true);
        label.setMaxSize(200, 910);
        label.setTranslateX(10);
        label.setTranslateY(70);
        label.setFont(new Font("华文行楷", 200));
        getContentRoot().getChildren().add(label);

        //背景设置，不允许操作
        Rectangle rectangle = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT);
        rectangle.setFill(Color.rgb(80, 80, 80, 0.5));
        getContentRoot().getChildren().add(rectangle);

        //牌堆背景
//        Rectangle background = new Rectangle(1000, CardAdventureApp.APP_HEIGHT - 70);
//        background.setFill(Color.rgb(255, 255, 255, 0.5));
//        background.setTranslateX((CardAdventureApp.APP_WITH - 1000) * 1.0 / 2);
//        background.setTranslateY(70);
//        getContentRoot().getChildren().add(background);


        //pane.getChildren().add()
        Pane pane = new Pane();
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setPrefSize(1000, CardAdventureApp.APP_HEIGHT - 70);
        scrollPane.setTranslateX((CardAdventureApp.APP_WITH - 1000) * 1.0 / 2 - 200);
        scrollPane.setTranslateY(70);
        //配置滚动框样式
        scrollPane.getStylesheets().add(getClass().getClassLoader().getResource("scrollPane.css").toExternalForm());
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        getContentRoot().getChildren().add(scrollPane);

        //设置pane的大小
        pane.setPrefSize(1000, 200 * (cards.size() / 5 + 1));
        //添加卡片
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            Pane cardPane = displayCard(card, i);
            pane.getChildren().add(cardPane);
        }

        //返回按钮设置
        back.setTranslateX(1700);
        back.setTranslateY(800);
        back.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            FXGL.getSceneService().popSubScene();
            LABEL.setText("");
        });
        getContentRoot().getChildren().add(back);
    }

    //制作卡牌
    private Pane displayCard(Card card, int n) {
        //制作卡牌（矩形
        Rectangle cardBack = new Rectangle(CARD_WIDTH, CARD_HEIGHT, Color.valueOf(card.getColorS()));
        //cardBack.setTranslateX(10);
        //cardBack.setTranslateY(10);

        //卡牌品质框
        Rectangle cardQuality = new Rectangle(CARD_WIDTH, CARD_HEIGHT / 5, card.getCardQuality().getColor());
        //卡牌名文本
        Text cardName = new Text(card.getCardName());
        cardName.setFill(Color.BLACK);
        cardName.setFont(new Font("华文行楷", 20));
        //卡牌品质框与卡牌名居中
        StackPane stackPane = new StackPane(cardQuality);
        stackPane.getChildren().add(cardName);
        //stackPane.setTranslateX(X_MOVE);
        //stackPane.setTranslateY(Y_MOVE);

        //卡牌描述
        //背景框
        StackPane cardDescriptionBack = new StackPane(new Rectangle(CARD_WIDTH - 10, CARD_HEIGHT / 2 - 5, Color.valueOf("#696969")));
        cardDescriptionBack.setTranslateX(5);
        cardDescriptionBack.setTranslateY(CARD_HEIGHT / 2);

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
        //卡牌属性
        Label cardAttribute = new Label(card.getAttribute().displayAttribute());
        cardAttribute.setFont(new Font("华文仿宋", 11.3));
        cardAttribute.setTextFill(Color.WHITE);
        cardAttribute.setTranslateX(5);
        cardAttribute.setTranslateY(CARD_HEIGHT / 5);
        cardAttribute.setMaxSize(CARD_WIDTH - 10, CARD_HEIGHT * 3 / 10);


        Pane pane = new Pane();
        pane.getChildren().add(cardBack);
        pane.getChildren().add(stackPane);
        pane.getChildren().add(cardDescriptionBack);
        pane.getChildren().add(cardDescription);
        pane.getChildren().add(cardAttribute);
        pane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> describe(card));

        pane.setTranslateX(X_MOVE + (n % 5) * 200);
        pane.setTranslateY(Y_MOVE + (n / 5) * 200);
        return pane;
    }

    //卡牌描述
    private void describe(Card card) {
        LABEL.setText(card.cardToString());
    }
}
