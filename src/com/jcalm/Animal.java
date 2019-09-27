package com.jcalm;

import java.awt.*;

public abstract class Animal implements CollisionDetector {

    private boolean predator;
    private byte full;
    private byte velocity;
    private byte direction;
    private boolean dead;
    private Coordinate coord; // skapar aggregat Coordinate till klassen Animal
    protected static final int HIT_RATE = 30; // konstant för Cheetah



    //hello
    public Animal() { // def konstruktor

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;
        predator = false;
    }

    public Animal(boolean predator, byte velocity) { // konstruktor med inparametrar
        this.velocity = velocity;
        this.predator = predator;
    }

    public void eat() { // metod för att äta

    }

    public void move() { // metod för att röra på djuren

    }


     public double getRandom() { // metod för att slumpmässa ett tal från 0-100
        int random;
        random = (int)(Math.random() * 100) + 1; // random = ett tal mellan 0-100
        return random;
     }

    public Rectangle getBounds() { // metod för att få djurens "radie"
        Rectangle radar = new Rectangle();
        return radar;
    }
    public boolean isPredator() {
        return predator;
    }
}








