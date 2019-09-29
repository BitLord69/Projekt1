package com.jcalm;

public class Zebra extends Animal {

    public Zebra(){
        super(false, Board.MAX_ZEBRA_VELOCITY);
    } // Zebra:Zebra

    public Zebra(byte velocity){
        super(false, velocity);
    } // Zebra:Zebra

    public void move() {
        super.move();
    }
}
