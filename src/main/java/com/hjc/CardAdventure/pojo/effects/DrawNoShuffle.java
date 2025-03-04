package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.DrawCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;

//抽牌效果，不洗牌
public class DrawNoShuffle extends Effect {
    public DrawNoShuffle(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        int num = super.getValue();
        //抽不够，不洗牌
        for (int i = 0; i < num; i++) {
            BattleEntities.drawCards.getComponent(DrawCardsComponent.class).draw();
        }
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "抽" + super.getValue() + "张牌，不洗牌";
    }
}
