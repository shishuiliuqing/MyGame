package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.AttributeDown;
import com.hjc.CardAdventure.pojo.effects.AttributeDownEffect;
import com.hjc.CardAdventure.pojo.effects.Effect;

import java.util.ArrayList;

public class AllAttributeDown extends Effect {
    public AllAttributeDown(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        ArrayList<Effect> effects = new ArrayList<>();
        effects.add(new AttributeDownEffect(getFrom(), getTo(), getValue(), AttributeDown.POWER_DOWN));
        effects.add(new AttributeDownEffect(getFrom(), getTo(), getValue(), AttributeDown.INTELLIGENCE_DOWN));
        effects.add(new AttributeDownEffect(getFrom(), getTo(), getValue(), AttributeDown.DEFENSE_DOWN));
        effects.add(new AttributeDownEffect(getFrom(), getTo(), getValue(), AttributeDown.AGILITY_DOWN));
        effects.add(new AttributeDownEffect(getFrom(), getTo(), getValue(), AttributeDown.PURITY_DOWN));
        effects.add(new AttributeDownEffect(getFrom(), getTo(), getValue(), AttributeDown.SPEED_DOWN));
        BattleInformation.insetEffect(effects);
    }

    @Override
    public String describeInDetail() {
        return "萎靡" + getValue() + "：全属性下降" + getValue() + "点";
    }

    @Override
    public String toString() {
        return "萎靡" + getValue();
    }
}
