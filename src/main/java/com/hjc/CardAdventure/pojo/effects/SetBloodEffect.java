package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.SpecialDamageType;

//设置目标血量
public class SetBloodEffect extends Effect {
    public SetBloodEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        getTo().specialHurt(getTo().getRoleBlood() - getValue(), SpecialDamageType.OTHER);
    }

    @Override
    public String describeInDetail() {
        return "御血：失去血量直至血量为" + getValue() + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "御血" + getValue();
    }
}
