package com.jcalm;

/*
Programmerat av Jan-Erik "Janis" Karlsson 2019-10-01
Programmering i Java EMMJUH19, EC-Utbildning
CopyLeft 2019 - JanInc
*/
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

public class Window extends JFrame implements ComponentListener {
    public static final int GUI_HEIGHT = 768;
    public static final int GUI_WIDTH = 1024;

    private int worldSize;
    private int zebras;
    private int cheetahs;

    private BufferStrategy bs;

    public Window(int worldSize, int zebras, int cheetahs){
//    public Window(int width, int height, String title, Board board){
        this.worldSize = worldSize;
        this.zebras = zebras;
        this.cheetahs = cheetahs;

        setTitle("Savannsimulator 1.2");
        setPreferredSize(new Dimension(GUI_WIDTH, GUI_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setIgnoreRepaint(true);
        setResizable(true);
        setAlwaysOnTop(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().addComponentListener(this);
    } // Window:Window

    public void addNotify() {
        super.addNotify();

        createBufferStrategy(2);
        bs = getBufferStrategy();

        setLayout(new BorderLayout());

        BoardFactory.createBoard(GUI_WIDTH, GUI_HEIGHT, worldSize, zebras, cheetahs, bs);
        BoardFactory.getBoard().createAnimals();
        add(BoardFactory.getBoard());
    } // addNotify

    public void componentHidden(ComponentEvent ce) {}
    public void componentShown(ComponentEvent ce) {}
    public void componentMoved(ComponentEvent ce) {}
    public void componentResized(ComponentEvent ce) {
        BoardFactory.getBoard().setWidth(this.getWidth());
        BoardFactory.getBoard().setHeight(this.getHeight());
    }

    public void updateTitle(){
        Board b = BoardFactory.getBoard();
        String s = String.format("Tick count: %4d, zebras: %d, cheetahs: %d, killed zebras: %d", b.getGameAge(), b.getZebraCount(), b.getCheetahCount(), b.getKillCount());
        setTitle(s);
    } // updateTitle
} // class Window
