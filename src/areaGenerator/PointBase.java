package areaGenerator;

/**
 * Created by mariya.chernyshova on 13.09.2016.
 */
public abstract class PointBase {
    private final double DEFAULT_POINT_SIZE = 30;
    protected double positionX;
    protected double positionY;
    protected boolean hasBomb;
    protected int number;
    protected boolean isOpen;
    protected boolean hasFlag;
    protected double pHeight;
    protected double pWidth;

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public PointBase(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.hasBomb = false;
        this.number =  0;
        this.isOpen = false;
        this.hasFlag = false;
        this.pHeight = DEFAULT_POINT_SIZE;
        this.pWidth = DEFAULT_POINT_SIZE;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean hasFlag() {
        return hasFlag;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public double getPointHeight() {
        return this.pHeight;
    }

    public double getPointWidth() {
        return this.pWidth;
    }
}
