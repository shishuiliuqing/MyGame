package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.pojo.Role;

//使用的牌返回抽牌堆
public class GoDrawCards extends Effect {

    public GoDrawCards(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        CardComponent.actionCard.endUseAnimation(1);
        CardComponent.actionCard = null;
    }

    @Override
    public String describeInDetail() {
        return "回抽：此牌使用结束后返回抽牌堆" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "回抽";
    }
}
