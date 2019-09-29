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

    public Rectangle getBounds() { // metod för att få djurens "radie"
        // TODO: 2019-09-29 Skriv kod för att generera ett djurs omgivande rektangel; beror på velocity, dvs hur långt ett djur max kan gå per varv
        Rectangle radar = new Rectangle();
        return radar;
    } // getBounds

    @Override
    public String toString() {
        return String.format("Animal{predator: %b, full: %d, velocity: %d, koordinater: %s%n}", predator,  full, velocity, coord);
    } // toString
} // class Animal