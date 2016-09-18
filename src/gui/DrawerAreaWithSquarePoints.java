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
    private final String FLAG_PATH = "/flag.png";
    private final int ARC = 10;
    private Canvas cBombs;
    private GraphicsContext gcBombs;
    private Canvas cTop;
    private GraphicsContext gcTop;

    public DrawerAreaWithSquarePoints (double areaHeight, double areaWidth) {
        cBombs = new Canvas(areaWidth, areaHeight);
        cTop = new Canvas(areaWidth, areaHeight);
        gcBombs = cBombs.getGraphicsContext2D();
        gcTop = cTop.getGraphicsContext2D();
    }

    public Canvas drawLayoutWithBombs(List<PointBase> points) {
        for (PointBase point : points) {
            if (point.hasBomb()) {
                gcBombs.drawImage(new Image(BOMB_PATH),point.getPositionX()+3,point.getPositionY()+3);
            } else {
                int y = 20;
                int x = 13;
                gcBombs.setFill(Color.BLACK);
                if (point.getNumber() != 0) {
                    gcBombs.fillText(String.valueOf(point.getNumber()), point.getPositionX() + x, point.getPositionY() + y);
                }
            }
        }
        return cBombs;
    }

    public Canvas drawTopLayout(List<PointBase> points) {
        for (PointBase point : points) {
            gcTop.setFill(Color.GREEN);
            gcTop.fillRoundRect(point.getPositionX(), point.getPositionY(), point.getWidth(), point.getHeight(), ARC, ARC);
        }
        return cTop;
    }

    public void clearOpenedPoints(List<PointBase> points) {
        for (PointBase p : points) {
            if (p.isOpen()) {
                gcTop.clearRect(p.getPositionX(), p.getPositionY(), p.getWidth(), p.getHeight());
            }
        }
    }

    public void clearPoint(PointBase point) {
        gcTop.clearRect(point.getPositionX(), point.getPositionY(), point.getWidth(), point.getHeight());
    }

    public void drawFlag(PointBase point) {
        gcTop.drawImage(new Image(FLAG_PATH),point.getPositionX()+3, point.getPositionY()+3);
    }

    public void drawDefaultPoint(PointBase point) {
        gcTop.fillRoundRect(point.getPositionX(), point.getPositionY(), point.getWidth(), point.getHeight(), ARC, ARC);
    }
}
