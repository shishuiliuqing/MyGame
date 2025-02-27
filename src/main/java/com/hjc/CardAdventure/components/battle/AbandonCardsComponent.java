package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.entityFactory.CardEntityFactory;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.util.Utils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.*;

public class AbandonCardsComponent extends Component {
    private final int num = BattleInformation.ABANDON_CARDS.size();

//    @Override
//    public String toString() {
//        return super.toString();
//    }

//    @Override
//    public boolean isComponentInjectionRequired() {
//        return super.isComponentInjectionRequired();
//    }

//    @Override
//    public void onRemoved() {
//        super.onRemoved();
//    }

    /**
     * 每帧调用
     *
     * @param tpf
     */
//    @Override
//    public void onUpdate(double tpf) {
//        super.onUpdate(tpf);
//    }

    /**
     * 当组件添加时调用
     */
    @Override
    public void onAdded() {
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

        abandonViewComponent.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
    }

    //    @Override
//    public BooleanProperty pausedProperty() {
//        return super.pausedProperty();
//    }
    //弃牌堆信息
    private void lookInformation() {
        TipBarComponent.update("弃牌堆，牌数：" + this.num);
    }

    //更新弃牌堆数量
    public void update() {
        entity.getViewComponent().getChildren().clear();
        onAdded();
    }
}
