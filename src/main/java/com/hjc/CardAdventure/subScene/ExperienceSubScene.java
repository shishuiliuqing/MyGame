package com.hjc.CardAdventure.subScene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.CampEntities;
import com.hjc.CardAdventure.pojo.Entities;
import com.hjc.CardAdventure.pojo.environment.InsideInformation;
import com.hjc.CardAdventure.pojo.player.Level;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.hjc.CardAdventure.pojo.player.PlayerInformation.player;


public class ExperienceSubScene extends SubScene {
    //前进键
    private static final Texture forward = FXGL.texture("forward.png", 200, 200);
    //可添加属性点
    private static int attributeUP = 0;
    //界面关闭原则
    private final int flag;

    public ExperienceSubScene(int experience, int flag) {
        this.flag = flag;
        Rectangle background = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT, Color.rgb(80, 80, 80, 0.5));
        getContentRoot().getChildren().add(background);

        Label label = new Label("获 得 经 验 ！");
        label.setFont(new Font("华文行楷", 100));
        label.setTextFill(Color.YELLOW);
        label.setMaxSize(600, 100);
        label.setTranslateX((CardAdventureApp.APP_WITH - 600) / 2.0);
        label.setTranslateY(170);
        getContentRoot().getChildren().add(label);

        Texture boxTexture = FXGL.texture("rectBox.png", 888, 574);
        boxTexture.setTranslateX((CardAdventureApp.APP_WITH - 888.0) / 2);
        boxTexture.setTranslateY(280);
        getContentRoot().getChildren().add(boxTexture);
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), boxTexture);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setOnFinished(e -> experienceUp(experience));
        ft.play();
    }

    //经验提升
    private void experienceUp(int experience) {
        Text text = new Text(Level.getLV(player.getExperience()) + "\nexp");
        text.setFont(new Font("微软雅黑", 40));
        text.setTranslateX((CardAdventureApp.APP_WITH - 888.0) / 2 + 50);
        text.setTranslateY(380);
        getContentRoot().getChildren().add(text);

        Texture experienceTexture = FXGL.texture("experience.png", 600, 40);
        experienceTexture.setTranslateX((CardAdventureApp.APP_WITH - 888.0) / 2 + 130);
        experienceTexture.setTranslateY(400);
        getContentRoot().getChildren().add(experienceTexture);

        Text upText = new Text("+" + experience);
        upText.setFont(new Font("微软雅黑", 25));
        upText.setFill(Color.GREEN);
        upText.setTranslateX((CardAdventureApp.APP_WITH - 888.0) / 2 + 740);
        upText.setTranslateY(430);
        getContentRoot().getChildren().add(upText);

//        Rectangle expRect = new Rectangle(586, 25, Color.GREEN);
//        expRect.setTranslateX((CardAdventureApp.APP_WITH - 888.0) / 2 + 137);
//        expRect.setTranslateY(408);
//        getContentRoot().getChildren().add(expRect);
        int nowExperience = player.getExperience();
        int needExperience = Level.nextExperience(nowExperience);
        //System.out.println(needExperience);
        //经验条原来的大小
        double o = nowExperience == 0 ? 1.0 : nowExperience * 1.0;
        Rectangle expRect = new Rectangle((nowExperience == 0 ? 1.0 / needExperience : nowExperience * 1.0 / needExperience) * 586.0, 22, Color.GREEN);
        expRect.setTranslateX((CardAdventureApp.APP_WITH - 888.0) / 2 + 137);
        expRect.setTranslateY(408);
        getContentRoot().getChildren().add(expRect);
        //添加经验
        if (nowExperience + experience > needExperience) {
            experience = nowExperience + experience - needExperience;
            player.setExperience(needExperience);
            nowExperience = needExperience;
        } else {
            nowExperience += experience;
            player.setExperience(nowExperience);
            experience = nowExperience + experience - needExperience;
        }

        //经验条增长动画
        ScaleTransition st = new ScaleTransition(Duration.seconds(0.3), expRect);
        st.setToX(nowExperience / o);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.3), expRect);
        tt.setFromX((CardAdventureApp.APP_WITH - 888.0) / 2 + 137);
        tt.setToX((CardAdventureApp.APP_WITH - 888.0) / 2 + 137 + (nowExperience - o) / needExperience * 586.0 / 2);
        ParallelTransition pt = new ParallelTransition(st, tt);
        int finalExperience = experience;
        int finalNowExperience = nowExperience;
        pt.setOnFinished(e -> upgrade(finalExperience, finalNowExperience, needExperience));
        pt.play();
    }

    private void upgrade(int experience, int nowExperience, int needExperience) {
        Rectangle r = new Rectangle(586.0, 22, Color.rgb(0, 0, 0, 0));
        StackPane stackPane = new StackPane(r);
        Text text = new Text(nowExperience + " / " + needExperience);
        text.setFill(Color.WHITE);
        text.setFont(new Font("华文琥珀", 30));
        stackPane.getChildren().add(text);
        stackPane.setTranslateX((CardAdventureApp.APP_WITH - 888.0) / 2 + 137);
        stackPane.setTranslateY(408);
        getContentRoot().getChildren().add(stackPane);

        if (experience >= 0) {
            Label upgradeText = new Label("恭喜你升级了！");
            upgradeText.setMaxSize(400, 200);
            upgradeText.setTranslateX((CardAdventureApp.APP_WITH - 600) / 2.0 + 150);
            upgradeText.setTranslateY(458);
            upgradeText.setTextFill(Color.RED);
            upgradeText.setFont(new Font("华文行楷", 50));
            getContentRoot().getChildren().add(upgradeText);

            int[] reward = Level.upgrade(player.getExperience());
            if (reward[0] > 0) {
                //Label label = new Label();
                //生成属性添加的“+”按钮
                attributeUP = reward[0];
                attributeUp(experience);
            }

//            if (experience > 0) {
//                while (getContentRoot().getChildren().size() > 3) {
//                    getContentRoot().getChildren().remove(3);
//                }
//                experienceUp(experience);
//            }
        } else {
            forward.setTranslateX(1700);
            forward.setTranslateY(800);
            forward.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                Rectangle background = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_WITH, Color.rgb(0, 0, 0, 0));
                getContentRoot().getChildren().add(background);

                //加载动画
                Rectangle load = new Rectangle(1, 1);
                load.setTranslateX((CardAdventureApp.APP_WITH - 1) / 2.0);
                load.setTranslateY((CardAdventureApp.APP_HEIGHT - 1) / 2.0);
                getContentRoot().getChildren().add(load);
                ScaleTransition st = new ScaleTransition(Duration.seconds(1), load);
                st.setToX(CardAdventureApp.APP_WITH);
                st.setToY(CardAdventureApp.APP_HEIGHT);
                st.setOnFinished(event -> {
                    //关闭该界面后，进入下一时间状态，回到营地
                    if (flag == 0) {
                        //关闭该场景
                        FXGL.getSceneService().popSubScene();
                        InsideInformation.turnTimeStatus();
                        //移除所有实体
                        ArrayList<Entity> entities = new ArrayList<>(FXGL.getGameWorld().getEntities());
                        FXGL.getGameWorld().removeEntities(entities);
                        //初始化信息栏
                        Entities.initEntities();
                        //初始化营地
                        CampEntities.initCampEntities();
                    } else {
                        //单纯关闭此界面
                        FXGL.getSceneService().popSubScene();
                    }
                });
                st.play();

            });
            getContentRoot().getChildren().add(forward);
        }
    }

    private void attributeUp(int experience) {
        double xMove = 560.0;
        double yMove = 600.0;
        double interval = 138;
        double bYMove = yMove - 50;
        //Rectangle powerR = new Rectangle(40, 40, Color.GREEN);
        //力量添加
        Label powerText = new Label("力量：" + player.getAttribute().getPower());
        powerText.setFont(new Font("华文行楷", 20));
        powerText.setTranslateX(xMove);
        powerText.setTranslateY(yMove);
        getContentRoot().getChildren().add(powerText);
        Button powerUp = new Button("+");
        powerUp.setTranslateX(xMove);
        powerUp.setTranslateY(bYMove);
        powerUp.setFocusTraversable(false);
        powerUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(powerUp);

        //智力添加
        Label intelligenceText = new Label("智力：" + player.getAttribute().getIntelligence());
        intelligenceText.setFont(new Font("华文行楷", 20));
        intelligenceText.setTranslateX(xMove + interval * 1);
        intelligenceText.setTranslateY(yMove);
        getContentRoot().getChildren().add(intelligenceText);
        Button intelligenceUp = new Button("+");
        intelligenceUp.setTranslateX(xMove + interval * 1);
        intelligenceUp.setTranslateY(bYMove);
        intelligenceUp.setFocusTraversable(false);
        intelligenceUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(intelligenceUp);

        //智力添加
        Label defenseText = new Label("防御：" + player.getAttribute().getDefense());
        defenseText.setFont(new Font("华文行楷", 20));
        defenseText.setTranslateX(xMove + interval * 2);
        defenseText.setTranslateY(yMove);
        getContentRoot().getChildren().add(defenseText);
        Button defenseUp = new Button("+");
        defenseUp.setTranslateX(xMove + interval * 2);
        defenseUp.setTranslateY(bYMove);
        defenseUp.setFocusTraversable(false);
        defenseUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(defenseUp);

        //敏捷添加
        Label agilityText = new Label("敏捷：" + player.getAttribute().getAgility());
        agilityText.setFont(new Font("华文行楷", 20));
        agilityText.setTranslateX(xMove + interval * 3);
        agilityText.setTranslateY(yMove);
        getContentRoot().getChildren().add(agilityText);
        Button agilityUp = new Button("+");
        agilityUp.setTranslateX(xMove + interval * 3);
        agilityUp.setTranslateY(bYMove);
        agilityUp.setFocusTraversable(false);
        agilityUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(agilityUp);

        //纯洁添加
        Label purityText = new Label("纯洁：" + player.getAttribute().getPurity());
        purityText.setFont(new Font("华文行楷", 20));
        purityText.setTranslateX(xMove + interval * 4);
        purityText.setTranslateY(yMove);
        getContentRoot().getChildren().add(purityText);
        Button purityUp = new Button("+");
        purityUp.setTranslateX(xMove + interval * 4);
        purityUp.setTranslateY(bYMove);
        purityUp.setFocusTraversable(false);
        purityUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(purityUp);

        //速度添加
        Label speedText = new Label("速度：" + player.getAttribute().getSpeed());
        speedText.setFont(new Font("华文行楷", 20));
        speedText.setTranslateX(xMove + interval * 5);
        speedText.setTranslateY(yMove);
        getContentRoot().getChildren().add(speedText);
        Button speedUp = new Button("+");
        speedUp.setTranslateX(xMove + interval * 5);
        speedUp.setTranslateY(bYMove);
        speedUp.setFocusTraversable(false);
        speedUp.setStyle("-fx-base: ORANGE");
        getContentRoot().getChildren().add(speedUp);


        //提示操作框
        Label tip = new Label("请选择" + attributeUP + "个属性添加属性点");
        tip.setFont(new Font("华文行楷", 50));
        tip.setTextFill(Color.ORANGE);
        tip.setTranslateX(650);
        tip.setTranslateY(660);
        getContentRoot().getChildren().add(tip);

        //力量添加
        powerUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setPower(player.getAttribute().getPower() + 1);
            //刷新显示
            powerText.setText("力量：" + player.getAttribute().getPower());
            //移除该按钮
            getContentRoot().getChildren().remove(powerUp);
            if (attributeUP == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //智力添加
        intelligenceUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setIntelligence(player.getAttribute().getIntelligence() + 1);
            //刷新显示
            intelligenceText.setText("智力：" + player.getAttribute().getIntelligence());
            //移除该按钮
            getContentRoot().getChildren().remove(intelligenceUp);
            if (attributeUP == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //防御添加
        defenseUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setDefense(player.getAttribute().getDefense() + 1);
            //刷新显示
            defenseText.setText("防御：" + player.getAttribute().getDefense());
            //移除该按钮
            getContentRoot().getChildren().remove(defenseUp);
            if (attributeUP == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //敏捷添加
        agilityUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setAgility(player.getAttribute().getAgility() + 1);
            //刷新显示
            agilityText.setText("敏捷：" + player.getAttribute().getAgility());
            //移除该按钮
            getContentRoot().getChildren().remove(agilityUp);
            if (attributeUP == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //纯洁添加
        purityUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setPurity(player.getAttribute().getPurity() + 1);
            //刷新显示
            purityText.setText("智力：" + player.getAttribute().getPurity());
            //移除该按钮
            getContentRoot().getChildren().remove(purityUp);
            if (attributeUP == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });

        //速度添加
        speedUp.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            //可添加属性点-1
            attributeUP--;
            //玩家力量属性+1
            player.getAttribute().setSpeed(player.getAttribute().getSpeed() + 1);
            //刷新显示
            speedUp.setText("智力：" + player.getAttribute().getSpeed());
            //移除该按钮
            getContentRoot().getChildren().remove(speedUp);
            if (attributeUP == 0 && experience > 0) {
                //删除其他组件
                while (getContentRoot().getChildren().size() > 3) {
                    getContentRoot().getChildren().remove(3);
                }
                //继续添加剩余的经验
                experienceUp(experience);
            }
        });
    }
}
