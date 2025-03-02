package com.hjc.CardAdventure.pojo.card;

import com.almasb.fxgl.entity.Entity;
import com.hjc.CardAdventure.components.battle.TargetComponent;
import com.hjc.CardAdventure.components.role.EnemyComponent;
import com.hjc.CardAdventure.components.role.PlayerComponent;
import com.hjc.CardAdventure.pojo.Attribute;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.AbandonAction;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    //卡牌编号
    private int cardNum;
    //卡牌名字
    private String cardName;
    //卡牌属性
    private Attribute attribute;
    //卡牌品质
    private CardQuality cardQuality;
    //卡牌颜色
    private String colorS;
    //卡牌效果序列
    private int[] cardEffects;
    //卡牌指定目标类型
    private TargetType targetType;

    //卡牌执行
    public void action() {
        //解析卡牌效果
        ArrayList<Effect> effects = Effects.getCardEffects(this.cardEffects);
        //将所有效果添加至效果序列
        BattleInformation.EFFECTS.addAll(effects);
        //添加使用后弃牌效果
        //BattleInformation.EFFECTS.add(new AbandonAction(PlayerInformation.player, PlayerInformation.player, 0));
        //执行效果序列
        BattleInformation.effectExecution();
    }

    //卡牌详细描述
    public String cardToString() {
        return cardName +
                Effect.NEW_LINE +
                "该卡牌打出需要属性\n" +
                attribute.displayAttribute() +
                Effect.NEW_LINE +
                Effects.CardEffectsToString(this) +
                Effect.NEW_LINE +
                Effects.effectsDetail(this.cardEffects);
    }

    //当卡牌被选择时调用
    public void isSelected() {
        //群体指定
        if (targetType == TargetType.ALL) {
            TargetComponent.isAll = true;
            for (Entity enemy : BattleEntities.enemies) {
                if (enemy == null) continue;
                enemy.getComponent(EnemyComponent.class).update(true);
            }
            //随机指定
        } else if (targetType == TargetType.RANDOMIZED) {
            TargetComponent.isRam = true;
            //自身指定
        } else if (targetType == TargetType.OWN) {
            TargetComponent.target = PlayerInformation.player;
            BattleEntities.playerBattle.getComponent(PlayerComponent.class).update(true);
            //单体指定
        } else {
            TargetComponent.needTarget = true;
            TargetComponent.target = BattleInformation.ENEMIES.get(0);
            BattleEntities.enemies[0].getComponent(EnemyComponent.class).update(true);
        }

        BattleEntities.target.getComponent(TargetComponent.class).update();
    }

    //当卡牌被放下时使用
    public void putDown() {
        //群体指定
        if (targetType == TargetType.ALL) {
            TargetComponent.isAll = false;
            for (Entity enemy : BattleEntities.enemies) {
                if (enemy == null) continue;
                enemy.getComponent(EnemyComponent.class).update(false);
            }
            //随机指定
        } else if (targetType == TargetType.RANDOMIZED) {
            TargetComponent.isRam = false;
            //其他
        } else {
            Role target = TargetComponent.target;
            TargetComponent.needTarget = false;
            if (targetType == TargetType.INDIVIDUAL) {
                int index = BattleInformation.ENEMIES.indexOf(target);
                BattleEntities.enemies[index].getComponent(EnemyComponent.class).update(false);
            } else {
                BattleEntities.playerBattle.getComponent(PlayerComponent.class).update(false);
            }
        }

        TargetComponent.target = null;
        BattleEntities.target.getComponent(TargetComponent.class).update();
    }
}
