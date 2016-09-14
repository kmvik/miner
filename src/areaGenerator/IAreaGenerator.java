package areaGenerator;

import java.util.List;

/**
 * Created by mariya.chernyshova on 14.09.2016.
 */
public interface IAreaGenerator {
    public double interval = 1;
    public double heightPoint = 30;
    public double widthPoint = 30;
    List<IPoint> generateArea();
    void fillPositionsPoints();
    void fillNumbers();
}
