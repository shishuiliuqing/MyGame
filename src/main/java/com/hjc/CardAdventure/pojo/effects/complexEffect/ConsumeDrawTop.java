package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.components.battle.ConsumeCardsComponent;
import com.hjc.CardAdventure.components.battle.DrawCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;

public class ConsumeDrawTop extends Effect {
    public ConsumeDrawTop(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        for (int i = 0; i < getValue(); i++) {
            if (!BattleInformation.DRAW_CARDS.isEmpty()) {
                BattleInformation.CONSUME_CARDS.add(BattleInformation.DRAW_CARDS.get(0));
                BattleInformation.DRAW_CARDS.remove(0);
                BattleEntities.consumeCards.getComponent(ConsumeCardsComponent.class).update();
                BattleEntities.drawCards.getComponent(DrawCardsComponent.class).update();
            }
        }
    }

    @Override
    public String describeInDetail() {
        return "弃顶：将抽牌堆顶部的牌置入消耗牌堆";
    }

    @Override
    public String toString() {
        return "弃顶" + getValue();
    }
}
