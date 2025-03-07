package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.PauseEffect;
import javafx.animation.TranslateTransition;
import javafx.scene.text.Text;

import java.util.Objects;

public class ConditionEffect extends Effect {
    //执行效果的前置条件
    //1.目标无护盾
    private final int condition;
    //要执行的效果
    private final Effect effect;


    public ConditionEffect(Role from, Role to, int value, int condition, Effect effect) {
        super(from, to, value);
        this.condition = condition;
        this.effect = effect;
    }

    @Override
    public void action() {
        if (super.getTo() == null) return;
        if (condition == 1 && super.getTo().getRoleArmor() == 0) letEffectAction();
    }

    @Override
    public String describeInDetail() {
        String front = "";
        if (condition == 1) front = "条件1：若目标无护盾";
        return Objects.equals(effect.describeInDetail(), "") ? front + Effect.NEW_LINE : front + "\n" + effect.describeInDetail();
    }

    @Override
    public String toString() {
        return "条件" + condition + "，额外" + effect.toString();
    }

    private void letEffectAction() {
        //暂停0.3秒，再执行效果
        BattleInformation.insetEffect(effect);
        BattleInformation.insetEffect(new PauseEffect(null, null, 0, 0.3));
    }
}
