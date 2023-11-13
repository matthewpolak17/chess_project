package Functionality;

import Color.Colors;

public abstract class Piece {

    private Colors color;
    private int x;
    private int y;
    private String name;
    private int xp;
    private int yp;
    public boolean hasMoved;

    //This variable helps the paint class draw this piece over
    //other pieces while it's being moved
    public int onTop;

    
    public Piece(Colors color, int x, int y, String name) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.name = name;
        hasMoved = false;
        xp = x * 100;
        yp = y * 100;
        onTop = 0;
    }

    public Colors getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXP() {
        return xp;
    }

    public int getYP() {
        return yp;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public void setYP(int yp) {
        this.yp = yp;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isValidMove(Board board, Square start, Square end);

    public abstract String toString();
    

}