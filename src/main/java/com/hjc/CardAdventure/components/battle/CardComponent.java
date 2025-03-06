package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.util.OutUtil;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayList;

import static com.hjc.CardAdventure.entityFactory.CardEntityFactory.*;
import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.*;

@Getter
public class CardComponent extends Component {
    //被选择时移动的距离
    private static final double Y_MOVE_SELECTED = 50;
    //当前手牌
    public static final ArrayList<CardComponent> HAND_CARDS = new ArrayList<>();
    //当前被选择的卡
    public static final ArrayList<CardComponent> CARD_COMPONENTS = new ArrayList<>();
    //当前状态是否为弃牌状态
    public static boolean isAbandon = false;
    //当前状态是否可以选择卡牌
    public static boolean selectable = false;
    //当前行动卡牌
    public static CardComponent actionCard;
    //当前卡牌
    private final Card card;
    //卡牌所在位置
    private final int boxNum;

    //卡牌宽
    public static final double CARD_WIDTH = CARD_X / (PICTURE_X / CARD_BOX_X);
    //卡牌高
    public static final double CARD_HEIGHT = CARD_Y / (PICTURE_X / CARD_BOX_X);
    //相对牌框x偏移
    private final double X_TO_BOX_MOVE = CARD_MOVE_X / (PICTURE_X / CARD_BOX_X);
    //相对牌框y偏移
    private final double Y_TO_BOX_MOVE = CARD_MOVE_Y / (PICTURE_X / CARD_BOX_X);
    //基础x偏移量
    private final double X_MOVE = CARD_BOX_Y + 10;
    //基础y偏移量
    private final double Y_MOVE = CardAdventureApp.APP_HEIGHT - CARD_BOX_Y;


    public CardComponent(int boxNum, Card card) {
        super();
        this.boxNum = boxNum;
        this.card = card;
    }

    @Override
    public void onAdded() {
        HAND_CARDS.add(this);
        //System.out.println(data.get("boxNum").toString());
        //System.out.println(CARD_BOX_X * (int) data.get("boxNum"));
        addUI();

        entity.getViewComponent().addOnClickHandler(e -> select());
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
    }

    private void lookInformation() {
        TipBarComponent.update(this.card.cardToString());
    }

    //卡牌ui添加
    private void addUI() {
        //制作卡牌（矩形
        Rectangle cardBack = new Rectangle(CARD_WIDTH, CARD_HEIGHT, Color.valueOf(this.card.getColorS()));
        cardBack.setTranslateX(X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1);
        cardBack.setTranslateY(Y_MOVE + Y_TO_BOX_MOVE);
        entity.getViewComponent().addChild(cardBack);

        //卡牌品质框
        Rectangle cardQuality = new Rectangle(CARD_WIDTH, CARD_HEIGHT / 5, this.card.getCardQuality().getColor());
        //卡牌名文本
        Text cardName = new Text(this.card.getCardName());
        cardName.setFill(Color.BLACK);
        cardName.setFont(new Font("华文行楷", 20));
        //卡牌品质框与卡牌名居中
        StackPane stackPane = new StackPane(cardQuality);
        stackPane.getChildren().add(cardName);
        stackPane.setTranslateX(X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1);
        stackPane.setTranslateY(Y_MOVE + Y_TO_BOX_MOVE);
        entity.getViewComponent().addChild(stackPane);

        //卡牌描述
        //背景框
        StackPane cardDescriptionBack = new StackPane(new Rectangle(CARD_WIDTH - 10, CARD_HEIGHT / 2 - 5, Color.valueOf("#696969")));
        cardDescriptionBack.setTranslateX(X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1 + 5);
        cardDescriptionBack.setTranslateY(Y_MOVE + Y_TO_BOX_MOVE + CARD_HEIGHT / 2);
        entity.getViewComponent().addChild(cardDescriptionBack);

        Label cardDescription = new Label(Effects.CardEffectsToString(this.card));
        //卡牌描述字体设置
        cardDescription.setFont(new Font("微软雅黑", 14));
        //卡牌描述大小设置
        cardDescription.setMaxSize(CARD_WIDTH - 10, CARD_HEIGHT / 2 - 5);
        //卡牌描述背景，字体颜色
        cardDescription.setStyle("-fx-background-color: #696969;-fx-text-fill: WHITE");
        //卡牌描述自动换行
        cardDescription.setWrapText(true);
        cardDescription.setTranslateX(X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1 + 5);
        cardDescription.setTranslateY(Y_MOVE + Y_TO_BOX_MOVE + CARD_HEIGHT / 2);
        entity.getViewComponent().addChild(cardDescription);

        //卡牌属性要求打印
        Label cardAttribute = new Label(this.card.getAttribute().displayAttribute());
        cardAttribute.setFont(new Font("华文仿宋", 11.3));
        cardAttribute.setTextFill(Color.WHITE);
        cardAttribute.setTranslateX(X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1 + 5);
        cardAttribute.setTranslateY(Y_MOVE + Y_TO_BOX_MOVE + CARD_HEIGHT / 5);
        cardAttribute.setMaxSize(CARD_WIDTH - 10, CARD_HEIGHT * 3 / 10);
        entity.getViewComponent().addChild(cardAttribute);
    }

    //选择卡牌
    public void select() {
        //非选卡状态
        if (!selectable) return;

        //被选择时
        if (isSelected()) {
            //点击后下移
            entity.translateY(Y_MOVE_SELECTED);
            //选择区移除
            CARD_COMPONENTS.remove(this);
            //如果为弃牌状态
            if (isAbandon) {
                //弃牌按钮变暗
                BattleEntities.abandon.getComponent(AbandonComponent.class).resetButton(false);
                //如果为出牌状态
            } else {
                //触发牌的放下效果
                card.putDown();
                //使用按钮变暗
                BattleEntities.produce.getComponent(ProduceComponent.class).resetButton(false);
            }

            return;
        }

        //该牌上移
        entity.translateY(-1 * Y_MOVE_SELECTED);

        //如果是弃牌
        if (isAbandon) {
            //等于弃牌数，将第一张选择的牌下移
            if (CARD_COMPONENTS.size() == AbandonComponent.needAbandon) {
                //牌下移
                CARD_COMPONENTS.get(0).select();
                //添加该牌
                CARD_COMPONENTS.add(this);
                //弃牌键亮起
                BattleEntities.abandon.getComponent(AbandonComponent.class).resetButton(true);
                //结束
                return;
            }


            //添加该牌
            CARD_COMPONENTS.add(this);
            //牌数满足，弃牌键亮起
            if (CARD_COMPONENTS.size() == AbandonComponent.needAbandon) {
                BattleEntities.abandon.getComponent(AbandonComponent.class).resetButton(true);
            }
            return;
        }


        //出牌阶段
        //当前出牌数为0，不允许选择牌
        if (SumCardsComponent.remainingProduce == 0) {
            //该牌强制下移
            entity.translateY(Y_MOVE_SELECTED);
            SumCardsComponent.warn();
            return;
        }

        if (!CARD_COMPONENTS.isEmpty()) {
            CARD_COMPONENTS.get(0).select();
            //CARD_COMPONENTS.remove(0);
            CARD_COMPONENTS.add(this);
            card.isSelected();
            BattleEntities.produce.getComponent(ProduceComponent.class).resetButton(true);
            return;
        }

        //添加此牌
        CARD_COMPONENTS.add(this);
        card.isSelected();
        BattleEntities.produce.getComponent(ProduceComponent.class).resetButton(true);
    }

    //判断当前卡牌是否被选择
    private boolean isSelected() {
        for (CardComponent cardComponent : CARD_COMPONENTS) {
            if (cardComponent == this) return true;
        }
        return false;
    }

    //卡牌效果执行
    public void action() {
        //当前行动卡牌
        CardComponent.actionCard = this;
        //将该牌移至中央
        entity.setX(CardAdventureApp.APP_WITH / 2.0 - (X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1));
        entity.setY(-290);
        //更新抽牌区状态
        DrawCardsComponent.CARD_BOX_STATUS[boxNum - 1] = 0;
        //运行卡牌效果
        card.action();
        //执行卡牌放下效果--目标指定刷新
        card.putDown();
        //可以继续行动
        selectable = true;
    }

    //主动弃牌消失特效1
    public void abandonAnimation1() {
        //手牌区删除此牌
        HAND_CARDS.remove(this);
        entity.removeFromWorld();
        DrawCardsComponent.CARD_BOX_STATUS[boxNum - 1] = 0;
        BattleInformation.ABANDON_CARDS.add(this.card);
        //生成矩形
        Rectangle rectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT, Color.valueOf(this.card.getColorS()));
        rectangle.setTranslateX(X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1);
        rectangle.setTranslateY(Y_MOVE + Y_TO_BOX_MOVE - (isSelected() ? 50 : 0));
        Entity abandon = FXGL.entityBuilder().view(rectangle).buildAndAttach();
        //生成矩形移动动画
        //System.out.println(isSelected());
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(0.2);
        st.setToY(0.2);
        st.setOnFinished(e -> {
            abandon.removeFromWorld();
            abandonAnimation2();
        });
        st.play();
    }

    //主动弃牌消失特效2
    private void abandonAnimation2() {
        //System.out.println(isSelected());
        //生成圆圈实体
        Circle circle = new Circle(20, Color.valueOf(PlayerInformation.player.getColorS()));
        circle.setCenterX(X_MOVE + CARD_BOX_X * boxNum + X_TO_BOX_MOVE + 1 + CARD_WIDTH / 2);
        circle.setCenterY(Y_MOVE + Y_TO_BOX_MOVE - (isSelected() ? 50 : 0) + CARD_HEIGHT / 2);
        Entity abandon = FXGL.entityBuilder().view(circle).buildAndAttach();
        //生成圆圈移动动画
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), circle);
        tt.setToX(1560 - CARD_BOX_X * boxNum);
        tt.setToY(isSelected() ? 50 : 0);
        tt.setOnFinished(e -> {
            abandon.removeFromWorld();
            BattleEntities.abandonCards.getComponent(AbandonCardsComponent.class).update();
        });
        tt.play();
    }

    //牌使用后的消失特效
    public void endUseAnimation(int flag) {
        //手牌区删除此牌
        HAND_CARDS.remove(this);
        //删除该实体
        entity.removeFromWorld();
        //更新抽牌区状态
        //DrawCardsComponent.CARD_BOX_STATUS[boxNum - 1] = 0;

        //flag==0,弃牌区添加此牌
        if (flag == 0) BattleInformation.ABANDON_CARDS.add(this.card);
        else if (flag == 1) {
            //抽牌堆插入此牌
            BattleInformation.DRAW_CARDS.add(card);
            //打乱抽牌堆
            OutUtil.disruptCards(BattleInformation.DRAW_CARDS);
        } else {
            //消耗牌堆插入此牌
            BattleInformation.CONSUME_CARDS.add(card);
        }
        //生成矩形
        Rectangle rectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT, Color.valueOf(this.card.getColorS()));
        rectangle.setTranslateX((CardAdventureApp.APP_WITH - CARD_WIDTH) / 2.0);
        rectangle.setTranslateY(Y_MOVE + Y_TO_BOX_MOVE - 290);
        Entity endUse = FXGL.entityBuilder().view(rectangle).buildAndAttach();
        //生成矩形移动动画
        //System.out.println(isSelected());
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.5), rectangle);
        st.setToX(0.2);
        st.setToY(0.2);
        st.setOnFinished(e -> {
            endUse.removeFromWorld();
            endUseAnimation1(flag);
        });
        st.play();
    }

    //牌使用后的消失特效
    private void endUseAnimation1(int flag) {
        //System.out.println(isSelected());
        //生成圆圈实体
        Circle circle = new Circle(20, Color.valueOf(PlayerInformation.player.getColorS()));
        circle.setCenterX(CardAdventureApp.APP_WITH / 2.0);
        circle.setCenterY(Y_MOVE + Y_TO_BOX_MOVE - 290 + CARD_HEIGHT / 2);
        Entity endUse = FXGL.entityBuilder().view(circle).buildAndAttach();

        double toX = 0;
        double toY = 0;
        if (flag == 0) {
            toX = 885;
            toY = 290;
        } else if (flag == 1) {
            toX = -880;
            toY = 290;
        } else {
            toX = 870;
            toY = 30;
        }
        //生成圆圈移动动画
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), circle);
        tt.setToX(toX);
        tt.setToY(toY);
        tt.setOnFinished(e -> {
            endUse.removeFromWorld();
            if (flag == 0) {
                BattleEntities.abandonCards.getComponent(AbandonCardsComponent.class).update();
            } else if (flag == 1) {
                BattleEntities.drawCards.getComponent(DrawCardsComponent.class).update();
            } else {
                BattleEntities.consumeCards.getComponent(ConsumeCardsComponent.class).update();
            }
        });
        tt.play();
    }

    //卡牌更新
    public void update() {
        entity.getViewComponent().clearChildren();
        addUI();
    }
}
