package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

//意图移除效果，移除当前怪物意图
public class IntentionClear extends Effect {

    public IntentionClear(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        //获取敌人意图，使该意图连续使用次数+1，将其置为null
        Enemy enemy = (Enemy) super.getTo();
        enemy.getNowIntention().setConsecutiveStarts(enemy.getNowIntention().getConsecutiveStarts() + 1);
        enemy.setNowIntention(null);
    }

    @Override
    public String describeInDetail() {
        return "中断：中断目标意图" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "中断";
    }
}
