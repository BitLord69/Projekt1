package com.jcalm;

public class Main {

    public static void main(String[] args) {
        BoardFactory.createBoard(100, (byte)10, (byte)2);
        Cheetah cheetah = new Cheetah();
    }
}
