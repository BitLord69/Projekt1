package com.jcalm;

public class Coordinate {

    private int x;
    private int y;

    public Coordinate() {
        x = 0;
        y = 0;
    } // Coordinate:Coordinate

    public Coordinate(int x, int y) {
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
        int newX = x + deltaX;
        int newY = y + deltaY;

        // TODO: 2019-09-29 Lägg in kontroller om koodinaterna är under noll eller utanför brädets storlek
        x = newX;
        y = newY;
    } // moveDelta

    public String toString() {
        return String.format("Coordinate{x: %d, y: %d}", x, y);
    } // toString
} // Coordinate



