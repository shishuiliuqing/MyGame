package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;

public class DeathEffect extends Effect {
    public DeathEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        //移除该角色所有信息
        BattleInformation.clearRole(super.getTo());
        super.getTo().die();
    }

    @Override
    public String describeInDetail() {
        return "死亡：让指定目标死亡" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "死亡";
    }
}
