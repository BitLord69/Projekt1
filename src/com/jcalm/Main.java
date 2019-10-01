package com.jcalm;

/*
Grupparbete 1, Java19: Robotspel, 2019-09
Gruppmedlemmar: Janis, Max, Lukas, Calle, Avid

Huvudprogrammet för simuleringen
*/

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int world = getWorldSize();
        int zebras = getZebras();
        BoardFactory.createBoard(world, zebras, getCheetahs(zebras));
        BoardFactory.getBoard().createAnimals();
        System.out.printf("I main, innan start: %n%s", BoardFactory.getBoard());
        BoardFactory.getBoard().runSimulation();
    }  // main

    public static int getWorldSize() {
        int worldSize = 0;
        Scanner s = new Scanner(System.in);
        System.out.println("Hej och välkommen till världens bästa spel");
        System.out.println("Hur stor vill du att spelplanen ska vara? ");
        worldSize = s.nextInt();
        return worldSize;
    } // getWorldSize

    public static int getZebras() {
        int numOfZebras = 0;
        Scanner s = new Scanner(System.in);
        System.out.println("Hur många zebror vill du ha? ");
        numOfZebras = s.nextInt();
        return numOfZebras;
    } // getZebras

    public static int  getCheetahs(int zebras) {
        int numOfCheetahs = 0;
        Scanner s = new Scanner(System.in);
        do {
            System.out.println("Hur många geoparder vill du ha? ");
            numOfCheetahs = s.nextInt();
            if (numOfCheetahs >= zebras) {
                System.out.println("Geoparder får inte vara fler eller ha samma antal som Zebras," +
                        " försök igen"
                );
            }

        } while (numOfCheetahs >= zebras);

        return numOfCheetahs;
    } // getCheetahs
} // class Main
