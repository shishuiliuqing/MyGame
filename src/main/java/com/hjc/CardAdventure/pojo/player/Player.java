package com.hjc.CardAdventure.pojo.player;

import com.hjc.CardAdventure.components.BloodComponent;
import com.hjc.CardAdventure.components.battle.AbandonComponent;
import com.hjc.CardAdventure.components.battle.ActionOverComponent;
import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.components.role.PlayerComponent;
import com.hjc.CardAdventure.pojo.*;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.effects.DrawEffect;
import com.hjc.CardAdventure.pojo.effects.ShuffleProduce;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.util.AttributeUtil;
import lombok.*;

import java.util.ArrayList;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;


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
    //人物经验
    private int experience;

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
        //刷新本回合出牌数
        BattleInformation.EFFECTS.add(new ShuffleProduce(player, player, 1));
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
            lossBlood();
            //实体刷新
            //受伤动画
            BattleEntities.playerBattle.getComponent(PlayerComponent.class).hurt(value);
            Entities.playerBlood.getComponent(BloodComponent.class).update();
        } else {
            int x = PlayerInformation.playerArmor + value;
            PlayerInformation.playerArmor = value * (-1);
            BattleEntities.playerBattle.getComponent(PlayerComponent.class).reduceArmor(x);
        }
    }

    @Override
    public void specialHurt(int value) {
        this.blood -= value;
        if (this.blood < 0) this.blood = 0;
        lossBlood();
        BattleEntities.playerBattle.getComponent(PlayerComponent.class).hurt(value);
        Entities.playerBlood.getComponent(BloodComponent.class).update();
    }

    //失去生命时机
    private void lossBlood() {
    }

    @Override
    public void physicalAttack(Role target, int value) {
        target.physicalHurt(value);
        //攻击时时机
        Opportunity.launchOpportunity(this, OpportunityType.ATTACK_TIME);
    }

    @Override
    public void die() {

    }

    //回复血量
    @Override
    public void restoreBlood(int value) {
        this.blood = this.blood + value;
        if (this.blood > maxBlood) this.blood = this.maxBlood;
        Entities.playerBlood.getComponent(BloodComponent.class).update();
        if (BattleInformation.isBattle) {
            BattleEntities.playerBattle.getComponent(PlayerComponent.class).restoreBlood(value);
        }
    }

    @Override
    public void addArmor(int value) {
        PlayerInformation.playerArmor += value;
        BattleEntities.playerBattle.getComponent(PlayerComponent.class).addArmor();
    }

    @Override
    public void upAttribute(AttributeUp attributeUp) {
        BattleEntities.playerBattle.getComponent(PlayerComponent.class).attributeUP(attributeUp);
        //更新手牌数值
        for (CardComponent handCard : CardComponent.HAND_CARDS) {
            handCard.update();
        }
    }

    @Override
    public ArrayList<Opportunity> getRoleOpportunities() {
        return PlayerInformation.opportunities;
    }

    @Override
    public ArrayList<OpportunityType> getRoleOpportunityType() {
        return PlayerInformation.opportunityTypes;
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
