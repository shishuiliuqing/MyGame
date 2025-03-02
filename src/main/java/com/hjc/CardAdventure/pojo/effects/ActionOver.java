package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.ActionOverComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.Role;

public class ActionOver extends Effect {
    public ActionOver(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        BattleEntities.actionOver.getComponent(ActionOverComponent.class).over();
    }

    @Override
    public String describeInDetail() {
        return "终止：结束当前回合" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "终止";
    }
}
