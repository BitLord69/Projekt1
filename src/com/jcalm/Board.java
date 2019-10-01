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

    public static final int LEVEL_NORMAL = 0;
    public static final int LEVEL_BOLD = 1;
    public static final int LEVEL_STRESSED = 2;
    public static final int LEVEL_INFO = 3;

    public static final byte MAX_ZEBRA_VELOCITY = 3;
    public static final byte MAX_CHEETAH_VELOCITY = 6;
    public static final byte MAX_COMA_COUNTER = 3;
    public static final int CHEETAH_STARVATION_RATE = 2;
    public static final int CHEETAH_RANDOM_MOVE_RATIO = 10;
    public static final int ZEBRA_RANDOM_MOVE_RATIO = 30;

    public static final int DELAY_IN_MILLIS = 250;

    private int size;
    private ArrayList<Animal> animals;
    private int tickCounter;
    private byte initialZebraCount;
    private byte initialCheetahCount;
    private boolean scrambleList;

    public Board() {
        size = 20;
        tickCounter = 0;
        scrambleList = false;
        initialZebraCount = 0;
        initialCheetahCount = 0;
        animals = new ArrayList<Animal>();
    } // Board:Board

    public Board(int size, byte initialZebraCount, byte initialCheetahCount) {
        tickCounter = 0;
        this.size = size;
        scrambleList = false;
        this.initialZebraCount = initialZebraCount;
        this.initialCheetahCount = initialCheetahCount;
        animals = new ArrayList<Animal>();
    } // Board:Board

    public static String pimpString(String title, int level) {
        String prefix;

        switch (level) {
            case LEVEL_NORMAL:
                prefix = ANSI_GREEN;
                break;

            case LEVEL_BOLD:
                prefix = ANSI_RED;
                break;

            case LEVEL_STRESSED:
                prefix = ANSI_YELLOW;
                break;

            case LEVEL_INFO:
                prefix = ANSI_BLUE;
                break;

            default:
                prefix = "";
        } // switch

        return prefix + title + ANSI_RESET;
    } // pimpString

    public static String pimpString(int number, int level) {
        String str = new String("" + number);
        return pimpString(str, level);
    } // pimpString

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
            if (scrambleList) {
                Collections.shuffle(animals);
            } // if scrambleList...

            // Fördröj spelet lite så att det går att läsa utskrifterna
            try {
                TimeUnit.MILLISECONDS.sleep(DELAY_IN_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } // catch

            quit = getZebraCount() == 0 || getCheetahCount() == 0; // || tickCounter >= 10;
        }  // while (!quit)
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
        System.out.printf("%nTack för att du spelade! Simuleringen tog %s ticks.%n", Board.pimpString(tickCounter, Board.LEVEL_INFO));
        System.out.printf("%s zebror och %s geopard(er) överlevde spelet",
                Board.pimpString(getZebraCount() + "/" + initialZebraCount, Board.LEVEL_INFO),
                Board.pimpString(getCheetahCount() + "/" + initialCheetahCount, Board.LEVEL_INFO));
    } // printResult

    private void printBoard() {
        System.out.printf("%s%nCurrent tick count: %s, antalet zebror: %s, antal geparder: %s, kill count: %s%n",
                "-".repeat(60), Board.pimpString((tickCounter + 1), Board.LEVEL_INFO),
                Board.pimpString(getZebraCount() + "/" + initialZebraCount, Board.LEVEL_INFO),
                Board.pimpString(getCheetahCount() + "/" + initialCheetahCount, Board.LEVEL_INFO),
                Board.pimpString(getKillCount(), Board.LEVEL_BOLD));
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
        StringBuilder sb = new StringBuilder(String.format("Board: size = %d, initialZebraCount = %d, initialCheetahCount = %d, scrambleList = %b, animals [%d] = %n", size, initialZebraCount, initialCheetahCount, scrambleList, animals.size()));

        for (Animal a : animals) {
            sb.append(String.format("\t\t%s%n", a));
        }
        return sb.toString();
    } // toString
} // class Board