package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.battle.*;
import com.hjc.CardAdventure.pojo.attribute.Attribute;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.ActionOver;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.PauseEffect;
import com.hjc.CardAdventure.pojo.effects.RoleAction;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.EnemyType;
import com.hjc.CardAdventure.pojo.enemy.IntentionGenerateType;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import com.hjc.CardAdventure.pojo.opportunity.Opportunity;
import com.hjc.CardAdventure.pojo.opportunity.OpportunityType;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;
import com.hjc.CardAdventure.subScene.RewardSubScene;
import com.hjc.CardAdventure.util.AttributeUtil;
import com.hjc.CardAdventure.util.OutUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class BattleInformation {
    //敌人类型，用于决定获得经验
    public static EnemyType enemyType;
    //敌人序列
    public static final ArrayList<Enemy> ENEMIES = new ArrayList<>();
    //记录敌人所在位置
    public static final HashMap<Role, String> ROLE_LOCATION = new HashMap<>();
    //当前行动序列
    public static final ArrayList<Role> THIS_ACTION = new ArrayList<>();
    //下一回合行动序列
    public static final ArrayList<Role> NEXT_ACTION = new ArrayList<>();
    //抽牌堆
    public static final ArrayList<Card> DRAW_CARDS = new ArrayList<>();
    //弃牌堆
    public static final ArrayList<Card> ABANDON_CARDS = new ArrayList<>();
    //消耗牌堆
    public static final ArrayList<Card> CONSUME_CARDS = new ArrayList<>();
    //手牌数
    //public static final Card[] HAND_CARDS = new Card[10];
    //效果序列器
    public static final ArrayList<Effect> EFFECTS = new ArrayList<>();
    //当前回合数
    public static int rounds;
    //战斗中的人物属性
    public static Attribute attribute;
    //判断是否在战斗
    public static boolean isBattle = false;
    //当前回合执行人
    public static Role nowAction;

    private BattleInformation() {
    }

    public static void initBattle() {
        //初始化工具类
        AttributeUtil.initUtil();

        //初始化回合数
        rounds = 0;

        //初始化敌人序列
        initEnemies();

        //初始化牌堆
        initCards();

        //初始化人物属性
        initAttribute();

        //初始化行动序列
        initActions();


        //正在战斗
        isBattle = true;
        //System.out.println(ENEMIES.get(0) == ENEMIES.get(1));
        //开始战斗
        //battle();
    }


    //初始化敌人序列
    private static void initEnemies() {
        //初始化敌人序列
        ENEMIES.clear();
        //添加怪物
        ENEMIES.addAll(CardAdventureApp.seasonMonsterPool.getEnemies(1, TimeStatus.DAY));
        //为每位敌人初始化意图
        for (Enemy enemy : ENEMIES) {
            IntentionGenerateType.generateIntention(enemy);
        }
        //初始化敌人位置
        ROLE_LOCATION.clear();
        ROLE_LOCATION.put(PlayerInformation.player, "P");
        for (int i = 0; i < ENEMIES.size(); i++) {
            ROLE_LOCATION.put(ENEMIES.get(i), String.valueOf(BattleEntities.enemyGenerateOrder[i]));
        }
    }

    //初始化牌堆
    private static void initCards() {
        //初始化抽牌堆
        DRAW_CARDS.clear();
        DRAW_CARDS.addAll(PlayerInformation.cards);
        OutUtil.disruptCards(DRAW_CARDS);
        //初始化弃牌堆
        ABANDON_CARDS.clear();
        //初始化消耗牌堆
        CONSUME_CARDS.clear();
        //初始化手牌区
        CardComponent.HAND_CARDS.clear();
//        for (int i = 0; i < 10; i++) {
//            HAND_CARDS[i] = null;
//        }
        //清除手牌区状态
        Arrays.fill(DrawCardsComponent.CARD_BOX_STATUS, 0);
    }

    //保存角色原有属性,并初始化玩家属性
    private static void initAttribute() {
        attribute = new Attribute();
        //保留玩家初始属性
        Attribute.cloneAttribute(PlayerInformation.player.getAttribute(), attribute);
        //清除人物护盾
        PlayerInformation.playerArmor = 0;
        //初始化各组件布尔值
        AbandonComponent.isLight = false;
        //非玩家回合
        ActionOverComponent.isPlayer = false;
        //属性更新
        AttributeComponent.attribute = PlayerInformation.player.getAttribute();
        //回合失去护盾打开
        PlayerInformation.lostArmorFlag = true;
        //非弃牌阶段
        CardComponent.isAbandon = false;
        //不可选择卡牌
        CardComponent.selectable = false;
        //当前行动卡牌清空
        CardComponent.actionCard = null;
        //使用键暗
        ProduceComponent.isLight = false;
        //记牌器刷新
        SumCardsComponent.remainingProduce = 0;
        //目标指定刷新
        TargetComponent.needTarget = false;
        TargetComponent.isRam = false;
        TargetComponent.isAll = false;
        TargetComponent.target = null;
        //玩家效果序列刷新
        PlayerInformation.opportunities.clear();
        PlayerInformation.opportunityTypes.clear();
    }

    //初始化行动序列
    private static void initActions() {
        //初始化行动序列
        THIS_ACTION.clear();
        NEXT_ACTION.clear();
        //添加所有角色，根据速度进行排序
        THIS_ACTION.add(PlayerInformation.player);
        THIS_ACTION.addAll(ENEMIES);
        sort(THIS_ACTION);
        //System.out.println(THIS_ACTION);
        NEXT_ACTION.addAll(THIS_ACTION);
        //初始化回合数为1
        rounds++;
    }

    //根据速度排序行动序列
    public static void sort(ArrayList<Role> roles) {
        for (int i = 0; i < roles.size(); i++) {
            int index = getMaxI(roles, i);
            Role t = roles.get(index);
            roles.set(index, roles.get(i));
            roles.set(i, t);
        }
    }

    //获得速度最大的角色索引，从n出发
    private static int getMaxI(ArrayList<Role> roles, int n) {
        int index = n;
        int maxSpeed = 0;
        for (int i = n; i < roles.size(); i++) {
            int speed = roles.get(i).getRoleAttribute().getSpeed();
            if (speed > maxSpeed) {
                maxSpeed = speed;
                index = i;
            }
        }
        return index;
    }

    //战斗开始
    public static void battle() {
        if (THIS_ACTION.isEmpty()) {
            THIS_ACTION.addAll(NEXT_ACTION);
            //回合添加
            rounds++;
            //触发所有角色的回合结束效果
            for (int i = ENEMIES.size() - 1; i >= 0; i--) {
                if (ENEMIES.get(i) == null) continue;
                Opportunity.roundOpportunityLaunch(ENEMIES.get(i));
            }
            Opportunity.roundOpportunityLaunch(PlayerInformation.player);
        }
        //获取当前行动对象
        //System.out.println(THIS_ACTION);
        Role role = THIS_ACTION.get(0);
        THIS_ACTION.remove(0);
        //生成行动执行效果
        RoleAction roleAction = new RoleAction(role, role, 1);
        EFFECTS.add(roleAction);
        //效果执行
        //effectExecution();
    }

    //效果执行器
    public static void effectExecution() {
        while (!EFFECTS.isEmpty()) {
            //System.out.println(EFFECTS);
            if (ENEMIES.isEmpty() && isBattle) {
                EFFECTS.clear();
                Attribute.cloneAttribute(attribute, PlayerInformation.player.getAttribute());
                //FXGL.getSceneService().pushSubScene(new RewardSubScene());
                reward();
                isBattle = false;
                break;
            }

            Effect effect = EFFECTS.get(0);
            EFFECTS.remove(0);
            effect.action();
            if (effect instanceof PauseEffect) break;
        }
    }

    private static void reward() {
        if (enemyType == EnemyType.LITTLE_MONSTER) {
            RewardSubScene.REWARD.clear();
            RewardSubScene.REWARD.add(1);
            //RewardSubScene.REWARD.add(1);
            RewardSubScene.REWARD.add(4);
            FXGL.getSceneService().pushSubScene(new RewardSubScene());
        }
    }

    //删除某个角色
    public static void clearRole(Role role) {
        //行动序列移除该角色
        THIS_ACTION.remove(role);
        NEXT_ACTION.remove(role);
        //移除所有有关该角色的效果（除回合结束）
        EFFECTS.removeIf(effect -> (!(effect instanceof ActionOver)) && (effect.getFrom() == role || effect.getTo() == role));
    }

    //效果序列插入一种效果
    public static void insetEffect(Effect effect) {
        EFFECTS.add(0, effect);
    }

    //效果序列插入多种效果
    public static void insetEffect(ArrayList<Effect> effects) {
        for (int i = effects.size() - 1; i >= 0; i--) {
            EFFECTS.add(0, effects.get(i));
        }
    }
}
