package com.hjc.CardAdventure.pojo.effects;

import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.util.RecordUtil;

import java.util.ArrayList;

public class RecordEffect extends Effect {
    //记录器操作代码xy(y3位，x代表操作的记录器)
    //x = 0，物理记录器


    //y = 0，删除记录器元素
    //y = 1，记录发动者护盾值

    public RecordEffect(Role from, Role to, int value) {
        super(from, to, value);
    }

    @Override
    public void action() {
        if (getFrom() == null || getTo() == null) return;
        ArrayList<Integer> integers;
        //判断记录器类型
        integers = switch (getValue() / 1000) {
            case 0 -> RecordUtil.PHYIntegers;
            case 1 -> RecordUtil.RestBloodIntegers;
            default -> null;
        };
        if (integers == null) return;

        //对记录器进行操作
        switch (getValue() % 1000) {
            case 0:
                RecordUtil.delete(integers);
                return;
            case 1:
                integers.add(getFrom().getRoleArmor());
                return;
        }
    }

    @Override
    public String describeInDetail() {
        String s = "";
        s += switch (getValue() % 1000) {
            case 1 -> "记录1：记录发动者的护盾值";
            default -> "";
        };

        if (!s.isEmpty()) {
            s += switch (getValue() / 1000) {
                case 0 -> "\n物理增伤：该伤害增加x（x为记录值）";
                case 1 -> "\n回复提升：该回复效果增加x（x为记录值）";
                default -> "";
            };
        }
        if (!s.isEmpty()) s += Effect.NEW_LINE;
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        s += switch (getValue() % 1000) {
            case 1 -> "记录1";
            default -> "";
        };

        if (!s.isEmpty()) {
            s += switch (getValue() / 1000) {
                case 0 -> "，物理增伤";
                case 1 -> "，回复提升";
                default -> "";
            };
        }
        return s;
    }
}
