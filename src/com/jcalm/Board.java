package com.jcalm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Board {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final byte MAX_ZEBRA_VELOCITY = 3;
    public static final byte MAX_CHEETAH_VELOCITY = 6;
    public static final byte MAX_COMA_COUNTER = 3;
    public static final int CHEETAH_STARVATION_RATE = 2;

    public static final int DELAY_IN_MILLIS = 250;

    private int size;
    private ArrayList<Animal> animals;
    private int tickCounter;
    private byte initialZebraCount;
    private byte initialCheetahCount;
    private boolean scambleList;

    public Board() {
        size = 20;
        tickCounter = 0;
        scambleList = false;
        initialZebraCount = 0;
        initialCheetahCount = 0;
        animals = new ArrayList<Animal>();
    } // Board:Board

    public Board(int size, byte initialZebraCount, byte initialCheetahCount) {
        tickCounter = 0;
        this.size = size;
        scambleList = false;
        this.initialZebraCount = initialZebraCount;
        this.initialCheetahCount = initialCheetahCount;
        animals = new ArrayList<Animal>();
    } // Board:Board

    public void createAnimals() {
        for (int i = 0; i < initialZebraCount; i++) {
            animals.add(new Zebra());
        } // for i...

        for (int i = 0; i < initialCheetahCount; i++) {
            animals.add(new Cheetah());
        } // for i...
    } // createAnimals

    public void killAnimal(Animal a) {
        a.setDead(true);
//        animals.remove(a);
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
            printBoard();

            for (Animal a : animals) {
                a.move();
            } // for a...

            cleanupBoard();
            tickCounter++;

            // Vill man randomisera listan så att geparderna inte alltid är sist? I så fall, sätt variablen till true
            // TODO: 2019-09-29 Diskutera om vi vill randomisera listan (ibland) 
            if (scambleList) {
                Collections.shuffle(animals);
            } // if scrambleList...

            // Fördröj spelet lite så att det går att läsa utskrifterna
            try {
                TimeUnit.MILLISECONDS.sleep(DELAY_IN_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // catch

            quit = getZebraCount() == 0 || getCheetahCount() == 0; // || tickCounter >= 10;
        }  //while (!quit)
        printResult();

        return 0;
    } // runSimulation

    // Ta bort de döda djuren från spelplanen
    private void cleanupBoard() {
        ArrayList<Animal> dead = new ArrayList<Animal>();
        for (Animal a : animals)
            if (a.isDead())
                dead.add(a);

        for (Animal a : dead)
            animals.remove(a);
    } // cleanupBoard

    private void printResult() {
        System.out.println("\nTack för att du spelade. Simuleringen tog " + ANSI_BLUE + tickCounter + ANSI_RESET + " ticks.");
        System.out.printf("%s%d/%d%s zebror och %s%d/%d%s geopard(er) överlevde spelet",
                ANSI_BLUE, getZebraCount(), initialZebraCount, ANSI_RESET,
                ANSI_BLUE, getCheetahCount(), initialCheetahCount, ANSI_RESET);
    } // printResult

    private void printBoard() {
        System.out.printf("%s%nCurrent tick count: %s%d%s, antalet zebror: %s%d/%d%s, antal geparder: %s%d/%d%s, kill count: %s%d%s%n",
                "-".repeat(60), ANSI_BLUE, (tickCounter + 1), ANSI_RESET,
                ANSI_BLUE, getZebraCount(), initialZebraCount, ANSI_RESET,
                ANSI_BLUE, getCheetahCount(), initialCheetahCount, ANSI_RESET,
                ANSI_RED, getKillCount(), ANSI_RESET);
//        for (Animal a : animals)
//            System.out.printf("%s%n", a);
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

    private byte getCheetahCount() {
        byte cheetahs = 0;
        for (Animal a : animals) {
            if (a instanceof Cheetah) {
                cheetahs++;
            } // if a...
        } // for a...

        return cheetahs;
    } // getZebraCount

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Board: size = %d, initialZebraCount = %d, initialCheetahCount = %d, scrambleList = %b, animals [%d] = %n", size, initialZebraCount, initialCheetahCount, scambleList, animals.size()));

        for (Animal a : animals) {
            sb.append(String.format("\t\t%s%n", a));
        }
        return sb.toString();
    } // toString
} // class Board