package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.entityFactory.CardEntityFactory;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import com.hjc.CardAdventure.util.Utils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.*;

public class AbandonCardsComponent extends Component {
    private int num;

    public AbandonCardsComponent() {
    }

    /**
     * 当组件添加时调用
     */
    @Override
    public void onAdded() {
        addComponent();

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> lookAbandonCards());
    }

    //添加组件
    private void addComponent() {
        num = BattleInformation.ABANDON_CARDS.size();
        //获取视图组件
        ViewComponent abandonViewComponent = entity.getViewComponent();
        abandonViewComponent.clearChildren();
        //外框偏移量
        double outXMove = CardAdventureApp.APP_WITH - CARD_BOX_X;
        double outYMove = CardAdventureApp.APP_HEIGHT - CARD_BOX_Y;
        //内框偏移量
        double inXMove = outXMove + CardEntityFactory.CARD_MOVE_X / (PICTURE_X / CARD_BOX_X);
        double inYMove = outYMove + CardEntityFactory.CARD_MOVE_Y / (PICTURE_X / CARD_BOX_X);
        //圆心所在位置
        double cXMove = inXMove + CardEntityFactory.CARD_X / (PICTURE_X / CARD_BOX_X) / 2;
        double cYMove = inYMove + CardEntityFactory.CARD_Y / (PICTURE_X / CARD_BOX_X) / 2;

        //外框
        Rectangle outBox = new Rectangle(CARD_BOX_X, CARD_BOX_Y, Color.valueOf(PlayerInformation.player.getColorS()));
        outBox.setX(outXMove);
        outBox.setTranslateY(outYMove);
        //内框
        Rectangle inBox = new Rectangle(CardEntityFactory.CARD_X / (PICTURE_X / CARD_BOX_X), CardEntityFactory.CARD_Y / (PICTURE_X / CARD_BOX_X), Color.GRAY);
        inBox.setTranslateX(inXMove);
        inBox.setTranslateY(inYMove);
        //圆半径
        double r = 25;
        StackPane stackPane = Utils.generateCircleNum(cXMove, cYMove, r, num, PlayerInformation.player.getColorS(), new Font("微软雅黑", 15));
        //装载以上配件
        abandonViewComponent.addChild(outBox);
        abandonViewComponent.addChild(inBox);
        abandonViewComponent.addChild(stackPane);
    }

    //弃牌堆信息
    private void lookInformation() {
        TipBarComponent.update("弃牌堆，牌数：" + this.num);
    }

    //查看弃牌堆
    private void lookAbandonCards() {
        LookCardsSubScene.cards = BattleInformation.ABANDON_CARDS;
        LookCardsSubScene.cardsType = "弃牌区";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }

    //更新弃牌堆数量
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }
}
