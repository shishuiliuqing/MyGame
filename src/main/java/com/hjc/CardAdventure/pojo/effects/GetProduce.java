package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.SumCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;

public class GetProduce extends Effect {
    public GetProduce(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        SumCardsComponent.remainingProduce += getValue();
        BattleEntities.sumProduce.getComponent(SumCardsComponent.class).update();
    }
    @Override
    public String describeInDetail() {
        return "灵敏：获得本回合出牌数" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "灵敏" + getValue();
    }
}
