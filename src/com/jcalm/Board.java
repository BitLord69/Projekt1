package com.jcalm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Board {
    public final static byte MAX_ZEBRA_VELOCITY = 3;
    public final static byte MAX_CHEETAH_VELOCITY = 6;

    private int size;
    private ArrayList<Animal> animals;
    private int tickCounter;
    private byte initialZebraCount;
    private byte initialCheetahCount;

    public Board() {
        size = 20;
        animals = new ArrayList<Animal>();
        tickCounter = 0;
        initialZebraCount = 0;
        initialCheetahCount = 0;
    } // Board:Board

    public Board(int size, byte initialZebraCount, byte initialCheetahCount) {
        this.size = size;
        this.initialZebraCount = initialZebraCount;
        this.initialCheetahCount = initialCheetahCount;
        animals = new ArrayList<Animal>();
        
        createAnimals();
    } // Board:Board

    private void createAnimals() {
        // TODO: 2019-09-29 Skriv kod för att skapa och lägga till djuren i listan
        for (int i = 0; i < initialZebraCount; i++){
            animals.add(new Zebra());
        } // for i...

        for (int i = 0; i < initialCheetahCount; i++){
            animals.add(new Cheetah());
        } // for i...
    } // createAnimals

    public void killAnimal(Animal a){
        // TODO: 2019-09-29 Ta bort ett djur ur listan
    } // killAnimal
    
    public int getSize() {
        return size;
    } // getSize

    public ArrayList<Animal> getAnimals() {
        return animals;
    } // getAnimals

    public byte getKillCount() {
        return (byte) (initialZebraCount - getZebraCount());
    } // getKillCount

    public byte runSimulation() {
        boolean quit = false;
        while (!quit) {
            for (Animal a : animals) {
                a.move();
            } // for a...

            tickCounter++;
            printBoard();

            // Fördröj spelet lite så att det går att läsa utskrifterna
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // catch

            quit = getZebraCount() == 0 || tickCounter > 10; // // TODO: 2019-09-29 Ta bort tickCounter-grenen när programmet är klart
        } // while !quit...
        printResult();

        return 0;
    } // runSimulation

    private void printResult() {
        System.out.println("Tack för att du spelade. Det tog " + tickCounter + " tics");
    }

    private void printBoard() {
        System.out.printf("Current tick count: %d%n", tickCounter);
        for (Animal a: BoardFactory.getBoard().getAnimals())
            System.out.printf("%s%n", a);
    } // printBoard

    private byte getZebraCount() {
        byte zebras = 0;
        for (Animal a : animals) {
            if (a instanceof Zebra) {
                zebras++;
            } // if a...
        } // for a...

        return zebras;
    } // getZebraCount
} // class Board