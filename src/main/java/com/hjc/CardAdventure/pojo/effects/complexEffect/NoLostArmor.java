package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;

public class NoLostArmor extends Effect {
    public NoLostArmor(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        getTo().isLostArmor(false);
    }

    @Override
    public String describeInDetail() {
        return "护盾：可抵挡物理伤害" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "使目标自身回合开始不会失去护盾";
    }
}
