package com.jcalm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Zebra extends Animal {

    public Zebra() {
        super(false, Board.MAX_ZEBRA_VELOCITY);
    } // Zebra:Zebra

    public Zebra(byte velocity) {
        super(false, velocity);
    } // Zebra:Zebra

    public void move() {

        if (getRandomPercentage() < Board.ZEBRA_RANDOM_MOVE_RATIO) { // om det slumpade talet är större än konstanten 30
            moveRandomly(); // gå åt ett slumpartat håll
            return;
        }

        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals(); // hämtar alla djur från spelbrädet
        Map<Animal, Double> distances = new HashMap<>(); // skapar en ny hashmap

        // System.out.printf("\t%s%n\tI %sCheetah.move%s - detta djur: %s%n", "-".repeat(30), Board.ANSI_GREEN, Board.ANSI_RESET, this);

        for (Animal a : animals) {

            if (a != this && a.isPredator() && !a.isDead()) { // om inte detta djuret, och om detta djuret är en predator, och om detta djuret inte är dött så...
                int deltaX = a.coord.getX() - coord.getX();
                int deltaY = a.coord.getY() - coord.getY();
                double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);  // räknar ut avståndet till det aktuella objektet
                distances.put(a, dist); // lägger in avståndet i hashmapen
            }
        }
        int moveX, moveY;


        Map<Animal, Double> sorted = distances // ber hashmapen att sortera alla avstånd
                .entrySet()
                .stream()
                .sorted(comparingByValue()) //
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        Animal closestCheetah = sorted.keySet().iterator().next(); // letar upp den närmsta geparden

        int deltaX = closestCheetah.coord.getX() - coord.getX();
        int deltaY = closestCheetah.coord.getY() - coord.getY();
        double cosV = deltaX / sorted.get(closestCheetah); // hämtar koordinaterna för den närmsta geparden


        moveX = (int) Math.round((velocity * cosV));
        moveY = (int) Math.round(Math.tan(Math.acos(cosV)) * velocity * cosV * (deltaY < 0 ? -1 : 1));
        coord.moveDelta(moveX, moveY); // zebran förflyttar sig

    }



}
