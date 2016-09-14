package gui;

import areaGenerator.IPoint;
import areaGenerator.SquareAreaGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        int n = 10;
        int m = 10;
        int bombsCount = 20;

        SquareAreaGenerator sag = new SquareAreaGenerator(n, m, bombsCount);

        primaryStage.setTitle("Miner");
        Group root = new Group();
        Canvas canvasBomb = new Canvas(n * 31, m * 31);
        Canvas canvasTop = new Canvas(n * 31, m * 31);
//        Canvas flagsLayer = new Canvas(n *31, m * 31);
        GraphicsContext gc = canvasBomb.getGraphicsContext2D();
        GraphicsContext gc2 = canvasTop.getGraphicsContext2D();
//        GraphicsContext gc3 = flagsLayer.getGraphicsContext2D();
        canvasTop.toFront();
        List<IPoint> points = sag.generateArea();

        for (IPoint point : points) {
            gc2.setFill(Color.GREEN);
            gc2.fillRoundRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint, 10, 10);
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

        canvasTop.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getSceneX();
                double y = event.getSceneY();
                for (IPoint point : points) {
                    if (x >= point.getPositionX() && x < point.getPositionX() + sag.widthPoint &&
                            y >= point.getPositionY() && y < point.getPositionY() + sag.heightPoint) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            if (point.hasBomb()) {
                                gc2.clearRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Game over");
                                alert.setHeaderText(null);
                                alert.setContentText("Вы всё взорвали =( Хотите сыграть еще раз?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK){

                                } else {
                                    primaryStage.close();
                                }
                            } else {
                                gc2.clearRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint);
                            }
                        }
                    }
                }
            }
        });

//        flagsLayer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                double x = event.getSceneX();
//                double y = event.getSceneY();
//                for (IPoint point : points) {
//                    if (x >= point.getPositionX() && x < point.getPositionX() + sag.widthPoint &&
//                            y >= point.getPositionY() && y < point.getPositionY() + sag.heightPoint) {
//                        if (event.getButton() == MouseButton.SECONDARY) {
//                            flagsLayer.toFront();
//                            gc3.drawImage(new Image("/flag.png"),point.getPositionX()+3,point.getPositionY()+3);
//                        }
//                    }
//                }
//            }
//        });

        root.getChildren().add(canvasBomb);
        root.getChildren().add(canvasTop);
//        root.getChildren().add(flagsLayer);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
