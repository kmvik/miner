package areaGenerator;

/**
 * Created by mariya.chernyshova on 13.09.2016.
 */
public interface IPoint {
    double getPositionX();
    double getPositionY();
    boolean hasBomb();
    void setHasBomb(boolean hasBomb);
    int getNumber();
    void setNumber(int number);
    boolean isOpen();
    void setIsOpen(boolean isOpen);
}
