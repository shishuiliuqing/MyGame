package com.hjc.CardAdventure.util;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Utils {
    private Utils() {
    }

    //创建圆心数字
    public static StackPane generateCircleNum(double x, double y, double r, int num, String colorS, Font textFont) {
        //创建一个圆
        Circle circle = new Circle(r, Color.valueOf(colorS));
        //创建数字文本
        Text numText = new Text(String.valueOf(num));
        numText.setFont(textFont);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(circle);
        stackPane.getChildren().add(numText);
        stackPane.setTranslateX(x - r);
        stackPane.setTranslateY(y - r);

        return stackPane;
    }

    //颜色解析
    public static String parseColor(String colorS) {
        if (colorS.equals("#ff0000")) return "Red";
        if (colorS.equals("#76d1ff")) return "Blue";
        return "";
    }
}
