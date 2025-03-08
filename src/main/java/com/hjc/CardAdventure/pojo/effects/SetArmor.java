package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;

public class SetArmor extends Effect {
    public SetArmor(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        getTo().setRoleArmor(getValue());
    }

    @Override
    public String describeInDetail() {
        if (getValue() == 0) return "";
        return "御甲" + getValue() + "：使目标的护盾变为" + getValue() + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        if (getValue() == 0) return "失去所有护盾";
        return "御甲" + getValue();
    }
}
