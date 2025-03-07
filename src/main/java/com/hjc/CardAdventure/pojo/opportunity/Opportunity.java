package com.hjc.CardAdventure.pojo.opportunity;

import com.hjc.CardAdventure.pojo.BattleInformation;
import com.hjc.CardAdventure.pojo.Role;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.Effects;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Opportunity {
    //效果名称
    private String name;
    //触发时机
    private OpportunityType opportunityType;
    //可执行回合
    private int rounds;
    //可触发次数
    private int num;
    //目标
    private Role to;
    //触发执行的效果
    private int[] effects;
    //触发次数为0时触发的效果
    private int[] endEffects;
    //是否可叠加
    private boolean stackable;
    //叠加层数
    private int layer;

    //向某个角色添加某个时机效果
    public static void addOpportunity(Opportunity opportunity, Role role) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
//        if (opportunity.getName().equals("寻回")) {
//            opportunities.add(opportunity);
//            updateOpportunityTypes(role);
//        }
        for (Opportunity o : opportunities) {
            if (o.getName().equals(opportunity.getName())) {
                //可叠加
                if (opportunity.getStackable()) {
                    o.setLayer(o.getLayer() + opportunity.getLayer());
                }
                if (o.getNum() == 999) return;
                o.setNum(o.getNum() + opportunity.getNum());
                return;
            }
        }

        opportunities.add(opportunity);
        //更新可触发队列
        updateOpportunityTypes(role);
    }

    //判断某个时机类型是否在某个角色的可触发时机类型
    public static boolean judgeOpportunityType(OpportunityType opportunityType, ArrayList<Opportunity> opportunities) {
        for (Opportunity opportunity : opportunities) {
            if (opportunity.getOpportunityType() == opportunityType) return true;
        }
        return false;
    }

    //某个时机到来，触发效果（次数触发器）
    public static void launchOpportunity(Role role, OpportunityType opportunityType) {
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        int n = 0;
        //满足发动时机的，发动效果
        while (n < opportunities.size()) {
            Opportunity opportunity = opportunities.get(n);
            if (opportunity.getOpportunityType() == opportunityType) {
                //发动次数-1
                if (opportunity.num != 999) opportunity.num--;
                //发动次数为0，执行结束效果
                if (opportunity.num == 0) {
                    ArrayList<Effect> endEffects = Effects.getEffects(opportunity.getEndEffects(), role, opportunity.getTo());
                    BattleInformation.insetEffect(endEffects);
                    //移除该时机效果
                    opportunities.remove(opportunity);
                    //更新可触发时机队列
                    updateOpportunityTypes(role);
                    n--;
                }

                for (int i = 0; i < opportunity.getLayer(); i++) {
                    //解析效果序列
                    ArrayList<Effect> oEffects = Effects.getEffects(opportunity.effects, role, opportunity.getTo());
                    //插入效果执行器发动效果
                    BattleInformation.insetEffect(oEffects);
                }
            }
            n++;
        }
    }

    //更新角色的可触发时机列表
    public static void updateOpportunityTypes(Role role) {
        ArrayList<OpportunityType> opportunityTypes = role.getRoleOpportunityType();
        opportunityTypes.clear();

        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        for (Opportunity opportunity : opportunities) {
            if (opportunityTypes.contains(opportunity.getOpportunityType())) continue;
            opportunityTypes.add(opportunity.getOpportunityType());
        }
    }

    //回合结束效果触发
    public static void roundOpportunityLaunch(Role role) {
        //执行该角色所有回合效果
        launchOpportunity(role, OpportunityType.ROUND_BEGIN);

        int n = 0;
        ArrayList<Opportunity> opportunities = role.getRoleOpportunities();
        while (n < opportunities.size()) {
            Opportunity opportunity = opportunities.get(n);
            //与回合无关效果触发
            if (opportunity.rounds == 0) {
                n++;
                continue;
            }
            //与回合有关效果
            opportunity.rounds--;
            if (opportunity.rounds == 0) {
                opportunities.remove(opportunity);
                ArrayList<Effect> endEffect = Effects.getEffects(opportunity.endEffects, role, opportunity.getTo());
                BattleInformation.insetEffect(endEffect);
                n--;
            }
            n++;
        }
    }

    //将时机效果转文本描述
    @Override
    public String toString() {
        String s = getName();
        if (num != 999) s += num;
        if (layer > 1) s += "（" + layer + "）";
        return s;
    }

    //时机效果序列转文本
    public static String opportunitiesToString(ArrayList<Opportunity> opportunities) {
        StringBuilder sb = new StringBuilder();
        for (Opportunity opportunity : opportunities) {
            sb.append(opportunity);
            if (opportunity.toString().isEmpty()) {
                continue;
            }
            sb.append("；");
        }
        sb.append(Effect.NEW_LINE);

        for (Opportunity opportunity : opportunities) {
            sb.append(opportunity.describeDetail());
        }
        return sb.toString();
    }

    //时机效果详细描述
    public String describeDetail() {
        String s = "";
        if (getName().equals("伤害翻倍")) {
            s = "伤害翻倍：回合结束后失效，使下" + num + "次伤害翻倍" + Effect.NEW_LINE;
        } else if (getName().equals("虚弱")) {
            s = "虚弱：使下 " + num + "次的伤害降低25%" + Effect.NEW_LINE;
        } else {
            s = "<" + getName() + ">";
            if (num != 999) {
                s += "（该效果可触发" + num + "次）";
            }
            if (layer > 1) {
                s += "（拥有该效果层数：" + layer + "）";
            }
            s += "\n" + OpportunityType.detailInformation(opportunityType);
            if (effects != null && effects.length > 0) {
                s += "\n";
                s += Effects.effectsDetail(effects);
            }
            if (endEffects != null && endEffects.length > 0) {
                s += "\n";
                s += Effects.effectsDetail(endEffects);
            }
            if ((endEffects == null || endEffects.length == 0) && (effects == null || effects.length == 0))
                s += Effect.NEW_LINE;
        }
        return s;
    }

    public boolean getStackable() {
        return this.stackable;
    }
}
