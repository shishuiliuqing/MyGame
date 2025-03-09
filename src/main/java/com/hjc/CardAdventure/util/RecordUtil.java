package com.hjc.CardAdventure.util;

import java.util.ArrayList;

//记录器
public class RecordUtil {
    //物理数值记录器
    public static ArrayList<Integer> PHYIntegers = new ArrayList<>();
    //血量回复数值记录器
    public static ArrayList<Integer> RestBloodIntegers = new ArrayList<>();

    //获取数值记录器第一个元素
    public static int getInteger(ArrayList<Integer> integers) {
        int x = 0;
        for (Integer integer : integers) {
            x += integer;
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
