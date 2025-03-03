package com.hjc.CardAdventure.pojo.player;

import com.hjc.CardAdventure.components.BloodComponent;
import com.hjc.CardAdventure.components.battle.AbandonComponent;
import com.hjc.CardAdventure.components.battle.ActionOverComponent;
import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.components.role.PlayerComponent;
import com.hjc.CardAdventure.pojo.*;
import com.hjc.CardAdventure.pojo.effects.DrawEffect;
import com.hjc.CardAdventure.util.AttributeUtil;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Role {
    //名字
    private String name;
    //生命
    private int blood;
    //最大生命值
    private int maxBlood;
    //属性
    private Attribute attribute;
    //角色类型
    private PlayerType playerType;
    //角色颜色
    private String colorS;
    //角色图片
    private String img;
    //角色图片宽度
    private double width;
    //角色图片高度
    private double height;
    //角色图片x偏移量
    private double x;
    //角色图片y偏移量
    private double y;


    //角色行动
    @Override
    public void action() {
        //回合开始阶段
        ActionOverComponent.isPlayer = true;

        //抽牌阶段
        int drawNum = AttributeUtil.drawNum();
        DrawEffect drawEffect = new DrawEffect(this, this, drawNum);
        BattleInformation.EFFECTS.add(drawEffect);
        //BattleInformation.effectExecution();

        //出牌阶段
//        while (ActionOverComponent.isPlayer) {
//            Scanner sc = new Scanner(System.in);
//            sc.next();
//        }

        //可选择手牌
        CardComponent.selectable = true;
    }

    @Override
    public String getRoleName() {
        return getName();
    }

    @Override
    public Attribute getRoleAttribute() {
        return getAttribute();
    }

    @Override
    public void physicalHurt(int value) {
        //伤害减护甲
        value = value - PlayerInformation.playerArmor;

        if (value > 0) {
            PlayerInformation.playerArmor = 0;
            //血量减少
            this.blood = this.blood - value;
            if (this.blood < 0) this.blood = 0;
            //实体刷新
            //受伤动画
            BattleEntities.playerBattle.getComponent(PlayerComponent.class).hurt(value);
            Entities.playerBlood.getComponent(BloodComponent.class).update();
        } else {
            PlayerInformation.playerArmor = value * (-1);
        }
    }

    @Override
    public void physicalAttack(Role target, int value) {
        target.physicalHurt(value);
    }

    //回复血量
    @Override
    public void restoreBlood(int value) {
        this.blood = this.blood + value;
        if (this.blood > maxBlood) this.blood = this.maxBlood;
        Entities.playerBlood.getComponent(BloodComponent.class).update();
        if (BattleEntities.playerBattle != null) {
            BattleEntities.playerBattle.getComponent(PlayerComponent.class).restoreBlood(value);
        }
    }

    @Override
    public void addArmor(int value) {
        PlayerInformation.playerArmor += value;
        BattleEntities.playerBattle.getComponent(PlayerComponent.class).addArmor();
    }

    //弃牌阶段
    public void abandon() {
        //可保留的牌的数量
        int reserveNum = attribute.getAgility() / 5;
        //需要弃牌数
        AbandonComponent.needAbandon = CardComponent.HAND_CARDS.size() - reserveNum;
        //执行弃牌
        BattleEntities.abandon.getComponent(AbandonComponent.class).abandonJudge();
    }
}
