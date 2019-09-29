package com.jcalm;

import java.awt.*;

public abstract class Animal implements CollisionDetector {
    protected static final int HIT_RATE = 30; // konstant för Cheetah

    private boolean predator;
    private byte full;
    private byte velocity;
    private byte direction;
    private boolean dead;
    private Coordinate coord; // skapar aggregat Coordinate till klassen Animal

    public Animal() { // def konstruktor
        byte x = (byte)(Math.random() * BoardFactory.getBoard().getSize());
        byte y = (byte)(Math.random() * BoardFactory.getBoard().getSize());

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;
        predator = false;
        coord = new Coordinate(x, y);
    } // Anmimal:Animal

    public Animal(boolean predator, byte velocity) { // konstruktor med inparametrar
        this.velocity = velocity;
        this.predator = predator;
    } // Anmimal:Animal

    public void eat() { // metod för att äta

    } // eat

    public void move() { // metod för att röra på djuren

    } // move

    public int getRandomPercentage(){
        // TODO: 2019-09-29 Skriv kod för att generera ett slumptal mellan 1 och 100
        return 10;
    } // getRandomPercentage

     public double getRandom() { // metod för att slumpmässa ett tal från 0-100
        int random;
        random = (int)(Math.random() * 100) + 1; // random = ett tal mellan 0-100
        return random;
     }

    public Rectangle getBounds() { // metod för att få djurens "radie"
        Rectangle radar = new Rectangle();
        return radar;
    } // getBounds

    public boolean isPredator() {
        return predator;
    }
}








    @Override
    public String toString() {
        return String.format("Animal{predator: %b, full: %d, velocity: %d, koordinater: %s%n}", predator,  full, velocity, coord);
    } // toString
} // class Animal