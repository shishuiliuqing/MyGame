package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.effects.AttributeUpEffect;
import com.hjc.CardAdventure.pojo.effects.Effect;

import java.util.ArrayList;

public class AllAttributeUp extends Effect {
    public AllAttributeUp(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        ArrayList<Effect> effects = new ArrayList<>();
        effects.add(new AttributeUpEffect(getFrom(), getTo(), getValue(), AttributeUp.POWER_UP));
        effects.add(new AttributeUpEffect(getFrom(), getTo(), getValue(), AttributeUp.INTELLIGENCE_UP));
        effects.add(new AttributeUpEffect(getFrom(), getTo(), getValue(), AttributeUp.DEFENSE_UP));
        effects.add(new AttributeUpEffect(getFrom(), getTo(), getValue(), AttributeUp.AGILITY_UP));
        effects.add(new AttributeUpEffect(getFrom(), getTo(), getValue(), AttributeUp.PURITY_UP));
        effects.add(new AttributeUpEffect(getFrom(), getTo(), getValue(), AttributeUp.SPEED_UP));
        BattleInformation.insetEffect(effects);
    }

    @Override
    public String describeInDetail() {
        return "全开" + getValue() + "：全属性上升" + getValue() + "点" + Effect.NEW_LINE;
    }

    @Override
    public String toString() {
        return "全开" + getValue();
    }
}
