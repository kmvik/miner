package areaGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariya.chernyshova on 14.09.2016.
 */
public class SquareAreaGenerator implements IAreaGenerator {
    private int areaHeight;
    private int areaWidth;
    private double areaHeightPx;

    public double getAreaWidthPx() {
        return areaWidthPx;
    }

    private double areaWidthPx;
    private List<PointBase> points;
    private IBombGenerator bombGenerator;
    private int _bombsCount;


    public SquareAreaGenerator(int areaHeight, int areaWidth, int bombsCount) {
        this.areaHeight = areaHeight;
        this.areaWidth = areaWidth;
        areaHeightPx = this.areaHeight * (heightPoint + interval);
        areaWidthPx = this.areaWidth * (widthPoint + interval);
        points = new ArrayList<>();
        bombGenerator = new RandomizeBombGenerator();
        _bombsCount = bombsCount;
    }

    public double getAreaHeightPx() {
        return areaHeightPx;
    }

    @Override
    public List<PointBase> generateArea() {
        fillPositionsPoints();
        bombGenerator.generateBombs(points, _bombsCount);
        fillNumbers();
        return points;
    }

    @Override
    public void fillPositionsPoints() {
        for (int i = 0; i < areaWidthPx; i++) {
            for (int j = 0; j < areaHeightPx; j++) {
                if (i % (widthPoint + interval) == 0 && j % (heightPoint + interval) == 0) {
                    points.add(new SquarePoint(i, j));
                }
            }
        }
    }

    private PointBase[][] convertToArray(List<PointBase> pointsList) {
        PointBase[][] pointsArray = new PointBase[areaHeight][areaWidth];
        int k = 0;
        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                pointsArray[i][j] = pointsList.get(k);
                k++;
            }
        }
        return pointsArray;
    }

    private List<PointBase> convertToPointsList(PointBase[][] pointsArray) {
        List<PointBase> pointsList = new ArrayList<>();
        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                pointsList.add(pointsArray[i][j]);
            }
        }
        return pointsList;
    }

    @Override
    public void fillNumbers() {
        PointBase[][] pointsArray = convertToArray(points);

        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                if (pointsArray[i][j].hasBomb()) {
                    if (j - 1 >= 0) {
                        if (i-1 >= 0) {
                            pointsArray[i-1][j-1].setNumber(pointsArray[i-1][j-1].getNumber() + 1);
                        }
                        pointsArray[i][j-1].setNumber(pointsArray[i][j-1].getNumber() + 1);
                        if (i+1 < areaHeight) {
                            pointsArray[i+1][j-1].setNumber(pointsArray[i+1][j-1].getNumber() + 1);
                        }
                    }
                    if (j + 1 < areaWidth) {
                        if (i-1 >= 0) {
                            pointsArray[i - 1][j + 1].setNumber(pointsArray[i - 1][j + 1].getNumber() + 1);
                        }
                        pointsArray[i][j+1].setNumber(pointsArray[i][j+1].getNumber() + 1);
                        if (i+1 < areaHeight) {
                            pointsArray[i+1][j+1].setNumber(pointsArray[i+1][j+1].getNumber() + 1);
                        }
                    }
                    if (i - 1 >= 0) {
                        pointsArray[i-1][j].setNumber(pointsArray[i-1][j].getNumber() + 1);
                    }
                    if (i + 1 < areaHeight) {
                        pointsArray[i+1][j].setNumber(pointsArray[i+1][j].getNumber() + 1);
                    }
                }
            }
        }

        points = convertToPointsList(pointsArray);
    }

    @Override
    public List<PointBase> openEmptyArea(List<PointBase> points, PointBase currPoint) {
        PointBase[][] pointsArray = convertToArray(points);
        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                if (pointsArray[i][j].equals(currPoint)) {
                    openPoints(i, j, pointsArray);
                }
            }
        }
        return convertToPointsList(pointsArray);
    }

    private void openNumber(PointBase point) {
        if (point.getNumber() > 0) {
            point.setIsOpen(true);
        }
    }

    private void openPoints(int i, int j, PointBase[][] pointsArray) {
        if (pointsArray[i][j].getNumber() == 0 && !pointsArray[i][j].isOpen() && !pointsArray[i][j].hasBomb()) {
            pointsArray[i][j].setIsOpen(true);
            if (j - 1 >= 0) {
                if (i-1 >= 0) {
                    openNumber(pointsArray[i-1][j-1]);
                    openPoints(i-1, j-1, pointsArray);
                }
                openNumber(pointsArray[i][j-1]);
                openPoints(i, j-1, pointsArray);
                if (i+1 < areaHeight) {
                    openNumber(pointsArray[i+1][j-1]);
                    openPoints(i+1, j-1, pointsArray);
                }
            }
            if (j + 1 < areaWidth) {
                if (i-1 >= 0) {
                    openNumber(pointsArray[i-1][j+1]);
                    openPoints(i-1, j+1, pointsArray);
                }
                openNumber(pointsArray[i][j+1]);
                openPoints(i, j+1, pointsArray);
                if (i+1 < areaHeight) {
                    openNumber(pointsArray[i+1][j+1]);
                    openPoints(i+1, j+1, pointsArray);
                }
            }
            if (i - 1 >= 0) {
                openNumber(pointsArray[i-1][j]);
                openPoints(i-1, j, pointsArray);
            }
            if (i + 1 < areaHeight) {
                openNumber(pointsArray[i+1][j]);
                openPoints(i+1, j, pointsArray);
            }
//
//            pointsArray[i-1][j-1].setIsOpen(true);
//            openPoints(i-1, j-1, pointsArray);
//
//            pointsArray[i][j-1].setIsOpen(true);
//            openPoints(i, j-1, pointsArray);
//
//            pointsArray[i+1][j-1].setIsOpen(true);
//            openPoints(i+1, j-1, pointsArray);
//
//            pointsArray[i-1][j].setIsOpen(true);
//            openPoints(i-1, j, pointsArray);
//
//            pointsArray[i+1][j].setIsOpen(true);
//            openPoints(i+1, j, pointsArray);
//
//            pointsArray[i-1][j+1].setIsOpen(true);
//            openPoints(i-1, j+1, pointsArray);
//
//
//            pointsArray[i][j+1].setIsOpen(true);
//            openPoints(i, j+1, pointsArray);
//
//            pointsArray[i+1][j+1].setIsOpen(true);
//            openPoints(i+1, j+1, pointsArray);
        }
    }
}
