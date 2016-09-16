package areaGenerator;

import java.util.List;
import java.util.Random;

/**
 * Created by mariya.chernyshova on 13.09.2016.
 */
public class RandomizeBombGenerator implements IBombGenerator {
    @Override
    public List<PointBase> generateBombs(List<PointBase> points, int countOfBombs) {
        int pointsCount = points.size();
        Random random = new Random();
        while (countOfBombs > 0) {
            int index = random.nextInt(pointsCount);
            if (!points.get(index).hasBomb()) {
                points.get(index).setHasBomb(true);
                countOfBombs--;
            }
        }
        return points;
    }
}
