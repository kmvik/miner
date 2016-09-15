package gui;

import areaGenerator.PointBase;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Created by mariya.chernyshova on 15.09.2016.
 */
public class DrawerAreaWithSquarePoints {
    private final String BOMB_PATH = "/bomb.png";
    private double areaHeight;
    private double areaWidth;
    private final int ARC = 10;
    private Canvas cBombs;
    private Canvas cTop;

    public DrawerAreaWithSquarePoints (double areaHeight, double areaWidth) {
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        cBombs = new Canvas(areaWidth, areaHeight);
        cTop = new Canvas(areaWidth, areaHeight);
    }

    public Canvas drawLayoutWithBombs(List<PointBase> points) {
        GraphicsContext gc = cBombs.getGraphicsContext2D();
        for (PointBase point : points) {
            if (point.hasBomb()) {
                gc.drawImage(new Image(BOMB_PATH),point.getPositionX()+3,point.getPositionY()+3);
            } else {
                int y = 20;
                int x = 13;
                gc.setFill(Color.BLACK);
                if (point.getNumber() != 0) {
                    gc.fillText(String.valueOf(point.getNumber()), point.getPositionX() + x, point.getPositionY() + y);
                }
            }
        }
        return cBombs;
    }

    public Canvas drawTopLayout(List<PointBase> points) {
        GraphicsContext gc2 = cTop.getGraphicsContext2D();
        for (PointBase point : points) {
            gc2.setFill(Color.GREEN);
            gc2.fillRoundRect(point.getPositionX(), point.getPositionY(), point.getPointWidth(), point.getPointHeight(), ARC, ARC);
        }
        return cTop;
    }
}
