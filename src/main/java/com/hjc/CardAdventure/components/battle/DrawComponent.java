package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.entityFactory.CardEntityFactory;
import com.hjc.CardAdventure.entityFactory.ImgEntityFactory;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.util.Utils;

import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.*;
import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;

public class DrawComponent extends Component {

    //要移动到的牌框
    private final int boxNum;
    //当前卡牌
    private final Card card;
    //x需要移动的距离
    private final double xNeedMove;
    //x已移动的距离
    private double xMove = 0;

    public DrawComponent(int boxNum, Card card) {
        super();
        this.boxNum = boxNum;
        this.card = card;
        this.xNeedMove = ImgEntityFactory.CARD_BOX_Y + 10 + ImgEntityFactory.CARD_BOX_X * boxNum;
    }

    @Override
    public void onUpdate(double tpf) {
        //抽牌移动效果
        if (xMove < xNeedMove) {
            entity.translateX(xNeedMove * tpf * 2);
            xMove += xNeedMove * tpf * 2;
        } else {
            entity.removeFromWorld();
        }
    }

    @Override
    public void onAdded() {
        //卡牌宽度
        double cardWith = CardEntityFactory.CARD_X / (PICTURE_X / CARD_BOX_X);
        //卡牌高度
        double cardHeight = CardEntityFactory.CARD_Y / (PICTURE_X / CARD_BOX_X);
        //创建卡牌背面
        Texture drawCards = FXGL.texture("cardBack/cardBack" + Utils.parseColor(player.getColorS()) + ".png", cardWith, cardHeight);
//        Texture drawCards1 = drawCards.multiplyColor(Color.RED);
        drawCards.setTranslateX(CardEntityFactory.CARD_MOVE_X / (PICTURE_X / CARD_BOX_X) + 1);
        drawCards.setTranslateY(CardAdventureApp.APP_HEIGHT - CARD_BOX_Y + CardEntityFactory.CARD_MOVE_Y / (PICTURE_X / CARD_BOX_X));

        entity.getViewComponent().addChild(drawCards);
    }

    @Override
    public void onRemoved() {
        FXGL.spawn("card", new SpawnData()
                .put("boxNum", boxNum)
                .put("card", card)
        );
    }

}
