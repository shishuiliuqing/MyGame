package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.effects.AttributeUpEffect;
import com.hjc.CardAdventure.pojo.effects.Effect;

public class DoubleAttribute extends Effect {
    private final AttributeUp attributeUp;

    public DoubleAttribute(Role from, Role to, int value, AttributeUp attributeUp) {
        super(from, to, value);
        this.attributeUp = attributeUp;
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        int value;
        Attribute attribute = getTo().getRoleAttribute();
        if (attributeUp == AttributeUp.POWER_UP) {
            value = attribute.getPower();
        } else if (attributeUp == AttributeUp.INTELLIGENCE_UP) {
            value = attribute.getIntelligence();
        } else if (attributeUp == AttributeUp.DEFENSE_UP) {
            value = attribute.getDefense();
        } else if (attributeUp == AttributeUp.AGILITY_UP) {
            value = attribute.getAgility();
        } else if (attributeUp == AttributeUp.PURITY_UP) {
            value = attribute.getPurity();
        } else {
            value = attribute.getSpeed();
        }
        BattleInformation.insetEffect(new AttributeUpEffect(getFrom(), getTo(), value, attributeUp));
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "使你的" + switch (attributeUp) {
            case AGILITY_UP -> "敏捷";
            case INTELLIGENCE_UP -> "智力";
            case DEFENSE_UP -> "防御";
            case PURITY_UP -> "纯洁";
            case SPEED_UP -> "速度";
            case POWER_UP -> "力量";
        } + "翻倍";
    }
}
