package com.jcalm;

public class Coordinate {

    private byte x;
    private byte y;



    public Coordinate() {}

    public Coordinate(byte x, byte y) {
        if (x > 0) {
            this.x = x;
        }
        else {
            this.x = 0;
        }
        if (y > 0) {
            this.y = y;
        }
        else {
            this.y = 0;
        }
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }
}



