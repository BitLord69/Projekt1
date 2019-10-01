package com.jcalm;

import java.awt.*;
import java.util.Random;

public abstract class Animal implements CollisionDetector {
    protected static final int HIT_RATE = 30; // konstant för Cheetah

    protected byte full;
    protected byte velocity;
    protected Coordinate coord; // skapar aggregat Coordinate till klassen Animal
    private boolean predator;
    private byte direction;
    private boolean dead;

    public Animal() { // def konstruktor
        int x = (int) (Math.random() * BoardFactory.getBoard().getSize());
        int y = (int) (Math.random() * BoardFactory.getBoard().getSize());

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;
        predator = false;
        coord = new Coordinate(x, y);
    } // Animal:Animal

    public Animal(boolean predator, byte velocity) { // konstruktor med inparametrar;
        int x = (int) (Math.random() * BoardFactory.getBoard().getSize());
        int y = (int) (Math.random() * BoardFactory.getBoard().getSize());
        coord = new Coordinate(x, y);

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;

        this.velocity = velocity;
        this.predator = predator;
    } // Animal:Animal

    public boolean eat(Animal dinner) { // metod för att äta *
        // TODO: 2019-09-29 Kontrollera mha procentkoll om bytet äts.
        return false;
    } // eat

    public void moveRandomly() {

        int x = coord.getX();
        int y = coord.getY();

        int xMin = x - velocity;
        int xMax = x + velocity; // skapar två x-koordinatsvariabler
        int yMin = y - velocity;
        int yMax = y + velocity; // skapar två y-koordinatsvariabler
        Random r = new Random(); // skapar ett objekt av klassen Random

        int newCoordinateX = r.nextInt(xMax - xMin) + xMin; // initierar en ny x-koordinat och ger den värdet av det slumpade talet
        int newCoordinateY = r.nextInt(yMax - yMin) + yMin; // initierar en ny y-koordinat och ger den värdet av det slumpade talet

        coord.setX(newCoordinateX); // sätter det nuvarande objektets x-värde till det av den slumpade x-koordinaten
        coord.setY(newCoordinateY); // sätter det nuvarande objektets y-värde till det av den slumpade y-koordinaten
        System.out.printf("\t\t%s - %s, %s: newX = %d, newY = %d%n", Board.pimpString("Animal.moveRandomly,", Board.LEVEL_NORMAL), Board.pimpString(getClass().getSimpleName(), Board.LEVEL_STRESSED ),this, newCoordinateX, newCoordinateY);
    } // moveRandomly

    public void move() { // metod för att röra på djuren
        // TODO: 2019-09-29 När Zerba.move är klar, se om man eventuellt kan flytta upp delar av metoderna hit, eller åtminstone lyfta upp delar i andra metoder
/*
        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();
        boolean bHasEaten = false;

        System.out.printf("-----------------%nI Animal.move - detta djur:%s%n", this);

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte detta! Dessutom, checka bara av andra typen av djur (om predator, kolla bytesdjur och vice versa)
            // Ändra detta om djuren ska kunna para sig om de hamnar på samma ruta
            // Lägg upp den i en map så att vi sedan kan sortera den på värdena och hämnta ut djuret som är närmast/längst bort
            if (a != this && ((isPredator() && !a.isPredator()) || !isPredator() && a.isPredator())) {
                // Börja med att kontrollera om det är en gepard och det kan komma åt en zebra i nuvarande position
                if (isPredator() && collide(a)) {
                    int tempX = a.coord.getX();
                    int tempY = a.coord.getY();

                    if (eat(a)) {
                        coord.setX(tempX);
                        coord.setY(tempY);
                        bHasEaten = true;
                        break;
                    } // if eat...
                } // if isPredator...
                else {
                    int deltaX = a.coord.getX() - coord.getX();
                    int deltaY = a.coord.getY() - coord.getY();
                    double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    distances.put(a, dist);
                } // else
            } // if a...
        } // for a...

        // Skippa resten om geparden redan ätit i steg ett
        if (!bHasEaten) {
            int moveX, moveY;

            // Sortera inläggen på värdet så att vi enkelt kan hämta ut djuret närmast/längst bort.
            Map<Animal, Double> sorted = distances
                    .entrySet()
                    .stream()
                    .sorted(isPredator() ? comparingByValue() : Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            Animal firstKey = sorted.keySet().iterator().next();

            // Hamnar geparden utanför strike zone? I så fall gå så långt som möjligt mot zebran
            if (sorted.get(firstKey) >= velocity) {
                // Använd cosinus-satsen för att beräkna nya x och y
                int deltaX = firstKey.coord.getX() - coord.getX();
                double cosV = deltaX / sorted.get(firstKey);

                moveX = (int) (velocity * cosV);
                moveY = (int) (Math.tan(Math.acos(cosV)) * moveX);
                coord.moveDelta(moveX, moveY);
            } // if sorted...
            else {
                moveX = firstKey.coord.getX();
                moveY = firstKey.coord.getY();

                if (eat(firstKey)) {
                    coord.setX(moveX);
                    coord.setY(moveY);
                } // if eat...
            } // else

            System.out.printf("Djurlistan: %s%nAvstånd till %s: %s, moveX: %d, moveY: %d%n", sorted, isPredator() ? "närmaste zebra" : "geparden längst bort", sorted.get(firstKey), moveX, moveY);
        } // if bHasEaten...
        else {
            System.out.printf("Burp - det var gott med mat!", this);
        } */
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
        Rectangle radar = new Rectangle(coord.getX(), coord.getY(), 1, 1);
        return radar;
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