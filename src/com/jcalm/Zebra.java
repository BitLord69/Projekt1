package com.jcalm;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Zebra extends Animal {

    public Zebra() {
        super(false, Board.MAX_ZEBRA_VELOCITY);
    } // Zebra:Zebra

    public Zebra(byte velocity) {
        super(false, velocity);
    } // Zebra:Zebra

    @Override
    public void render(Graphics g) {
        int squareWidth = BoardFactory.getBoard().getMapSquareWidth();
        int squareHeight = BoardFactory.getBoard().getMapSquareHeight();

        int x = coord.getX() * squareWidth;
        int y = coord.getY() * squareHeight;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BufferedImage bufferedImage = new BufferedImage(squareWidth, squareHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg = bufferedImage.createGraphics();

        gImg.fillRect(0, 0, squareWidth, squareHeight);
        gImg.setColor(Color.BLACK);
        gImg.drawLine(0, squareHeight, squareWidth, 0); // /
        Rectangle2D rect = new Rectangle2D.Double(0, 0, squareWidth, squareHeight);

        g2d.setPaint(new TexturePaint(bufferedImage, rect));
        g2d.fill(new RoundRectangle2D.Double(x, y, squareWidth, squareHeight, 2, 2));
    } // render

    public void move() {
        if (getRandomPercentage() < Board.ZEBRA_RANDOM_MOVE_RATIO) { // om det slumpade talet är större än konstanten 30
            moveRandomly(); // gå åt ett slumpartat håll
            return;
        } // if getRandomPercentage

        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals(); // hämtar alla djur från spelbrädet
        Map<Animal, Double> distances = new HashMap<>(); // skapar en ny hashmap

        for (Animal a : animals) {
            if (a != this && a.isPredator() && !a.isDead()) { // om inte detta djuret, och om detta djuret är en predator, och om detta djuret inte är dött så...
                int deltaX = a.coord.getX() - coord.getX();
                int deltaY = a.coord.getY() - coord.getY();
                double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);  // räknar ut avståndet till det aktuella objektet
                distances.put(a, dist); // lägger in avståndet i hashmapen
            } // if a...
        } // for a...

        int moveX, moveY;
        int traceX = coord.getX();
        int traceY = coord.getY();

        Map<Animal, Double> sorted = distances // ber hashmapen att sortera alla avstånd
                .entrySet()
                .stream()
                .sorted(comparingByValue()) //
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        // Slut på geparder? Hoppa ut i så fall. Eftersom vi säter en flagga att ett djur dött, kan de ta slut under pågående körning
        if (distances.isEmpty())
            return;

        Animal closestCheetah = sorted.keySet().iterator().next(); // letar upp den närmsta geparden

        int deltaX = closestCheetah.coord.getX() - coord.getX();
        int deltaY = closestCheetah.coord.getY() - coord.getY();
        double cosV = deltaX / sorted.get(closestCheetah); // hämtar koordinaterna för den närmsta geparden

        moveX = (int) Math.round((velocity * cosV)) * -1; // -1 på slutet gör att zebran rör sig bort från geparden
        moveY = (int) Math.round(Math.tan(Math.acos(cosV)) * velocity * cosV * (deltaY < 0 ?  -1 : 1)) * -1; // samma som ovanstående kommentar
        coord.moveDelta(moveX , moveY); // zebran förflyttar sig

        System.out.printf("\t%s%n\tI %s, %s:%s : Avstånd till närmaste gepard: %s -> ΔX: %s, ΔY: %s = %s%n", "-".repeat(40),
                Board.pimpString("Zebra.move", Board.LEVEL_NORMAL),
                Board.pimpString(traceX, Board.LEVEL_INFO),
                Board.pimpString(traceY, Board.LEVEL_INFO),
                Board.pimpString(sorted.get(closestCheetah), Board.LEVEL_INFO),
                Board.pimpString(moveX, Board.LEVEL_INFO),
                Board.pimpString(moveY, Board.LEVEL_INFO),
                coord);
    } // move
} // class Zebra
