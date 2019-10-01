package com.jcalm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Zebra extends Animal {

    public Zebra() {
        super(false, Board.MAX_ZEBRA_VELOCITY);
    } // Zebra:Zebra

    public Zebra(byte velocity) {
        super(false, velocity);
    } // Zebra:Zebra

    public void move() {
        // Kolla om zebran ska flytta sig slumpartat
        if (getRandomPercentage() < Board.ZEBRA_RANDOM_MOVE_RATIO) {
            moveRandomly();
            return;
        } // if getRandomPercentage...

        //  moveRandom();
        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte det egna. Dessutom, kontrollera bara zebror (ändra detta om geparder ska kunna para sig)
            // Lägg upp avstånden i en map så att vi sedan kan sortera den på värdena och hämta ut djuret som är närmast/längst bort
            if (a != this && a.isPredator() && !a.isDead()) {

            }
        }


    }
}