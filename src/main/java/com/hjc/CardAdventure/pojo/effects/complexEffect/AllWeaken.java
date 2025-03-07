package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

public class AllWeaken extends Effect {
    public AllWeaken(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        for (Enemy enemy : BattleInformation.ENEMIES) {
            BattleInformation.insetEffect(new WeakenEffect(getFrom(), enemy, getValue()));
        }
    }

    @Override
    public String describeInDetail() {
        return "虚弱：伤害降低25%" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "对所有敌人施加" + getValue() + "层虚弱";
    }
}
