package com.jcalm;

public class Zebra extends Animal {

    public Zebra(){
        super(false, Board.MAX_ZEBRA_VELOCITY);
    } // Zebra:Zebra

    public Zebra(byte velocity){
        super(false, velocity);
    } // Zebra:Zebra

    public void move() {
        // TODO: 2019-09-29 Lägg in zebrans flytt. Börja med eventuell slumpvis flytt och om inte detta, unvdik geparder
        super.move();
    }
}
