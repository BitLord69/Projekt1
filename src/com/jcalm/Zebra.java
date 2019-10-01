package com.jcalm;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Zebra extends Animal {

    public Zebra() {
        super(false, Board.MAX_ZEBRA_VELOCITY);
    } // Zebra:Zebra

    public Zebra(byte velocity) {
        super(false, velocity);
    } // Zebra:Zebra

   // TODO: 2019-09-30 10% random move, 90% zebrah-move, OBS ZEBRAH-MOVE ÄR INTE KLAR
    public void move() {

        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();

        // System.out.printf("\t%s%n\tI %sCheetah.move%s - detta djur: %s%n", "-".repeat(30), Board.ANSI_GREEN, Board.ANSI_RESET, this);

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte det egna. Dessutom, kontrollera bara zebror (ändra detta om geparder ska kunna para sig)
            // Lägg upp avstånden i en map så att vi sedan kan sortera den på värdena och hämta ut djuret som är närmast/längst bort
            if (a != this && a.isPredator() && !a.isDead()) {
                // Börja med att kontrollera om geparden kan komma åt en zebra i nuvarande position. Kan bli så om en zerba har flyttats slumpartat

                int deltaX = a.coord.getX() - coord.getX();
                int deltaY = a.coord.getY() - coord.getY();
                double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                distances.put(a, dist);

            } // else
        } // if a...
        // for a...

        // Skippa resten om geparden redan ätit i steg ett

        int moveX, moveY;

        // Sortera inläggen på värdet så att vi enkelt kan hämta ut djuret närmast/längst bort.

        Map<Animal, Double> sorted = distances
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        Animal closestCheetah = sorted.keySet().iterator().next();

        // Hamnar geparden utanför strike zone? I så fall gå så långt som möjligt mot zebran
        if (sorted.get(closestCheetah) >= velocity) {
            // Använd cosinus-satsen för att beräkna nya x och y
            int deltaX = closestCheetah.coord.getX() - coord.getX();
            int deltaY = closestCheetah.coord.getY() - coord.getY();
            double cosV = deltaX / sorted.get(closestCheetah);

            // TODO: 2019-09-29 Om man vill att geparder ska gå slumpartad längd, ändra i formeln nedan
            moveX = (int) Math.round((velocity * cosV));
            moveY = (int) Math.round(Math.tan(Math.acos(cosV)) * velocity * cosV * (deltaY < 0 ? -1 : 1));
            coord.moveDelta(moveX, moveY);

        } // if sorted...
        else {
            moveX = closestCheetah.coord.getX();
            moveY = closestCheetah.coord.getY();


            // if eat...


        } // else

        //System.out.printf("\tAvstånd till närmaste zebra: %s: %f, move(X): %d, move(Y): %d%n", closestZebra, sorted.get(closestZebra), moveX, moveY);
    } // if bHasEaten...
    // move
}


