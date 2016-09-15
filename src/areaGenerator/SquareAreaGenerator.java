package areaGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariya.chernyshova on 14.09.2016.
 */
public class SquareAreaGenerator implements IAreaGenerator {
    private int _areaHeight;
    private int _areaWidth;
    private double heightPx;
    private double widthPx;
    private List<PointBase> points;
    private IBombGenerator bombGenerator;
    private int _bombsCount;


    public SquareAreaGenerator(int areaHeight, int areaWidth, int bombsCount) {
        _areaHeight = areaHeight;
        _areaWidth = areaWidth;
        heightPx = _areaHeight * (heightPoint + interval);
        widthPx = _areaWidth * (widthPoint + interval);
        points = new ArrayList<>();
        bombGenerator = new RandomizeBombGenerator();
        _bombsCount = bombsCount;
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
        for (int i = 0; i < widthPx; i++) {
            for (int j = 0; j < heightPx; j++) {
                if (i % (widthPoint + interval) == 0 && j % (heightPoint + interval) == 0) {
                    points.add(new SquarePoint(i, j));
                }
            }
        }
    }

    @Override
    public void fillNumbers() {
        PointBase[][] matrix = new PointBase[_areaHeight][_areaWidth];
        int k = 0;
        for (int i = 0; i < _areaHeight; i++) {
            for (int j = 0; j < _areaWidth; j++) {
                matrix[i][j] = points.get(k);
                k++;
            }
        }

        for (int i = 0; i < _areaHeight; i++) {
            for (int j = 0; j < _areaWidth; j++) {
                if (matrix[i][j].hasBomb()) {
                    if (j - 1 >= 0) {
                        if (i-1 >= 0) {
                            matrix[i-1][j-1].setNumber(matrix[i-1][j-1].getNumber() + 1);
                        }
                        matrix[i][j-1].setNumber(matrix[i][j-1].getNumber() + 1);
                        if (i+1 < _areaHeight) {
                            matrix[i+1][j-1].setNumber(matrix[i+1][j-1].getNumber() + 1);
                        }
                    }
                    if (j + 1 < _areaWidth) {
                        if (i-1 >= 0) {
                            matrix[i - 1][j + 1].setNumber(matrix[i - 1][j + 1].getNumber() + 1);
                        }
                        matrix[i][j+1].setNumber(matrix[i][j+1].getNumber() + 1);
                        if (i+1 < _areaHeight) {
                            matrix[i+1][j+1].setNumber(matrix[i+1][j+1].getNumber() + 1);
                        }
                    }
                    if (i - 1 >= 0) {
                        matrix[i-1][j].setNumber(matrix[i-1][j].getNumber() + 1);
                    }
                    if (i + 1 < _areaHeight) {
                        matrix[i+1][j].setNumber(matrix[i+1][j].getNumber() + 1);
                    }
                }
            }
        }

        points = new ArrayList<>();
        for (int i = 0; i < _areaHeight; i++) {
            for (int j = 0; j < _areaWidth; j++) {
                points.add(matrix[i][j]);
            }
        }
    }

    @Override
    public void openEmptyArea(List<PointBase> points, PointBase currPoint) {

    }
}
