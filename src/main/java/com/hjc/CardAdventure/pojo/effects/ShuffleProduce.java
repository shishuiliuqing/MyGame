package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.SumCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;

public class ShuffleProduce extends Effect {
    public ShuffleProduce(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        BattleEntities.sumProduce.getComponent(SumCardsComponent.class).shuffleAndUpdate();
    }

    @Override
    public String describeInDetail() {
        return "逆转：刷新本回合的出牌数" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "逆转";
    }
}
