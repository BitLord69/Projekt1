package com.jcalm;

import java.awt.*;
import java.util.Random;

public abstract class Animal implements CollisionDetector {
    protected static final int HIT_RATE = 30; // konstant för Cheetah

    protected byte full;
    protected byte velocity;
    protected Coordinate coord; // skapar aggregat Coordinate till klassen Animal
    private boolean predator;
    private boolean dead;

    public Animal() { // def konstruktor
        int x = (int) (Math.random() * BoardFactory.getBoard().getBoardSize());
        int y = (int) (Math.random() * BoardFactory.getBoard().getBoardSize());

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;
        predator = false;
        coord = new Coordinate(x, y);
    } // Animal:Animal

    public Animal(boolean predator, byte velocity) { // konstruktor med inparametrar;
        int x = (int) (Math.random() * BoardFactory.getBoard().getBoardSize());
        int y = (int) (Math.random() * BoardFactory.getBoard().getBoardSize());
        coord = new Coordinate(x, y);

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;

        this.velocity = velocity;
        this.predator = predator;
    } // Animal:Animal

    public abstract void render(Graphics g);

    public boolean eat(Animal dinner) { // metod för att äta *
        return false;
    } // eat

    public void moveRandomly() {

        int deltaX, deltaY;
        Random r = new Random(); // skapar ett objekt av klassen Random
        //
        deltaX = r.nextInt(velocity+velocity) - velocity; // slumpar fram ett x-värde inom koordinat +- velocity
        deltaY = r.nextInt(velocity+velocity) - velocity; // slumpar fram ett värde y-värde inom koordinat +- velocity
        coord.moveDelta(deltaX, deltaY);

        System.out.printf("\t%s%n\t%s - %s, %s: newX = %s, newY = %s%n", "-".repeat(40),
                Board.pimpString("Animal.moveRandomly,", Board.LEVEL_NORMAL),
                Board.pimpString(getClass().getSimpleName(), Board.LEVEL_STRESSED),
                this,
                Board.pimpString(deltaX, Board.LEVEL_INFO),
                Board.pimpString(deltaY, Board.LEVEL_INFO));
    } // moveRandomly

    public void move() { // metod för att röra på djuren
        // TODO: 2019-09-29 När Zerba.move är klar, se om man eventuellt kan flytta upp delar av metoderna hit, eller åtminstone lyfta upp delar i andra metoder
    } // move

    public int getRandomPercentage() { // metod för att slumpa ett tal från 1-100
        return (int) (Math.random() * 100) + 1; // random = ett tal mellan 0-100
    } // getRandomPercentage

    public boolean isPredator() {
        return predator;
    } // isPredator

    public boolean isDead() {
        return dead;
    } // isDead

    public void setDead(boolean dead) {
        this.dead = dead;
        System.out.printf("\t\t\tI %s - %s dog och skickas direkt till himlen!%n", Board.pimpString("Animal.setDead", Board.LEVEL_BOLD), this);
    } // setDead

    public Coordinate getCoord() {
        return coord;
    } // getCoord

    // Implementering av interfacet CollisionDetercor
    public Rectangle getBounds() { // metod för att få djurens "radie"
        return new Rectangle(coord.getX(), coord.getY(), 1, 1);
    } // getBounds

    // Implementering av interfacet CollisionDetercor
    public boolean collide(CollisionDetector cd) {
        return (getBounds().intersects(cd.getBounds()));
    } // collide

    @Override
    public String toString() {
        return String.format("Animal{predator: %b, dead: %b, full: %d, velocity: %d, koordinater: %s}", predator, dead, full, velocity, coord);
    } // toString
} // class Animal


