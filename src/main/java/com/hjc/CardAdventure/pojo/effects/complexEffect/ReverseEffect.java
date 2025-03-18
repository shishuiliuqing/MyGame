package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;

import java.util.Objects;

public class ReverseEffect extends Effect {
    public ReverseEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;
        Effect effect = Effects.getEffect(getValue(), getFrom(), BattleInformation.nowAction);
        BattleInformation.insetEffect(effect);
    }

    @Override
    public String describeInDetail() {
        return Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), BattleInformation.nowAction)).describeInDetail();
    }

    @Override
    public String toString() {
        return "受到伤害时，" + Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), BattleInformation.nowAction)).toString();
    }
}
