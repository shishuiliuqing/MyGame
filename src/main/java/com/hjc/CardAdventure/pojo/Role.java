package com.hjc.CardAdventure.pojo;

import com.hjc.CardAdventure.pojo.attribute.Attribute;
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

    //受伤
    void physicalHurt(int value);

    //攻击
    void physicalAttack(Role target, int value);

    //死亡
    void die();

    //回血
    void restoreBlood(int value);

    //护甲增加
    void addArmor(int value);

    //属性添加
    void upAttribute(AttributeUp attributeUp);

    //获得角色的效果时机序列
    ArrayList<Opportunity> getRoleOpportunities();

    //获取角色的可触发效果时机
    ArrayList<OpportunityType> getRoleOpportunityType();
}
