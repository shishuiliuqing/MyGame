package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.DrawCardsComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

//抽牌效果
public class DrawEffect extends Effect {
    public DrawEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        for (int i = 0; i < super.getValue(); i++) {
            BattleEntities.drawCards.getComponent(DrawCardsComponent.class).draw();
        }
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "抽" + super.getValue() + "张牌";
    }
}
