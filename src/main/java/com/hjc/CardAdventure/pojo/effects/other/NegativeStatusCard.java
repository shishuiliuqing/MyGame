package com.hjc.CardAdventure.pojo.effects.other;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.util.EffectUtil;
import com.hjc.CardAdventure.util.OutUtil;

import java.util.ArrayList;

public class NegativeStatusCard extends Effect {
    public static final String STATUS_ADDRESS = "data/card/status/";
    public static final String[] STATUS_NAME = {"nauseated.json"};

    public NegativeStatusCard(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        ArrayList<Card> cards;
        //是0进抽牌堆
        if (getValue() % 10 == 0) cards = BattleInformation.DRAW_CARDS;
            //是1进弃牌堆
        else cards = BattleInformation.ABANDON_CARDS;

        int num = (getValue() % 100) / 10;
        int index = getValue() / 100;
        ArrayList<Card> statusCards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            //获得状态牌
            Card card = FXGL.getAssetLoader().loadJSON(STATUS_ADDRESS + STATUS_NAME[index], Card.class).get();
            statusCards.add(card);
        }

        //生成特效
        EffectUtil.cardsAddStatus(statusCards, cards);
        //状态牌进入牌堆
        cards.addAll(statusCards);
        OutUtil.disruptCards(cards);
    }

    @Override
    public String describeInDetail() {
        String s = "";
        if (getValue() / 100 == 0) {
            s += "<恶心>：状态牌，无效果，消耗" + Effect.NEW_LINE;
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        if (getValue() % 10 == 0) {
            s += "向抽牌堆添加";
        } else {
            s += "向弃牌堆添加";
        }

        s += (getValue() % 100 / 10) + "张";

        if (getValue() / 100 == 0) {
            s += "<恶心>";
        }

        return s;
    }
}
