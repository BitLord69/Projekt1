package com.jcalm;

import java.awt.*;

public class Cheetah extends Animal {
    private byte comaCounter = 0;

    public Cheetah() { // default konstruktor
        super(true, Board.MAX_CHEETAH_VELOCITY);
    } // Cheetah:Cheetah

    public Cheetah(byte velocity) { // skapar en konstruktor med en byte som inparameter, sätter boolean predator till true och skickar dem till Animal
        super(true, velocity);
    }

    public void move(CollisionDetector cd) {

    }

    @Override
    public void eat() {
        // TODO: 2019-09-29 Kolla om geoparden lyckas döda bytet, och i så fall ta bort det ur listan
        super.eat();
    }

    public boolean collide(CollisionDetector cd) {
        return true;
    }
}
