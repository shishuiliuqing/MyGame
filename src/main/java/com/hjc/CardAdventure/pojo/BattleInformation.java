package com.hjc.CardAdventure.pojo;

import com.almasb.fxgl.dsl.FXGL;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.components.battle.CardComponent;
import com.hjc.CardAdventure.pojo.card.Card;
import com.hjc.CardAdventure.pojo.effects.Effect;
import com.hjc.CardAdventure.pojo.effects.RoleAction;
import com.hjc.CardAdventure.pojo.enemy.Enemy;
import com.hjc.CardAdventure.pojo.enemy.IntentionGenerateType;
import com.hjc.CardAdventure.pojo.environment.TimeStatus;
import com.hjc.CardAdventure.pojo.player.PlayerInformation;

import java.util.ArrayList;
import java.util.HashMap;


public class BattleInformation {
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

    private BattleInformation() {
    }

    public static void initBattle() {
        //初始化敌人序列
        initEnemies();

        //初始化牌堆
        initCards();

        //初始化人物属性
        initAttribute();

        //初始化行动序列
        initActions();

        //初始化回合数
        rounds = 1;

        //System.out.println(ENEMIES.get(0) == ENEMIES.get(1));
        //开始战斗
        //battle();
    }


    //初始化敌人序列
    private static void initEnemies() {
        //初始化敌人序列
        ENEMIES.clear();
        //添加怪物
        ENEMIES.addAll(CardAdventureApp.seasonMonsterPool.getEnemies(1, TimeStatus.EVENING));
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
        //初始化弃牌堆
        ABANDON_CARDS.clear();
        //初始化消耗牌堆
        CONSUME_CARDS.clear();
        //初始化手牌区
        CardComponent.HAND_CARDS.clear();
//        for (int i = 0; i < 10; i++) {
//            HAND_CARDS[i] = null;
//        }
    }

    //保存角色原有属性,并初始化玩家属性
    private static void initAttribute() {
        attribute = new Attribute();
        Attribute.cloneAttribute(PlayerInformation.player.getAttribute(), attribute);
        PlayerInformation.playerArmor = 0;
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
        System.out.println(THIS_ACTION);
        NEXT_ACTION.addAll(THIS_ACTION);
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
        }
        //获取当前行动对象
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
            Effect effect = EFFECTS.get(0);
            EFFECTS.remove(0);
            effect.action();
        }
    }
}
