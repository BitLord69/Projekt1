package com.jcalm;

import java.awt.*;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public abstract class Animal implements CollisionDetector {
    protected static final int HIT_RATE = 30; // konstant för Cheetah

    private boolean predator;
    private byte full;
    private byte velocity;
    private byte direction;
    private boolean dead;
    private Coordinate coord; // skapar aggregat Coordinate till klassen Animal

    public Animal() { // def konstruktor
        int x = (int) (Math.random() * BoardFactory.getBoard().getSize());
        int y = (int) (Math.random() * BoardFactory.getBoard().getSize());

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;
        predator = false;
        coord = new Coordinate(x, y);
    } // Animal:Animal

    public Animal(boolean predator, byte velocity) { // konstruktor med inparametrar
        int size = BoardFactory.getBoard().getSize();
        int x = (int) (Math.random() * size);
        int y = (int) (Math.random() * size);

        dead = false; // sätter dead till false så djuren man skapar inte är döda direkt
        full = 100;
        coord = new Coordinate(x, y);

        this.velocity = velocity;
        this.predator = predator;
    } // Anmimal:Animal

    public void eat() { // metod för att äta
    } // eat

    public void move() { // metod för att röra på djuren
        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte detta! Dessutom, checka bara av andra typen av djur (om predator, kolla bytesdjur och vice versa)
            // Ändra detta om djuren ska kunna para sig om de hamnar på samma ruta
            // Lägg upp den i en map så att vi sedan kan sortera den på värdena och hämnta ut djuret som är närmast/längst bort
            if (a != this && ((isPredator() && !a.isPredator()) || !isPredator() && a.isPredator())) {
                int deltaX = a.coord.getX() - coord.getX();
                int deltaY = a.coord.getY() - coord.getY();
                double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                distances.put(a, dist);
            } // if a...

            if (a.isPredator()) {
            }
        } // for a...

        // Sortera inläggen på värdet så att vi enkelt  kan hämta ut den djuret närmast/längst bort.
        Map<Animal, Double> sorted = distances
                .entrySet()
                .stream()
                .sorted(isPredator() ? comparingByValue() : Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        Animal firstKey = sorted.keySet().iterator().next();

        int deltaX = firstKey.coord.getX() - coord.getX();
        int deltaY = firstKey.coord.getY() - coord.getY();
        double cosV = deltaX / sorted.get(firstKey);

        int moveX = (int)(velocity * cosV);
        int moveY = (int)(Math.tan(Math.acos(cosV)) * moveX);

        System.out.printf("-----------------%nI Animal.move - detta djur:%s%n%s%nNärmast/längst bort: %s%n%s, moveX: %d, moveY: %d%n", this, sorted, firstKey, sorted.get(firstKey), moveX, moveY);
    } // move

    public double getRandomPercentage() { // metod för att slumpa ett tal från 1-100
        int random;
        random = (int) (Math.random() * 100) + 1; // random = ett tal mellan 0-100
        return random;
    } // getRandomPercentage

    public Rectangle getBounds() { // metod för att få djurens "radie"
        // TODO: 2019-09-29 Lägg till så att rektangeln täcker in hur lågt djuret kan gå per runda (x och y hamnar då i mitten!)
        Rectangle radar = new Rectangle(coord.getX(), coord.getY());
        return radar;
    } // getBounds

    public boolean isPredator() {
        return predator;
    } // isPredator

    @Override
    public String toString() {
        return String.format("Animal{predator: %b, full: %d, velocity: %d, koordinater: %s}", predator, full, velocity, coord);
    } // toString
} // class Animal