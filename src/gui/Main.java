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
        int bombsCount = 15;
        double pointSize = 30;
        int lifeCount = 0;

        SquareAreaGenerator sag = new SquareAreaGenerator(n, m, bombsCount, pointSize, pointSize);

        primaryStage.setTitle("Miner");
        Group root = new Group();
        List<PointBase> points = sag.generateArea();
        DrawerAreaWithSquarePoints drawer = new DrawerAreaWithSquarePoints(sag.getAreaHeightPx(), sag.getAreaWidthPx());
        Canvas canvasBomb = drawer.drawLayoutWithBombs(points);
        Canvas canvasTop = drawer.drawTopLayout(points);
        canvasTop.toFront();

        canvasTop.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            PointBase pointClicked = getClickedPoint(x, y, points);
            if (event.getButton() == MouseButton.PRIMARY && !pointClicked.hasFlag()) {
                drawer.clearPoint(pointClicked);
                if (pointClicked.hasBomb() && lifeCount == 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game over");
                        alert.setHeaderText(null);
                        alert.setContentText("Вы всё взорвали =(");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            primaryStage.close();
                        }
                }
                if (pointClicked.getNumber() == 0) {
                    drawer.clearOpenedPoints(sag.openEmptyArea(points, pointClicked).stream().filter(p -> p.isOpen()).collect(Collectors.toList()));
                } else {
                    pointClicked.setIsOpen(true);
                }
            }
            if (event.getButton() == MouseButton.SECONDARY && !pointClicked.isOpen()) {
                if (pointClicked.hasFlag()) {
                    drawer.drawDefaultPoint(pointClicked);
                    pointClicked.setHasFlag(false);
                    sag.setMarkedBombs(sag.getMarkedBombs() - 1);
                } else {
                    drawer.drawFlag(pointClicked);
                    pointClicked.setHasFlag(true);
                    sag.setMarkedBombs(sag.getMarkedBombs() + 1);
                }
            }
            if (checkIsWin(points, bombsCount)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Вы выиграли");
                alert.setHeaderText(null);
                alert.setContentText("Вы выиграли. Поздравляем!");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    primaryStage.close();
                }
            }
        });

        root.getChildren().add(canvasBomb);
        root.getChildren().add(canvasTop);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private PointBase getClickedPoint(double x, double y, List<PointBase> points) {
        return points.stream().filter(a -> x >= a.getPositionX()
                && x < a.getPositionX() + a.getWidth()
                && y >= a.getPositionY()
                && y < a.getPositionY() + a.getHeight())
                .collect(Collectors.toList())
                .get(0);
    }

    private boolean checkIsWin(List<PointBase> points, int bombsCount) {
        long pointsWithFlag = points.stream().filter(p -> p.hasFlag()).count();
        long openedBombs = points.stream().filter(p -> p.hasBomb() && p.isOpen()).count();
        return openedBombs + points.stream().filter(p -> p.hasBomb() && p.hasFlag()).count() == bombsCount && openedBombs + pointsWithFlag == bombsCount;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
