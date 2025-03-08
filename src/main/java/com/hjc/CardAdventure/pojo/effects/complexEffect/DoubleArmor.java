package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.ArmorAdd;
import com.hjc.CardAdventure.pojo.effects.Effect;

public class DoubleArmor extends Effect {
    public DoubleArmor(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        BattleInformation.insetEffect(new ArmorAdd(getFrom(), getTo(), getTo().getRoleArmor()));
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "使目标护盾翻倍";
    }
}
