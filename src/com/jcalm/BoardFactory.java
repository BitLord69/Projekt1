package com.jcalm;

/*
Programmerat av Jan-Erik "Janis" Karlsson 2019-09-27
Programmering i Java EMMJUH19, EC-Utbildning
CopyLeft 2019 - JanInc
*/

public class BoardFactory {
    private static Board board = null;

    private BoardFactory() {
        board = new Board();
    } // privat konstruktor

    public static void createBoard(int size, byte initialZebraCount, byte initialCheetahCount) {
        board = new Board(size, initialZebraCount, initialCheetahCount);
    }

    public Board getBoard() {
        if (board == null)
            board = new Board();

        return board;
    } // getBoard
}
