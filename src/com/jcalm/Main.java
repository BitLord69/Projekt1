package com.jcalm;

/*
Grupparbete 1, Java19: Robotspel, 2019-09
Gruppmedlemmar: Janis, Max, Lukas, Calle, Avid

Huvudprogrammet för simuleringen
*/

public class Main {

    public static void main(String[] args) {
        // TODO: 2019-09-29 Fråga användaren om de tre parametrarna till createBoard
        BoardFactory.createBoard(500, (byte) 10, (byte)2 );
        BoardFactory.getBoard().createAnimals();
        System.out.printf("I main, innan start: %n%s", BoardFactory.getBoard());
        BoardFactory.getBoard().runSimulation();
    }  // main
} // class Main

