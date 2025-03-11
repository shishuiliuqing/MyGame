package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.PauseEffect;
import com.hjc.CardAdventure.util.RecordUtil;
import javafx.animation.TranslateTransition;
import javafx.scene.text.Text;

import java.util.Objects;

public class ConditionEffect extends Effect {
    //执行效果的前置条件
    //1.目标无护盾
    //2.目标力量低于发动者
    //3.目标血量低于记录值
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
        else if (condition == 2 && super.getTo().getRoleAttribute().getPower() < getFrom().getRoleAttribute().getPower())
            letEffectAction();
        else if (condition == 3 && getTo().getRoleBlood() < RecordUtil.getInteger(RecordUtil.EnemyBloodIntegers))
            letEffectAction();
    }

    @Override
    public String describeInDetail() {
        String front = "";
        if (condition == 1) front = "条件1：若目标无护盾";
        else if (condition == 2) front = "条件2：若目标力量低于发动者";
        else if (condition == 3) front = "条件3：若造成伤害";
        return Objects.equals(effect.describeInDetail(), "") ? front + Effect.NEW_LINE : front + "\n" + effect.describeInDetail();
    }

    @Override
    public String toString() {
        return "条件" + condition + "，" + effect.toString();
    }

    private void letEffectAction() {
        //暂停0.3秒，再执行效果
        BattleInformation.insetEffect(effect);
        BattleInformation.insetEffect(new PauseEffect(null, null, 0, 0.3));
    }
}
