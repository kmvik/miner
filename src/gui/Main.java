package gui;

import areaGenerator.IPoint;
import areaGenerator.SquareAreaGenerator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        int n = 10;
        int m = 10;
        int bombsCount = 10;

        SquareAreaGenerator sag = new SquareAreaGenerator(n, m, bombsCount);

        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(n * 31, m * 31);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);

        List<IPoint> points = sag.generateArea();

        for (IPoint point : points) {
            if (point.hasBomb()) {
                gc.setFill(Color.RED);
                gc.fillRoundRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint, 10, 10);
            } else if (point.getNumber() == 0) {
                gc.setFill(Color.GREEN);
                gc.fillRoundRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint, 10, 10);
            } else {
                int y = 20;
                int x = 13;
                switch (point.getNumber()) {
                    case 1:
                        gc.fillText("1", point.getPositionX() + x, point.getPositionY() + y);
                        break;
                    case 2:
                        gc.fillText("2", point.getPositionX() + x, point.getPositionY() + y);
                        break;
                    case 3:
                        gc.fillText("3", point.getPositionX() + x, point.getPositionY() + y);
                        break;
                    case 4:
                        gc.fillText("4", point.getPositionX() + x, point.getPositionY() + y);
                        break;
                    case 5:
                        gc.fillText("5", point.getPositionX() + x, point.getPositionY() + y);
                        break;
                }
            }
        }

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getSceneX();
                double y = event.getSceneY();
                for (IPoint point : points) {
                    if (x >= point.getPositionX() && x < point.getPositionX() + sag.widthPoint && y >= point.getPositionY() && y < point.getPositionY() + sag.heightPoint) {
                        gc.clearRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint);
                    }
                }
            }
        });
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void drawShapes(GraphicsContext gc, List<Point> points) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i % 29 == 0 && j % 29 == 0) {
                    points.add(new Point(i, j));
//                    gc.fillPolygon(new double[] {i + 8, i +18,  i + 26, i +18, i+8 , i},
//                            new double[] {j, j, j-13, j-26, j-26, j-13},
//                            6);
                    gc.fillRoundRect(i, j, 30, 30, 10, 10);
                }
            }
        }
    }

    private class Point {
        public double x;
        public double y;

        public Point (double _x, double _y) {
            x = _x;
            y = _y;
        }
    }
}
