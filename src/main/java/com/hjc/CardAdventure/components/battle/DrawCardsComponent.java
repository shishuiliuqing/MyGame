package com.hjc.CardAdventure.components.battle;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.TipBarComponent;
import com.hjc.CardAdventure.entityFactory.CardEntityFactory;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.subScene.LookCardsSubScene;
import com.hjc.CardAdventure.util.Utils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import static com.hjc.CardAdventure.entityFactory.ImgEntityFactory.*;

public class DrawCardsComponent extends Component {

    public static final int[] CARD_BOX_STATUS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int num;
    private final String colorS;


    public DrawCardsComponent() {
        super();
        this.colorS = PlayerInformation.player.getColorS();
    }

    @Override
    public void onAdded() {
        addComponent();

        //entity.getViewComponent().addOnClickHandler(e -> draw());

        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> lookInformation());
        entity.getViewComponent().addOnClickHandler(e -> lookDrawCards());
    }

    //添加组件
    private void addComponent() {
        num = BattleInformation.DRAW_CARDS.size();
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
    }

    //查看抽牌堆
    private void lookDrawCards() {
        LookCardsSubScene.cards = BattleInformation.DRAW_CARDS;
        LookCardsSubScene.cardsType = "抽牌区";
        FXGL.getSceneService().pushSubScene(new LookCardsSubScene());
    }

    //抽牌堆信息
    private void lookInformation() {
        TipBarComponent.update("抽牌堆，剩余牌数：" + this.num);
    }


    //发牌
    public int draw() {
        int boxNum = nearEmptyBox();
        if (boxNum == -1) return 0;
        //抽牌堆没牌
        if (BattleInformation.DRAW_CARDS.isEmpty()) {
            return 1;
        }

        //更新抽牌堆
        Card card = BattleInformation.DRAW_CARDS.get(0);
        BattleInformation.DRAW_CARDS.remove(0);
        update();
        FXGL.spawn("draw", new SpawnData()
                .put("boxNum", boxNum)
                .put("card", card)
        );
        CARD_BOX_STATUS[boxNum - 1] = 1;
        return 0;
    }

    //获取最近的空选牌框
    private int nearEmptyBox() {
        for (int i = 0; i < CARD_BOX_STATUS.length; i++) {
            if (CARD_BOX_STATUS[i] == 0) {
                //CARD_BOX_STATUS[i] = 1;
                return i + 1;
            }
        }
        return -1;
    }

    //更新抽牌堆
    public void update() {
        entity.getViewComponent().clearChildren();
        addComponent();
    }
}
