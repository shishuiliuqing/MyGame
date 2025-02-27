package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.pojo.Role;

public class PhysicalDamage extends Effect {

    public PhysicalDamage(Role from, Role to, int value) {
        super(from, to, value);
    }

    public PhysicalDamage() {
        super();
    }

    @Override
    public void action() {
        //没有目标，指定目标
        super.updateTo();
        //目标受到伤害
        super.getTo().physicalHurt(super.getValue());
    }

    @Override
    public String toString() {
        return "造成" + super.getValue() + "点伤害";
    }

    @Override
    public String describeInDetail() {
        return "";
    }
}
