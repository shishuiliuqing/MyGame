package com.hjc.CardAdventure.pojo.player;

import com.hjc.CardAdventure.pojo.Medicine;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;

import java.util.ArrayList;

public class PlayerInformation {
    //当前玩家角色
    public static Player player;
    //当前金币数
    public static int gold = 99;
    //药水栏位
    public static int medicineBoxNum = 3;
    //药水位
    public static ArrayList<Medicine> medicines = new ArrayList<>();
    //角色牌堆
    public static ArrayList<Card> cards = new ArrayList<>();
    //角色护甲
    public static int playerArmor = 0;
    //角色时机序列
    public static ArrayList<Opportunity> opportunities = new ArrayList<>();
    //角色可触发时机
    public static ArrayList<OpportunityType> opportunityTypes = new ArrayList<>();
}
