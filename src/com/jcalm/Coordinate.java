package com.jcalm;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate() {
        x = 0;
        y = 0;
    } // Coordinate:Coordinate

    public Coordinate(int x, int y) {
        int size = BoardFactory.getBoard().getBoardSize();

        if (x > 0) {
            if (x > size)
                this.x = size - 1;
            else {
                this.x = x;
            }
        } else {
            this.x = 0;
        }

        if (y > 0) {
            if (y > size)
                this.y = size - 1;
            else {
                this.y = y;
            }
        } else {
            this.y = 0;
        }
    } // Coordinate:Coordinate

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

    public void moveDelta(int deltaX, int deltaY) {
        int size = BoardFactory.getBoard().getBoardSize();

        int newX = x + deltaX;
        int newY = y + deltaY;

        if (newX < 0) {
            newX = Math.abs(newX);
        } else if (newX > size - 1)
            newX = size - newX % size;

        if (newY < 0) {
            newY = Math.abs(newY);
        } else if (newY > size - 1)
            newY = size - newY % size;

        x = newX;
        y = newY;
    } // moveDelta

    public String toString() {
        return String.format("Coordinate{x: %s, y: %s}", Board.pimpString(x, Board.LEVEL_INFO), Board.pimpString(y, Board.LEVEL_INFO));
    } // toString
} // Coordinate



