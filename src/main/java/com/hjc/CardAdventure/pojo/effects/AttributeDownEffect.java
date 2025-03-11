package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.ActionComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.AttributeDown;


public class AttributeDownEffect extends Effect {
    private final AttributeDown attributeDown;

    public AttributeDownEffect(Role from, Role to, int value, AttributeDown attributeDown) {
        super(from, to, value);
        this.attributeDown = attributeDown;
    }

    @Override
    public void action() {
        if (getTo() == null) return;
        Attribute attribute = getTo().getRoleAttribute();
        int value = getValue();
        if (value <= 0) return;
        if (attributeDown == AttributeDown.POWER_DOWN) {
            attribute.setPower(attribute.getPower() - value);
            if (attribute.getPower() < -999) attribute.setPower(-999);
        } else if (attributeDown == AttributeDown.INTELLIGENCE_DOWN) {
            attribute.setIntelligence(attribute.getIntelligence() - value);
            if (attribute.getIntelligence() < -999) attribute.setIntelligence(-999);
        } else if (attributeDown == AttributeDown.DEFENSE_DOWN) {
            attribute.setDefense(attribute.getDefense() - value);
            if (attribute.getDefense() < -999) attribute.setDefense(-999);
        } else if (attributeDown == AttributeDown.AGILITY_DOWN) {
            attribute.setAgility(attribute.getAgility() - value);
            if (attribute.getAgility() < -999) attribute.setAgility(-999);
        } else if (attributeDown == AttributeDown.PURITY_DOWN) {
            attribute.setPurity(attribute.getPurity() - value);
            if (attribute.getPurity() < -999) attribute.setPurity(-999);
        } else {
            attribute.setSpeed(attribute.getSpeed() - value);
            if (attribute.getSpeed() < -999) attribute.setSpeed(-999);
            BattleInformation.sort(BattleInformation.NEXT_ACTION);
            BattleEntities.actionBox.getComponent(ActionComponent.class).update();
        }
        getTo().downAttribute(attributeDown);
    }

    @Override
    public String describeInDetail() {
        return "";
    }

    @Override
    public String toString() {
        return "降低目标" + getValue() + "点" + switch (attributeDown) {
            case POWER_DOWN -> "力量";
            case INTELLIGENCE_DOWN -> "智力";
            case DEFENSE_DOWN -> "防御";
            case AGILITY_DOWN -> "敏捷";
            case PURITY_DOWN -> "纯洁";
            case SPEED_DOWN -> "速度";
        };
    }

    public AttributeDown getAttributeDown() {
        return this.attributeDown;
    }
}
