package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.util.AttributeUtil;

public class PhysicalDamageMany extends Effect {
    public PhysicalDamageMany(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        //计算伤害数值
        int value = getValue() / 10 * 100 + 1;
        value = AttributeUtil.mathPhysicalDamage(new PhysicalDamage(getFrom(), getTo(), value));
        //计算伤害次数
        int num = getValue() % 10;
        getFrom().physicalAttack(getTo(), value, num);
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        //计算伤害数值
        int value = getValue() / 10 * 100 + 1;
        value = AttributeUtil.mathMagic(new PhysicalDamage(getFrom(), getTo(), value));
        return "造成" + value + "点伤害" + getValue() % 10 + "次";
    }
}
