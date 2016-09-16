package areaGenerator;

import java.util.List;

/**
 * Created by mariya.chernyshova on 13.09.2016.
 */
public interface IBombGenerator {
    List<PointBase> generateBombs(List<PointBase> points, int countOfBombs);
}
