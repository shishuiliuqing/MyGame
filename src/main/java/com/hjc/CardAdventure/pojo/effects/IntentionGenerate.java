package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.IntentionGenerateType;

//意图生成
public class IntentionGenerate extends Effect {
    public IntentionGenerate(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        Enemy enemy = (Enemy) super.getTo();
        IntentionGenerateType.generateIntention(enemy);
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "为指定目标生成一个意图";
    }
}
