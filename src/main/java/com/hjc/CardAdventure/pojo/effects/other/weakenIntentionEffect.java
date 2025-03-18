package com.hjc.CardAdventure.pojo.effects.other;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.PauseEffect;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.util.EffectUtil;

public class weakenIntentionEffect extends Effect {
    public weakenIntentionEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        EffectUtil.weakenIntention((Enemy) getFrom());
        //BattleInformation.insetEffect(new PauseEffect(getFrom(), getTo(), getValue(), EffectUtil.WEAKEN_INTENTION - 2));
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "";
    }
}
