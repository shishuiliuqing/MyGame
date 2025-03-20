package com.hjc.CardAdventure.pojo.event;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    //当前执行事件
    public static Event event;
    //事件编号
    private String num;
    //事件地址
    private String address;
    //事件名字
    private String name;
    //事件类型
    private EventType eventType;
    //事件选项
    ArrayList<Option> options;

    //事件创建（文本选择类）
    public static void createTextEvent(Event event) {
        Entity entity = FXGL.entityBuilder().buildAndAttach();
        //初始化背景框
        initBackBox(entity);
        //初始化图片
        initPicture(entity, event);
        //初始化标题
        initTitle(entity, event);
        //初始化文本
        initText(entity,event);
        //初始化选项
        initOption();
    }

    //初始化背景框
    private static void initBackBox(Entity entity) {
        Rectangle rectangle = new Rectangle(CardAdventureApp.APP_WITH, CardAdventureApp.APP_HEIGHT - 70, Color.rgb(0, 0, 0, 0.9));
        rectangle.setY(70);
        entity.getViewComponent().addChild(rectangle);

        Texture backBox = FXGL.texture("event/textBox.png", CardAdventureApp.APP_WITH-250, CardAdventureApp.APP_HEIGHT - 100);
        backBox.setTranslateX(125);
        backBox.setTranslateY(70);
        entity.getViewComponent().addChild(backBox);
    }

    //初始化图片
    private static void initPicture(Entity entity, Event event) {
        Texture picture = FXGL.texture("event/picture/" + event.getAddress() + ".png", 720, 720);
        picture.setTranslateY(145);
        picture.setTranslateX(195);
        entity.getViewComponent().addChild(picture);
    }

    //初始化标题
    private static void initTitle(Entity entity, Event event) {
        Rectangle rectangle = new Rectangle(770,70,Color.rgb(0,0,0,0));
        StackPane stackPane = new StackPane(rectangle);
        stackPane.setTranslateX(945);
        stackPane.setTranslateY(145);
        entity.getViewComponent().addChild(stackPane);

        Text text = new Text(event.getName());
        text.setFont(new Font("华文行楷",50));
        text.setFill(Color.WHITE);
        stackPane.getChildren().add(text);
    }

    //初始化文本
    private static void initText(Entity entity, Event event) {
//        System.out.println(FXGL.text(event.getAddress()+".txt"));
        String text = FXGL.text(event.getAddress() + ".txt").get(0);
        Label label = new Label(text);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("华文行楷",40));
        label.setWrapText(true);
        label.setMaxSize(770,720);
        label.setTranslateX(945);
        label.setTranslateY(215);
        entity.getViewComponent().addChild(label);
    }
}
