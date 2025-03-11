package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.AttributeDown;
import com.hjc.CardAdventure.pojo.effects.AttributeDownEffect;
import com.hjc.CardAdventure.pojo.effects.Effect;

public class LostAttribute extends Effect {
    private final AttributeDown attributeDown;

    public LostAttribute(Role from, Role to, int value, AttributeDown attributeDown) {
        super(from, to, value);
        this.attributeDown = attributeDown;
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        int value;
        Attribute attribute = getTo().getRoleAttribute();
        switch (attributeDown) {
            case POWER_DOWN -> value = attribute.getPower();
            case INTELLIGENCE_DOWN -> value = attribute.getIntelligence();
            case DEFENSE_DOWN -> value = attribute.getDefense();
            case AGILITY_DOWN -> value = attribute.getAgility();
            case PURITY_DOWN -> value = attribute.getPurity();
            case SPEED_DOWN -> value = attribute.getSpeed();
            default -> value = 0;
        }
        BattleInformation.insetEffect(new AttributeDownEffect(getFrom(), getTo(), value, attributeDown));
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "指定目标失去所有" + switch (attributeDown) {
            case SPEED_DOWN -> "速度";
            case PURITY_DOWN -> "纯洁";
            case AGILITY_DOWN -> "敏捷";
            case DEFENSE_DOWN -> "防御";
            case INTELLIGENCE_DOWN -> "智力";
            case POWER_DOWN -> "力量";
        };
    }
}
