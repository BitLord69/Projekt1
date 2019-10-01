package com.jcalm;

/*
Programmerat av Jan-Erik "Janis" Karlsson 2019-10-01
Programmering i Java EMMJUH19, EC-Utbildning
CopyLeft 2019 - JanInc
*/

import javax.swing.*;
import java.awt.*;

public class Window {
    private JFrame frame;
    private Board board;

    public Window(int width, int height, String title, Board board){
        this.board = board;

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(board);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    } // Window:Window

    public void update(){
        String s = String.format("Tick count: %4d, zebras: %d, cheetahs: %d, killed zebras: %d", board.getTickCounter(), board.getZebraCount(), board.getCheetahCount(), board.getKillCount());
        frame.setTitle(s);
    } // update
} // class Window
