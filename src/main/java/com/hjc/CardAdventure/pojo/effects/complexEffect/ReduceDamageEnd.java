package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.util.RecordUtil;

public class ReduceDamageEnd extends Effect {
    public ReduceDamageEnd(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        for (int i = 0; i < getValue(); i++) {
            RecordUtil.delete(RecordUtil.ReduceDamageIntegers);
        }
    }

    @Override
    public String describeInDetail() {
        return "当前伤害减免：" + RecordUtil.getInteger(RecordUtil.ReduceDamageIntegers) + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "减伤结束";
    }
}
