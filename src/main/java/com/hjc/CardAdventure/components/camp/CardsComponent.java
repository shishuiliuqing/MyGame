package com.hjc.CardAdventure.components.camp;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.entityFactory.CardEntityFactory;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import com.hjc.CardAdventure.util.Utils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.*;
import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.CARD_BOX_X;

public class CardsComponent extends Component {
    //牌堆数
    private int num;
    //颜色
    private String colorS;

    @Override
    public void onAdded() {
        num = PlayerInformation.cards.size();
        colorS = PlayerInformation.player.getColorS();
        entity.getViewComponent().clearChildren();

        //卡牌宽度
        double cardWith = CardEntityFactory.CARD_X / (PICTURE_X / CARD_BOX_X);
        //卡牌高度
        double cardHeight = CardEntityFactory.CARD_Y / (PICTURE_X / CARD_BOX_X);

        Texture cardBox = FXGL.texture("cardBox.png", CARD_BOX_X, CARD_BOX_Y);
        cardBox.setTranslateX(0);
        cardBox.setTranslateY(CardAdventureApp.APP_HEIGHT - CARD_BOX_Y);

        Texture drawCards = FXGL.texture("cardBack/cardBack" + Utils.parseColor(this.colorS) + ".png", cardWith, cardHeight);
//        Texture drawCards1 = drawCards.multiplyColor(Color.RED);
        drawCards.setTranslateX(CardEntityFactory.CARD_MOVE_X / (PICTURE_X / CARD_BOX_X) + 1);
        drawCards.setTranslateY(CardAdventureApp.APP_HEIGHT - CARD_BOX_Y + CardEntityFactory.CARD_MOVE_Y / (PICTURE_X / CARD_BOX_X));

        //圆心所在位置
        double cXMove = CardEntityFactory.CARD_MOVE_X / (PICTURE_X / CARD_BOX_X) + CardEntityFactory.CARD_X / (PICTURE_X / CARD_BOX_X) / 2;
        double cYMove = CardAdventureApp.APP_HEIGHT - CARD_BOX_Y + CardEntityFactory.CARD_MOVE_Y / (PICTURE_X / CARD_BOX_X) + CardEntityFactory.CARD_Y / (PICTURE_X / CARD_BOX_X) / 2;
        //圆半径
        double r = 25;
        StackPane stackPane = Utils.generateCircleNum(cXMove, cYMove, r, this.num, this.colorS, new Font("微软雅黑", 15));

        entity.getViewComponent().addChild(cardBox);
        entity.getViewComponent().addChild(drawCards);
        entity.getViewComponent().addChild(stackPane);
        //entity.getViewComponent().addOnClickHandler(e -> draw());

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> lookCards());
    }

    private void lookInformation() {
        TipBarComponent.update("总牌堆，牌数：" + this.num);
    }

    //查看抽牌堆
    private void lookCards() {
        LookCardsSubScene.cards = PlayerInformation.cards;
        LookCardsSubScene.cardsType = "总牌堆";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }
}
