package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.SumCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;

public class ReduceProduce extends Effect {
    public ReduceProduce(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        SumCardsComponent.remainingProduce = SumCardsComponent.remainingProduce - super.getValue();
        if (SumCardsComponent.remainingProduce < 0) SumCardsComponent.remainingProduce = 0;
        BattleEntities.sumProduce.getComponent(SumCardsComponent.class).update();
    }

    @Override
    public String describeInDetail() {
        if (super.getValue() == 1) return "";
        return "沉重：当使用此牌后，将使你本回合的出牌数减少x（x为沉重的数值）" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        if (super.getValue() == 1) return "";
        return "沉重" + super.getValue();
    }
}
