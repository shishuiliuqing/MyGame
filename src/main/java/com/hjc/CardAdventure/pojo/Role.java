package com.hjc.CardAdventure.pojo;

public interface Role {
    void action();

    //获得角色名字
    String getRoleName();

    //获得角色属性
    Attribute getRoleAttribute();

    //受伤
    void physicalHurt(int value);

    //攻击
    void physicalAttack(Role target,int value);
}
