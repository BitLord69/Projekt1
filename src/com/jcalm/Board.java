package com.jcalm;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Board extends Canvas {
    public static final int GUI_HEIGHT = 768;
    public static final int GUI_WIDTH = 1024;

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
    private int initialZebraCount;
    private int initialCheetahCount;
    private boolean scrambleList;
    private Window window;

    public Board() {
        size = 20;
        tickCounter = 0;
        scrambleList = false;
        initialZebraCount = 0;
        initialCheetahCount = 0;
        animals = new ArrayList<Animal>();
        window = new Window(GUI_WIDTH, GUI_HEIGHT, "Savannsimulator v.0.5", this);
    } // Board:Board

    public Board(int size, int initialZebraCount, int initialCheetahCount) {
        tickCounter = 0;
        this.size = size;
        scrambleList = false;
        this.initialZebraCount = initialZebraCount;
        this.initialCheetahCount = initialCheetahCount;
        animals = new ArrayList<Animal>();
        window = new Window(GUI_WIDTH, GUI_HEIGHT, "Savannsimulator v.0.5", this);
    } // Board:Board

    // Statisk metod som gör det lite enklare att skriva ut färgmeddelanden
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
        String str = "" + number;
        return pimpString(str, level);
    } // pimpString

    public static String pimpString(double number, int level) {
        return pimpString(String.format("%.5f", number), level);
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
    } // killAnimal

    public int getBoardSize() {
        return size;
    } // getSize

    // returns the width of each map square
    public int getMapSquareWidth() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) dim.getWidth() / size;
    } // getMapSquareSize

    // returns the height of each map square
    public int getMapSquareHeight() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) dim.getHeight() / size;
    } // getMapSquareHeight

    public ArrayList<Animal> getAnimals() {
        return animals;
    } // getAnimals

    public byte getKillCount() {
        return (byte) (initialZebraCount - getZebraCount());
    } // getKillCount

    public byte runSimulation() {
        boolean quit = false;
        BufferStrategy bs = null;

        while (!quit) {
            bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(3);
            } // if bs...
            else {
                Graphics g = bs.getDrawGraphics();
                printBoard(g);

                for (Animal a : animals) {
                    a.move();
                    a.render(g);
                } // for a...

                cleanupBoard();
                tickCounter++;
                window.update();

                quit = (getZebraCount() == 0 || getCheetahCount() == 0); // || tickCounter >= 10;

                // Vill man randomisera listan så att geparderna inte alltid är sist? I så fall, sätt variablen till true
                // TODO: 2019-09-29 Diskutera om vi vill randomisera listan (ibland)
                if (scrambleList) {
                    Collections.shuffle(animals);
                } // if scrambleList...

                if (quit) {
                    printBoard(g);
                    Graphics2D g2d = (Graphics2D) g;

                    String s = "G A M E    O V E R";
                    Font font = new Font("Serif", Font.PLAIN, 72);
                    FontMetrics metrics = g.getFontMetrics(font);
                    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                    g2d.setFont(font);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(Color.RED);
                    Rectangle2D r = metrics.getStringBounds(s, g2d);
                    int yText = (int) (dim.getHeight() - r.getHeight()) / 2;
                    g2d.drawString(s, (int) (dim.getWidth() - metrics.stringWidth(s)) / 2, yText);
                    s = (getCheetahCount() == 0) ? "Zebrorna vann!!!" : "Geparderna vann!";
                    r = metrics.getStringBounds(s, g2d);
                    g2d.drawString(s, (int) (dim.getWidth() - metrics.stringWidth(s)) / 2, yText + (int) r.getHeight());
                    bs.show();
                } // if quit...

                bs.show();
                g.dispose();

                // Fördröj spelet lite så att det går att läsa utskrifterna
                try {
                    TimeUnit.MILLISECONDS.sleep(DELAY_IN_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // catch
            } // else
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

    private void printBoard(Graphics g) {
        String message = String.format("%s%nCurrent tick count: %s, antalet zebror: %s, antal geparder: %s, kill count: %s%n",
                "-".repeat(80),
                Board.pimpString((tickCounter + 1), Board.LEVEL_INFO),
                Board.pimpString(getZebraCount() + "/" + initialZebraCount, Board.LEVEL_INFO),
                Board.pimpString(getCheetahCount() + "/" + initialCheetahCount, Board.LEVEL_INFO),
                Board.pimpString(getKillCount(), Board.LEVEL_BOLD));
        System.out.print(message);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 0, (int) dim.getWidth(), (int) dim.getHeight());

        Font font = new Font("Serif", Font.PLAIN, 20);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        message = String.format("Current tick count: %s, antalet zebror: %s, antal geparder: %s, kill count: %s%n",
                (tickCounter + 1),
                getZebraCount() + "/" + initialZebraCount,
                getCheetahCount() + "/" + initialCheetahCount,
                getKillCount());

        // Justera så att texten hamnar i mitten
        FontMetrics metrics = g.getFontMetrics(font);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = (int) (dim.getWidth() - metrics.stringWidth(message)) / 2;
        g2d.drawString(message, x, 20);
    } // printBoard

    private void printResult() {
        System.out.printf("%nTack för att du spelade! Simuleringen tog %s ticks.%n", Board.pimpString(tickCounter, Board.LEVEL_INFO));
        System.out.printf("%s zebror och %s geopard(er) överlevde spelet",
                Board.pimpString(getZebraCount() + "/" + initialZebraCount, Board.LEVEL_INFO),
                Board.pimpString(getCheetahCount() + "/" + initialCheetahCount, Board.LEVEL_INFO));
    } // printResult

    public byte getZebraCount() {
        byte zebras = 0;
        for (Animal a : animals) {
            if (a instanceof Zebra) {
                zebras++;
            } // if a...
        } // for a...

        return zebras;
    } // getZebraCount

    public byte getCheetahCount() {
        byte cheetahs = 0;
        for (Animal a : animals) {
            if (a instanceof Cheetah) {
                cheetahs++;
            } // if a...
        } // for a...

        return cheetahs;
    } // getZebraCount

    public int getTickCounter() {
        return tickCounter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Board: size = %d, initialZebraCount = %d, initialCheetahCount = %d, scrambleList = %b, animals [%d] = %n", size, initialZebraCount, initialCheetahCount, scrambleList, animals.size()));

        for (Animal a : animals) {
            sb.append(String.format("\t\t%s%n", a));
        }
        return sb.toString();
    } // toString
} // class Board