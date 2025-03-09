package com.hjc.CardAdventure.pojo.effects.complexEffect;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.components.role.EnemyComponent;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.enemy.Enemy;

import java.util.Objects;

public class AllTargetEffect extends Effect {
    public AllTargetEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null) return;

        for (Enemy enemy : BattleInformation.ENEMIES) {
            Effect effect = Effects.getEffect(getValue(), getFrom(), enemy);
            BattleInformation.insetEffect(effect);
        }

        //更新手牌显示
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
        //更新怪物显示
        for (Entity enemy : BattleEntities.enemies) {
            if (enemy == null) continue;
            enemy.getComponent(EnemyComponent.class).update();
        }
    }

    @Override
    public String describeInDetail() {
        return Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), getTo())).describeInDetail();
    }

    @Override
    public String toString() {
        return "群体，" + Objects.requireNonNull(Effects.getEffect(getValue(), getFrom(), getTo()));
    }
}
