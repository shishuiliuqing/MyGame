package com.hjc.CardAdventure.pojo;

import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.attribute.AttributeDown;
import com.hjc.CardAdventure.pojo.attribute.AttributeUp;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;

import java.util.ArrayList;

public interface Role {
    void action();

    //获得角色名字
    String getRoleName();

    //获得角色属性
    Attribute getRoleAttribute();

    //获得角色护甲
    int getRoleArmor();

    //物理受伤
    void physicalHurt(int value);

    //特殊受伤
    void specialHurt(int value, SpecialDamageType specialDamageType);

    //攻击
    void physicalAttack(Role target, int value);

    //死亡
    void die();

    //获取目标血量
    int getRoleBlood();

    //回血
    void restoreBlood(int value);

    //护甲增加
    void addArmor(int value);

    //设置角色护盾值
    void setRoleArmor(int vale);

    //设置角色是否回合开始失去护盾
    void isLostArmor(boolean flag);

    //设置角色易伤状态
    void setRoleVulnerability(boolean vulnerability);

    //属性添加
    void upAttribute(AttributeUp attributeUp);

    //属性下降
    void downAttribute(AttributeDown attributeDown);

    //获得角色的效果时机序列
    ArrayList<Opportunity> getRoleOpportunities();

    //获取角色的可触发效果时机
    ArrayList<OpportunityType> getRoleOpportunityType();

}
