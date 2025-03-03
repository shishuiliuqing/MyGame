package com.hjc.CardAdventure.pojo.enemy;

import com.hjc.CardAdventure.components.role.EnemyComponent;
import com.hjc.CardAdventure.pojo.Attribute;
import com.hjc.CardAdventure.pojo.BattleEntities;
import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.ActionOver;
import com.hjc.CardAdventure.pojo.effects.DrawEffect;
import com.hjc.CardAdventure.pojo.effects.IntentionGenerate;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
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

    @Override
    public void action() {
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
        } else {
            this.armor = value * (-1);
        }
    }

    //物理攻击
    @Override
    public void physicalAttack(Role target, int value) {
        int index = getEntityIndex();
        if (index == -1) return;
        //执行攻击效果
        BattleEntities.enemies[index].getComponent(EnemyComponent.class).attack(value);
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

    //获得当前敌人实体所在位置索引
    private int getEntityIndex() {
        for (int i = 0; i < BattleInformation.ENEMIES.size(); i++) {
            if (BattleInformation.ENEMIES.get(i) == this) return i;
        }
        return -1;
    }
}
