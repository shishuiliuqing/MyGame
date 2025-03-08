package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.SpecialDamageType;

public class FireDamage extends Effect {
    public FireDamage(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        getTo().specialHurt(getValue(), SpecialDamageType.FIRE);
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "造成" + getValue() + "点火焰伤害";
    }
}
