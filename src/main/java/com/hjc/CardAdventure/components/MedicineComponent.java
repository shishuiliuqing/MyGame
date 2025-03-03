package com.hjc.CardAdventure.components;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import com.hjc.CardAdventure.CardAdventureApp;
import com.hjc.CardAdventure.pojo.Entities;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MedicineComponent extends Component {
    public static int[] medicines = {0, 0, 0, 1, 1};

    @Override
    public void onAdded() {
        Texture medicineTexture = FXGL.texture("medicine.png", 30, 30);
        medicineTexture.setTranslateX(605);
        medicineTexture.setTranslateY(15);
        entity.getViewComponent().addChild(medicineTexture);

        double xMove = 665;
        for (int i = 0; i < medicines.length; i++) {
            if (medicines[i] == 0) {
                Circle outCircle = new Circle(8, Color.valueOf("#aec6cf"));
                outCircle.setStroke(Color.BLACK);
                Circle inCircle = new Circle(4, Color.valueOf("#aec6cf"));
                inCircle.setStroke(Color.BLACK);
                outCircle.setCenterX(xMove + 50 * i  + 8);
                inCircle.setCenterX(xMove + 50 * i  + 8);
                outCircle.setTranslateY(30);
                inCircle.setTranslateY(30);
                entity.getViewComponent().addChild(outCircle);
                entity.getViewComponent().addChild(inCircle);
            } else {

            }
        }
    }

    public void update() {
        entity.getViewComponent().clearChildren();
        onAdded();
    }
}
