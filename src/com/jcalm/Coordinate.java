package com.jcalm;

public class Coordinate {

    private byte x;
    private byte y;

    public Coordinate() {
        x = 0;
        y = 0;
    } // Coordinate:Coordinate

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

        // TODO: 2019-09-29 Lägg in en kontroll så att värdena inte är större än brädets storlek
    } // Coordinate:Coordinate

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public void moveDelta(byte deltaX, byte deltaY) {
        int newX = x - deltaX;
        int newY = y - deltaY;

        // TODO: 2019-09-29 Lägg in kontroller om koodinaterna är under noll eller utanför brädets storlek
    } // moveDelta

    @Override
    public String toString() {
        return String.format("Coordinate{x: %d, y: %x}", x, y);
    } // toString
} // Coordinate



