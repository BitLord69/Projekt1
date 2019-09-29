package com.jcalm;

/*
Grupparbete 1, Java19: Robotspel, 2019-09
Gruppmedlemmar: Janis, Max, Lukas, Calle, Avid

Huvudprogrammet för simuleringen
*/

public class Main {

    public static void main(String[] args) {
        BoardFactory.createBoard(100, (byte)2, (byte)1);
        BoardFactory.getBoard().createAnimals();
        BoardFactory.getBoard().runSimulation();
    }  // main
} // class Main
