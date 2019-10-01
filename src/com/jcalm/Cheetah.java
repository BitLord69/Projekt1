package com.jcalm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Cheetah extends Animal {
    private byte foodComaCounter = 0;

    public Cheetah() { // default konstruktor
        super(true, Board.MAX_CHEETAH_VELOCITY);
    } // Cheetah:Cheetah*

    public Cheetah(byte velocity) { // skapar en konstruktor med en byte som inparameter, sätter boolean predator till true och skickar dem till Animal
        super(true, velocity);
    } // Cheetah:Cheetah

    private void moveToClosest(Map<Animal, Double> distances) {
        int moveX, moveY;

        // Sortera inläggen på värdet så att vi enkelt kan hämta ut djuret närmast/längst bort.
        Map<Animal, Double> sorted = distances
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        Animal closestZebra = sorted.keySet().iterator().next();

        // Hamnar geparden utanför strike zone? I så fall gå så långt som möjligt mot zebran
        if (sorted.get(closestZebra) >= velocity) {
            // Använd cosinus-satsen för att beräkna nya x och y
            int deltaX = closestZebra.coord.getX() - coord.getX();
            int deltaY = closestZebra.coord.getY() - coord.getY();
            double cosV = deltaX / sorted.get(closestZebra);

            // TODO: 2019-09-29 Om man vill att geparder ska gå slumpartad längd, ändra i formeln nedan
            moveX = (int) Math.round((velocity * cosV));
            moveY = (int) Math.round(Math.tan(Math.acos(cosV)) * velocity * cosV * (deltaY < 0 ? -1 : 1));
            coord.moveDelta(moveX, moveY);
            starve();

            System.out.printf("\tI %s, %s: Avstånd till närmaste zebra: %s: %f, move(X): %d, move(Y): %d%n",
                    Board.pimpString("Cheetah.moveToClosest", Board.LEVEL_NORMAL), this.getCoord(), closestZebra, sorted.get(closestZebra), moveX, moveY);
        } // if sorted...
        else {
            System.out.printf("\tI %s, försöker äta zebra: %s: %f%n", Board.pimpString("Cheetah.moveToClosest", Board.LEVEL_NORMAL), closestZebra, sorted.get(closestZebra));
            if (!hadSnack(closestZebra)) {
                starve();
            } // if !hadSnack...
        } // else
    } // moveToClosest

    @Override
    public Rectangle getBounds() {
        Coordinate coNy = new Coordinate(coord.getX() - velocity / 2, coord.getY() - velocity / 2);

        Rectangle radar = new Rectangle(coNy.getX(), coNy.getY(), velocity, velocity);
        return radar;
    } // getBounds

    private boolean hadSnack(Animal a) {
        // Kollar ifall det är en kollision och om så är fallet, om geparden fick sig en liten munsbit
        boolean didEat = false;

        // Är geparden inom räckhåll?
        if (collide(a)) {
            int tempX = a.coord.getX();
            int tempY = a.coord.getY();

            // Lyckas geparden slå bytet?
            if (eat(a)) {
                coord.setX(tempX);
                coord.setY(tempY);
                didEat = true;
                System.out.printf("\t\tI %s - åt %s%n", Board.pimpString("Cheetah.hadSnack", Board.LEVEL_BOLD), a);
            } // if eat...
            else
            // Djuren står nära varandra ruta, men geparden slog inte bytet -> bytet panikhoppar iväg
            {
                System.out.printf("\t\tI %s - lyckades %s äta %s%n", Board.pimpString("Cheetah.hadSnack", Board.LEVEL_BOLD), Board.pimpString("inte", Board.LEVEL_STRESSED), a);
                a.moveRandomly();
            } // else
        } // if collide...

        return didEat;
    } // hadSnack

    public void move() {
        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();
        boolean hasEaten = false;
        boolean movedRandomly = false;

        // Om geparden redan är mätt, minska mättnaden och hoppa ut direkt
        if (foodComaCounter > 0) {
            foodComaCounter--;
            System.out.printf("\t%s%n\tI %s - detta djurs foodComaCounter = %d%n", "-".repeat(30), Board.pimpString("Cheetah.move", Board.LEVEL_NORMAL), foodComaCounter);
            return;
        } // if full...

        System.out.printf("\t%s%n\tI %s - detta djur: %s%n", "-".repeat(30), Board.pimpString("Cheetah.move", Board.LEVEL_NORMAL), this);

        // Kolla om geparden ska flytta sig slumpartat
        if (getRandomPercentage() < Board.CHEETAH_RANDOM_MOVE_RATIO) {
            moveRandomly();
            movedRandomly = true;
        } // if getRandomPercentage...

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte det egna. Dessutom, kontrollera bara zebror (ändra detta om geparder ska kunna para sig)
            // Lägg upp avstånden i en map så att vi sedan kan sortera den på värdena och hämta ut djuret som är närmast/längst bort
            if (a != this && !a.isPredator() && !a.isDead()) {
                // Börja med att kontrollera om geparden kan komma åt en zebra i nuvarande position. Kan bli så om en zerba har flyttats slumpartat
                if (hadSnack(a)) {
                    hasEaten = true;
                    break;
                } // if hadSnack...
                else if (!movedRandomly) { // Lägg upp avstånden i en hashmap ifall geparden inte redan har förflyttat sig slumpartat
                    int deltaX = a.coord.getX() - coord.getX();
                    int deltaY = a.coord.getY() - coord.getY();
                    double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    distances.put(a, dist);
                } // else
            } // if a...
        } // for a...

        // Skippa resten om geparden redan ätit i steg ett, eller flyttat sig slumpartat
        if (!hasEaten && !movedRandomly) {
            moveToClosest(distances);
        } // if !hasEaten...
    } // move

    private void starve() {
        full -= Board.CHEETAH_STARVATION_RATE;
        if (full < 0)
            setDead(true);
    } // starve

    @Override
    public boolean eat(Animal dinner) {
        boolean killed = getRandomPercentage() < HIT_RATE; // formel för lyckad jakt istället för true

        System.out.printf("\t\tI %s - %s vill äta %s, lyckades döda: %b%n", Board.pimpString("Cheetah.eat", Board.LEVEL_BOLD), this, dinner, killed);
        if (killed) {
            full = 100;
            foodComaCounter = Board.MAX_COMA_COUNTER;
            BoardFactory.getBoard().killAnimal(dinner);
        } // if killed...
        return killed;
    } // eat
} // class Cheetah