package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.util.AttributeUtil;

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
        super.getFrom().physicalAttack(super.getTo(), AttributeUtil.mathPhysicalDamage(this), 1);
    }

    @Override
    public String toString() {
        int b = getValue() % 100;
        return "造成" + AttributeUtil.mathPhysicalDamage(this) + "点伤害" + (b == 1 ? "" : "（ " + b + "倍力量加成）");
    }

    @Override
    public String describeInDetail() {
        return "";
    }
}
