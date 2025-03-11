package com.hjc.CardAdventure.util;

import java.util.ArrayList;

//记录器
public class RecordUtil {
    //物理数值记录器
    public static ArrayList<Integer> PHYIntegers = new ArrayList<>();
    //血量回复数值记录器
    public static ArrayList<Integer> RestBloodIntegers = new ArrayList<>();
    //伤害减免记录器
    public static ArrayList<Integer> ReduceDamageIntegers = new ArrayList<>();
    //怪物血量记录器，用于造成伤害判断
    public static ArrayList<Integer> EnemyBloodIntegers = new ArrayList<>();

    //获取数值记录器第一个元素
    public static int getInteger(ArrayList<Integer> integers) {
        int x = 0;
        for (Integer integer : integers) {
            x += integer;
            if (x >= 999) return 999;
        }
        return x;
    }

    //初始化RecordUtil
    public static void initRecord() {
        PHYIntegers.clear();
        RestBloodIntegers.clear();
    }

    //删除Record中的最后的元素
    public static void delete(ArrayList<Integer> integers) {
        if (integers == null) return;
        if (!integers.isEmpty()) integers.remove(integers.size() - 1);
    }
}
