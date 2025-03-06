package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;

public class LostBlood extends Effect {
    public LostBlood(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        getTo().specialHurt(getValue());
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "失去" + getValue() + "点生命";
    }
}
