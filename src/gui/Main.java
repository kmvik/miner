package gui;

import areaGenerator.IPoint;
import areaGenerator.SquareAreaGenerator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        int n = 10;
        int m = 10;
        int bombsCount = 20;

        SquareAreaGenerator sag = new SquareAreaGenerator(n, m, bombsCount);

        primaryStage.setTitle("Miner");
        Group root = new Group();
        Canvas canvas = new Canvas(n * 31, m * 31);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        List<IPoint> points = sag.generateArea();

        for (IPoint point : points) {
            if (point.hasBomb()) {
                gc.drawImage(new Image("/bomb.png"),point.getPositionX()+3,point.getPositionY()+3);
            } else {
                int y = 20;
                int x = 13;
                gc.setFill(Color.BLACK);
                if (point.getNumber() != 0) {
                    gc.fillText(String.valueOf(point.getNumber()), point.getPositionX() + x, point.getPositionY() + y);
                }
            }
        }

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getSceneX();
                double y = event.getSceneY();
                for (IPoint point : points) {
                    if (x >= point.getPositionX() && x < point.getPositionX() + sag.widthPoint &&
                            y >= point.getPositionY() && y < point.getPositionY() + sag.heightPoint) {
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
}
