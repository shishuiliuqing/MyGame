package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.components.role.EnemyComponent;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.effects.*;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import lombok.*;

import java.util.ArrayList;

//怪物类
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Enemy implements Role {
    //怪物编号
    private int enemyNum;
    //名字
    private String name;
    //生命
    private int blood;
    //最大生命值
    private int maxBlood;
    //属性
    private Attribute attribute;
    //角色类型
    private EnemyType enemyType;
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

    //战斗用属性
    //护甲
    private int armor;
    //敌人意图
    private ArrayList<Intention> intentions;
    //敌人当前意图
    private Intention nowIntention;
    //效果时机序列
    private ArrayList<Opportunity> opportunities = new ArrayList<>();
    //可触发时机序列
    private ArrayList<OpportunityType> opportunityTypes = new ArrayList<>();

    @Override
    public void action() {
        //当前敌人不在场上，直接下一个回合
//        if (getEntityIndex() == -1) {
//            BattleInformation.EFFECTS.add(new ActionOver(this, this, 1));
//            return;
//        }
        //System.out.println(BattleInformation.ENEMIES);
        //无意图，生成下一个意图，回合结束
        if (nowIntention == null) {
            //生成一个意图
            BattleInformation.EFFECTS.add(new IntentionGenerate(this, this, 1));
            //回合结束
            BattleInformation.EFFECTS.add(new ActionOver(this, this, 1));
        } else {
            BattleInformation.EFFECTS.addAll(IntentionType.intentionEffects(this, nowIntention.getEffects()));
        }

        //执行效果
        //BattleInformation.effectExecution();
//        BattleInformation.EFFECTS.add(new ActionOver(this, this, 1));
//        BattleInformation.EFFECTS.add(new DrawEffect(PlayerInformation.player,PlayerInformation.player,1));
        int index = getEntityIndex();
        BattleEntities.enemies[index].getComponent(EnemyComponent.class).end();
    }

    @Override
    public String getRoleName() {
        return getName();
    }

    @Override
    public Attribute getRoleAttribute() {
        return getAttribute();
    }

    //受到物理伤害
    @Override
    public void physicalHurt(int value) {
        int index = getEntityIndex();
        if (index == -1) return;

        //伤害减护甲
        value = value - armor;

        if (value > 0) {
            this.armor = 0;
            //血量减少
            this.blood = this.blood - value;
            if (this.blood < 0) this.blood = 0;
            //实体刷新
            //受伤动画
            BattleEntities.enemies[index].getComponent(EnemyComponent.class).hurt(value);
            //BattleEntities.enemies[index].getComponent(EnemyComponent.class).onOver(false);
            if (this.blood == 0) BattleInformation.insetEffect(new DeathEffect(this, this, 1));
        } else {
            int x = this.armor + value;
            this.armor = value * (-1);
            BattleEntities.enemies[index].getComponent(EnemyComponent.class).reduceArmor(x);
        }
    }

    //物理攻击
    @Override
    public void physicalAttack(Role target, int value) {
        int index = getEntityIndex();
        if (index == -1) return;
        //执行攻击效果
        BattleEntities.enemies[index].getComponent(EnemyComponent.class).attack(value);
        //攻击后时机
        Opportunity.launchOpportunity(this, OpportunityType.ATTACK_TIME);
    }

    //回复血量
    @Override
    public void restoreBlood(int value) {
        this.blood = this.blood + value;
        if (this.blood > maxBlood) this.blood = this.maxBlood;
        int index = getEntityIndex();
        BattleEntities.enemies[index].getComponent(EnemyComponent.class).restoreBlood(value);
    }

    @Override
    public void addArmor(int value) {
        armor += value;
        int index = getEntityIndex();
        BattleEntities.enemies[index].getComponent(EnemyComponent.class).addArmor();
    }

    @Override
    public void upAttribute(AttributeUp attributeUp) {
        int index = getEntityIndex();
        BattleEntities.enemies[index].getComponent(EnemyComponent.class).attributeUP(attributeUp);
    }

    @Override
    public ArrayList<Opportunity> getRoleOpportunities() {
        return this.opportunities;
    }

    @Override
    public ArrayList<OpportunityType> getRoleOpportunityType() {
        return this.opportunityTypes;
    }

    //获得当前敌人实体所在位置索引
    private int getEntityIndex() {
        for (int i = 0; i < BattleEntities.enemyGenerateOrder.length; i++) {
            if (BattleInformation.ROLE_LOCATION.get(this).equals(String.valueOf(BattleEntities.enemyGenerateOrder[i])))
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return name + "   " + blood + "/" + maxBlood + Effect.NEW_LINE + attribute.displayAttribute() + Effect.NEW_LINE + IntentionType.intentionParse(this);
    }

    @Override
    public void die() {
        int index = getEntityIndex();
        //怪物序列移除该敌人
        BattleInformation.ENEMIES.remove(this);
        //播放死亡动画
        BattleEntities.enemies[index].getComponent(EnemyComponent.class).deathAnimation();
        //删除该实体
        BattleEntities.enemies[index] = null;
    }
}
