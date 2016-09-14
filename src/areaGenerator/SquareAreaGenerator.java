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
    private List<IPoint> points;
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
    public List<IPoint> generateArea() {
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
        IPoint[][] matrix = new IPoint[_areaHeight][_areaWidth];
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


//        for (int i = 0; i < _areaWidth * _areaHeight - 1; i++) {
//            int diagUpLeftIndex =  i - _areaWidth - 1;
//            int upIndex = i - _areaWidth;
//            int diagUpRightIndex = i - _areaWidth + 1;
//            int leftIndex = i - 1;
//            int rightIndex = i + 1;
//            int diagDownLeftIndex = i + _areaWidth - 1;
//            int downIndex = i + _areaWidth;
//            int diagDownRightIndex = i + _areaWidth + 1;
//            if (points.get(i).hasBomb()) {
//                if (i % _areaWidth > 0) {
//                    if (!points.get(diagUpLeftIndex).hasBomb() && diagUpLeftIndex > 0) {
//                        int currNumber = points.get(diagUpLeftIndex).getNumber();
//                        points.get(diagUpLeftIndex).setNumber(currNumber + 1);
//                    }
//                    if (!points.get(leftIndex).hasBomb() && leftIndex > 0) {
//                        int currNumber = points.get(leftIndex).getNumber();
//                        points.get(leftIndex).setNumber(currNumber + 1);
//                    }
//                    if (!points.get(diagDownLeftIndex).hasBomb() && diagDownLeftIndex > 0) {
//                        int currNumber = points.get(diagDownLeftIndex).getNumber();
//                        points.get(diagDownLeftIndex).setNumber(currNumber + 1);
//                    }
//
//                }
//                if (i < _areaWidth) {
//                    if (!points.get(diagUpRightIndex).hasBomb()) {
//                        int currNumber = points.get(diagUpRightIndex).getNumber();
//                        points.get(diagUpRightIndex).setNumber(currNumber + 1);
//                    }
//                    if (!points.get(rightIndex).hasBomb()) {
//                        int currNumber = points.get(rightIndex).getNumber();
//                        points.get(rightIndex).setNumber(currNumber + 1);
//                    }
//                    if (!points.get(diagDownRightIndex).hasBomb()) {
//                        int currNumber = points.get(diagDownRightIndex).getNumber();
//                        points.get(diagDownRightIndex).setNumber(currNumber + 1);
//                    }
//                }
//                if (upIndex > 0 && !points.get(upIndex).hasBomb()) {
//                    int currNumber = points.get(upIndex).getNumber();
//                    points.get(upIndex).setNumber(currNumber + 1);
//                }
//                if (downIndex < points.size() && !points.get(downIndex).hasBomb()) {
//                    int currNumber = points.get(downIndex).getNumber();
//                    points.get(downIndex).setNumber(currNumber + 1);
//                }
//            }
//        }
    }
}
