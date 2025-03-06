package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.util.AttributeUtil;

public class PhysicalDamageAll extends Effect {
    public PhysicalDamageAll(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getFrom() == null) return;
        //对所有敌人造成物理伤害
        int damage = AttributeUtil.mathPhysicalDamage(new PhysicalDamage(getFrom(), new Enemy(), getValue()));
        for (Enemy enemy : BattleInformation.ENEMIES) {
            getFrom().physicalAttack(enemy, damage);
        }
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "对所有敌人造成" + AttributeUtil.mathPhysicalDamage(new PhysicalDamage(getFrom(), new Enemy(), getValue())) + "点伤害";
    }
}
