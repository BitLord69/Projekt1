package com.jcalm;

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
    } // Cheetah:Cheetah

    public Cheetah(byte velocity) { // skapar en konstruktor med en byte som inparameter, sätter boolean predator till true och skickar dem till Animal
        super(true, velocity);
    } // Cheetah:Cheetah

    public void move() {
        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();
        boolean bHasEaten = false;

        // Om geparden redan är mätt, minska mättnaden och hoppa ut direkt
        if (foodComaCounter > 0) {
            foodComaCounter--;
            return;
        } // if full...

        System.out.printf("\t%s%n\tI %sCheetah.move%s - detta djur: %s%n", "-".repeat(30), Board.ANSI_GREEN, Board.ANSI_RESET, this);

        // TODO: 2019-09-29 Lägg in slumpvald flytt

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte det egna. Dessutom, kontrollera bara zebror (ändra detta om geparder ska kunna para sig)
            // Lägg upp avstånden i en map så att vi sedan kan sortera den på värdena och hämta ut djuret som är närmast/längst bort
            if (a != this && !a.isPredator() && !a.isDead()) {
                // Börja med att kontrollera om geparden kan komma åt en zebra i nuvarande position. Kan bli så om en zerba har flyttats slumpartat
                if (collide(a)) {
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
                    .sorted(comparingByValue())
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            Animal firstKey = sorted.keySet().iterator().next();

            // Hamnar geparden utanför strike zone? I så fall gå så långt som möjligt mot zebran
            if (sorted.get(firstKey) >= velocity) {
                // Använd cosinus-satsen för att beräkna nya x och y
                int deltaX = firstKey.coord.getX() - coord.getX();
                int deltaY = firstKey.coord.getY() - coord.getY();
                double cosV = deltaX / sorted.get(firstKey);

                // TODO: 2019-09-29 Om man vill att geparder ska gå slumpartad längd, ändra i formeln nedan
                moveX = (int) Math.round((velocity * cosV));
                moveY = (int) Math.round(Math.tan(Math.acos(cosV)) * velocity * cosV * (deltaY < 0 ? -1 : 1));
                coord.moveDelta(moveX, moveY);
                starve();
            } // if sorted...
            else {
                moveX = firstKey.coord.getX();
                moveY = firstKey.coord.getY();

                if (eat(firstKey)) {
                    coord.setX(moveX);
                    coord.setY(moveY);
                } // if eat...
                else
                    starve();
            } // else

            System.out.printf("\tAvstånd till närmaste zebra: %s: %f, move(X): %d, move(Y): %d%n", firstKey, sorted.get(firstKey), moveX, moveY);
        } // if bHasEaten...
    } // move

    private void starve() {
        full -= Board.CHEETAH_STARVATION_RATE;
        if (full < 0)
            setDead(true);
    } // starve

    @Override
    public boolean eat(Animal dinner) {
        // TODO: 2019-09-29 Kolla om geoparden lyckas döda bytet - just nu lyckas de alltid.
        boolean killed = true; // formel för lyckad jakt istället för true

        System.out.printf("\t\tI %sCheetah.eat!%s %s vill äta %s%n", Board.ANSI_RED, Board.ANSI_RESET, this, dinner);
        if (killed) {
            full = 100;
            foodComaCounter = Board.MAX_COMA_COUNTER;
            BoardFactory.getBoard().killAnimal(dinner);
        } // if killed...
        return killed;
    } // eat
} // class Cheetah