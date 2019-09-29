package com.jcalm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Cheetah extends Animal {
    private byte comaCounter = 0;

    public Cheetah() { // default konstruktor
        super(true, Board.MAX_CHEETAH_VELOCITY);
    } // Cheetah:Cheetah

    public Cheetah(byte velocity) { // skapar en konstruktor med en byte som inparameter, sätter boolean predator till true och skickar dem till Animal
        super(true, velocity);
    }

    public void move() {
        super.move();
        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();
        boolean bHasEaten = false;

        System.out.printf("-----------------%nI Cheetah.move - detta djur: %s%n", this);

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte detta! Dessutom, kontrollera bara zebror (ändra detta om geparder ska kunna para sig)
            // Lägg upp den i en map så att vi sedan kan sortera den på värdena och hämnta ut djuret som är närmast/längst bort
            if (a != this && !a.isPredator()) {
                // Börja med att kontrollera om geparden kan komma åt en zebra i nuvarande position
                if (collide(a)) {
                    System.out.printf("Kollision!!!!!!!!!!!!!!!!!!!!!!!!%n %s, %s", this, a);

                    int tempX = a.coord.getX();
                    int tempY = a.coord.getY();

//                    if (eat(a)) {
                        coord.setX(tempX);
                        coord.setY(tempY);
                        bHasEaten = true;
//                        break;
//                    } // if eat...
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
                double cosV = deltaX / sorted.get(firstKey);

                moveX = (int) (velocity * cosV);
                moveY = (int) (Math.tan(Math.acos(cosV)) * moveX);
                coord.moveDelta(moveX, moveY);
            } // if sorted...
            else {
                moveX = firstKey.coord.getX();
                moveY = firstKey.coord.getY();
                System.out.printf("Inom strike zone!!!!!!!!!! %s%n", firstKey);

//                if (eat(firstKey)) {
                    coord.setX(moveX);
                    coord.setY(moveY);
//                } // if eat...
            } // else

            System.out.printf("Djurlistan: %s%nAvstånd till närmaste zebra: %s, %s, move(X): %d, move(Y): %d%n", sorted, firstKey, sorted.get(firstKey), moveX, moveY);
        } // if bHasEaten...
        else {
            System.out.printf("Burp - det var gott med mat!", this);
        } // else
    } // move

    @Override
    public boolean eat(Animal dinner) {
        // TODO: 2019-09-29 Kolla om geoparden lyckas döda bytet, och i så fall ta bort det ur listan
        System.out.printf("I Cheetah.eat!%s, %s%n", this, dinner);
        return super.eat(dinner);
    }
}
