package com.jcalm;

/*
Grupparbete 1, Java19: Robotspel, 2019-09
Gruppmedlemmar: Janis, Max, Lukas, Calle, Avid

Denna klass är en Singleton, som returnerar ett bräde.
*/

import java.awt.image.BufferStrategy;

public class BoardFactory {
    private static Board board = null;

    private BoardFactory() {
        board = new Board();
    } // privat konstruktor

    public static void createBoard(int width, int height, int size, int initialZebraCount, int initialCheetahCount, BufferStrategy bs) {
        board = new Board(width, height, size, initialZebraCount, initialCheetahCount, bs);
    } // createBoard

    public static Board getBoard() {
        if (board == null)
            board = new Board();

        return board;
    } // getBoard
}
