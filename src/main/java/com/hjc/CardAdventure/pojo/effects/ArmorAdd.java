package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.util.AttributeUtil;

//护盾添加
public class ArmorAdd extends Effect {
    public ArmorAdd(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        super.getTo().addArmor(AttributeUtil.mathArmorAdd(this));
    }

    @Override
    public String describeInDetail() {
        return "护盾：可抵挡物理伤害" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "获得" + AttributeUtil.mathArmorAdd(this) + "点护盾";
    }
}
