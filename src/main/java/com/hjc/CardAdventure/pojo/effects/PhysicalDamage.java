package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.pojo.Role;

//物理伤害
public class PhysicalDamage extends Effect {

    public PhysicalDamage(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        //没有目标，指定目标
        super.updateTo();
        //无目标，不触发
        if (super.getTo() == null) return;
        //目标受到伤害
        super.getTo().physicalHurt(super.getValue() + super.getFrom().getRoleAttribute().getPower());
    }

    @Override
    public String toString() {
        return "造成" + (super.getValue() + super.getFrom().getRoleAttribute().getPower()) + "点伤害";
    }

    @Override
    public String describeInDetail() {
        return "";
    }
}
