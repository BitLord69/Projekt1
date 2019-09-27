package com.jcalm;

/*
Grupparbete 1, Java19: Robotspel, 2019-09
Gruppmedlemmar: Janis, Max, Lukas, Calle, Avid

Denna klass är en Singleton, som returnerar ett bräde.
*/

public class BoardFactory {
    private static Board board = null;

    private BoardFactory() {
        board = new Board();
    } // privat konstruktor

    public static void createBoard(int size, byte initialZebraCount, byte initialCheetahCount) {
        board = new Board(size, initialZebraCount, initialCheetahCount);
    }

    public static Board getBoard() {
        if (board == null)
            board = new Board();

        return board;
    } // getBoard
}
