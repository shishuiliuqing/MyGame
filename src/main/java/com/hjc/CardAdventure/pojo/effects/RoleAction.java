package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;

//目标获得一个回合
public class RoleAction extends Effect {

    public RoleAction(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        //无目标，不触发
        if (super.getTo() == null) return;
        super.getTo().action();
    }

    @Override
    public String describeInDetail() {
        return "跃迁：额外获得一个回合" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "跃迁 " + super.getValue();
    }
}
