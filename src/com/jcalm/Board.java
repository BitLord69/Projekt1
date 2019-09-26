package com.jcalm;

import java.util.concurrent.TimeUnit;

public class Board {
    private int size;
    private List<Animal> animals;
    private int tickCounter;
    private byte initialZebraCount;
    private byte initialCheetahCount;

    public Board() {
        size = 20;
        animals = new List<Animal>();
        tickCounter = 0;
        initialZebraCount = 0;
        initialCheetahCount = 0;
    }

    public Board(int size, byte initialZebraCount, byte initialCheetahCount) {
        this.size = size;
        this.initialZebraCount = initialZebraCount;
        this.initialCheetahCount = initialCheetahCount;
        animals = new List<Animal>();
    }

    public byte getKillCount() {


        return (byte) (initialZebraCount - getZebraCount());
    }

    public byte runSimulation() {
        boolean quit = false;
        while (!quit) {
            for (Animal a : animals) {
                a.move();
            }

            printBoard();
            tickCounter++;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            quit = getZebraCount() == 0;
        }
        printResult();
    }

    private void printResult() {
        System.out.println("Tack för att du spelade. Det tog " + tickCounter + " tics");
    }

    private void printBoard() {

    }

    private byte getZebraCount() {
        byte zebras = 0;
        for (Animal a : animals) {
            if (a instanceof Zebra) {
                zebras++;
            }
        }
        return zebras;

    }
}