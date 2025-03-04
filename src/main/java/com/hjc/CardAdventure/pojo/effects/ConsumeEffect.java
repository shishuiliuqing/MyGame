package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.pojo.Role;

public class ConsumeEffect extends Effect {
    public ConsumeEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        CardComponent.actionCard.endUseAnimation(2);
        CardComponent.actionCard = null;
    }

    @Override
    public String describeInDetail() {
        return "消耗：此牌使用后将进入消耗牌堆（消耗牌堆不参与洗牌）" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "消耗";
    }
}
