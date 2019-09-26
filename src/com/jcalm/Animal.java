package com.jcalm;

import java.awt.*;

public abstract class Animal implements CollisionDetector {

    private boolean predator;
    private byte full;
    private byte velocity;
    private byte direction;
    private boolean dead;
    private Coordinate coord; // skapar aggregat Coordinate till klassen Animal


    //hello
    public Animal() { // def konstruktor

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;
    }

    public Animal(boolean predator, byte velocity) { // konstruktor med inparametrar
        this.velocity = velocity;
        this.predator = predator;
    }

    public void eat() { // metod för att äta

    }

    public void move() { // metod för att röra på djuren

    }

    public Rectangle getBound() { // metod för att få djurens "radie"
        Rectangle radar = new Rectangle();
        return radar;
    }
}


