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
import java.util.stream.Collectors;

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
            PointBase pointClicked = points.stream().filter(a -> x >= a.getPositionX() && x < a.getPositionX() + a.getPointWidth()
            && y >= a.getPositionY() && y < a.getPositionY() + sag.heightPoint).collect(Collectors.toList()).get(0);
            if (event.getButton() == MouseButton.PRIMARY && !pointClicked.hasFlag()) {
                gc2.clearRect(pointClicked.getPositionX(), pointClicked.getPositionY(), sag.widthPoint, sag.heightPoint);
                pointClicked.setIsOpen(true);
                if (pointClicked.hasBomb()) {
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
                if (pointClicked.getNumber() == 0) {
                    sag.openEmptyArea(points, pointClicked).stream().filter(p -> p.isOpen()).collect(Collectors.toList()).forEach(drawer::clearTopLayoutOnPoint);
                }
            }
            if (event.getButton() == MouseButton.SECONDARY && !pointClicked.isOpen()) {
                if (pointClicked.hasFlag()) {
                    gc2.fillRoundRect(pointClicked.getPositionX(), pointClicked.getPositionY(), sag.widthPoint, sag.heightPoint, 10, 10);
                    pointClicked.setHasFlag(false);
                } else {
                    gc2.drawImage(new Image("/flag.png"),pointClicked.getPositionX()+3,pointClicked.getPositionY()+3);
                    pointClicked.setHasFlag(true);
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
