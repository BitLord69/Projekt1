package com.jcalm;

import java.awt.*;

public class Cheetah extends Animal {

    byte comaCounter = 0;

    public Cheetah() { // default konstruktor

    }

    public Cheetah(byte velocity) { // skapar en konstruktor med en byte som inparameter, sätter boolean predator till true och skickar de till Animal
        super(true, velocity);
    }


    public void move(CollisionDetector cd) {

    }

    public boolean collide(CollisionDetector cd) {
        return true;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
