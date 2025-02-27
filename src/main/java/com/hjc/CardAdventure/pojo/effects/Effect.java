package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Effect {

    public static final String NEW_LINE = "\n------------------------------------\n";

    //发动对象
    private Role from;
    //指定对象
    private Role to;
    //数值效果
    private int value;

    //执行效果
    public abstract void action();

    //效果详细描述
    public abstract String describeInDetail();

    @Override
    public abstract String toString();

    //目标更新
    public void updateTo() {
        if (to == null) to = TargetComponent.target;
    }
}
