package gui;

import areaGenerator.PointBase;
import areaGenerator.SquareAreaGenerator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
                List<PointBase> points = sag.generateArea();
        DrawerAreaWithSquarePoints drawer = new DrawerAreaWithSquarePoints(sag.getAreaHeightPx(), sag.getAreaWidthPx());
        Canvas canvasBomb = drawer.drawLayoutWithBombs(points);
        Canvas canvasTop = drawer.drawTopLayout(points);
        GraphicsContext gc2 = canvasTop.getGraphicsContext2D();
        canvasTop.toFront();

        canvasTop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            for (PointBase point : points) {
                if (x >= point.getPositionX() && x < point.getPositionX() + sag.widthPoint &&
                        y >= point.getPositionY() && y < point.getPositionY() + sag.heightPoint) {
                    if (event.getButton() == MouseButton.PRIMARY && !point.hasFlag()) {
                        gc2.clearRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint);
                        point.setIsOpen(true);
                        if (point.hasBomb()) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Game over");
                            alert.setHeaderText(null);
                            alert.setContentText("Вы всё взорвали =( Хотите сыграть еще раз?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                // перезагрузка приложения
                            } else {
                                primaryStage.close();
                            }
                        }
                        if (point.getNumber() == 0) {
                            sag.openEmptyArea(points, point);
                        }
                    }
                    if (event.getButton() == MouseButton.SECONDARY && !point.hasBomb()) {
                        if (point.hasFlag()) {
                            gc2.fillRoundRect(point.getPositionX(), point.getPositionY(), sag.widthPoint, sag.heightPoint, 10, 10);
                            point.setHasFlag(false);
                        } else {
                            gc2.drawImage(new Image("/flag.png"),point.getPositionX()+3,point.getPositionY()+3);
                            point.setHasFlag(true);
                        }
                    }
                }
            }
        });

        root.getChildren().add(canvasBomb);
        root.getChildren().add(canvasTop);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
