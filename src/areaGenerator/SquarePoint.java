package areaGenerator;

/**
 * Created by mariya.chernyshova on 13.09.2016.
 */
public class SquarePoint implements IPoint {
    private double _positionX;
    private double _positionY;
    private boolean _hasBomb;
    private int _number;
    private boolean _isOpen;

    public SquarePoint(double positionX, double positionY) {
        _positionX = positionX;
        _positionY = positionY;
        _hasBomb = false;
        _number = 0;
    }

    @Override
    public double getPositionX() {
        return _positionX;
    }

    @Override
    public double getPositionY() {
        return _positionY;
    }

    @Override
    public boolean hasBomb() {
        return _hasBomb;
    }

    @Override
    public void setHasBomb(boolean hasBomb) {
        _hasBomb = hasBomb;
    }

    @Override
    public int getNumber() {
        return _number;
    }

    @Override
    public void setNumber(int number) {
        _number = number;
    }

    @Override
    public boolean isOpen() {
        return _isOpen;
    }

    @Override
    public void setIsOpen(boolean isOpen) {
        _isOpen = isOpen;
    }
}
