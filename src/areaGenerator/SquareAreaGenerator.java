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

    @Override
    public void fillNumbers() {
        PointBase[][] matrix = new PointBase[areaHeight][areaWidth];
        int k = 0;
        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                matrix[i][j] = points.get(k);
                k++;
            }
        }

        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                if (matrix[i][j].hasBomb()) {
                    if (j - 1 >= 0) {
                        if (i-1 >= 0) {
                            matrix[i-1][j-1].setNumber(matrix[i-1][j-1].getNumber() + 1);
                        }
                        matrix[i][j-1].setNumber(matrix[i][j-1].getNumber() + 1);
                        if (i+1 < areaHeight) {
                            matrix[i+1][j-1].setNumber(matrix[i+1][j-1].getNumber() + 1);
                        }
                    }
                    if (j + 1 < areaWidth) {
                        if (i-1 >= 0) {
                            matrix[i - 1][j + 1].setNumber(matrix[i - 1][j + 1].getNumber() + 1);
                        }
                        matrix[i][j+1].setNumber(matrix[i][j+1].getNumber() + 1);
                        if (i+1 < areaHeight) {
                            matrix[i+1][j+1].setNumber(matrix[i+1][j+1].getNumber() + 1);
                        }
                    }
                    if (i - 1 >= 0) {
                        matrix[i-1][j].setNumber(matrix[i-1][j].getNumber() + 1);
                    }
                    if (i + 1 < areaHeight) {
                        matrix[i+1][j].setNumber(matrix[i+1][j].getNumber() + 1);
                    }
                }
            }
        }

        points = new ArrayList<>();
        for (int i = 0; i < areaHeight; i++) {
            for (int j = 0; j < areaWidth; j++) {
                points.add(matrix[i][j]);
            }
        }
    }

    @Override
    public void openEmptyArea(List<PointBase> points, PointBase currPoint) {

    }
}
