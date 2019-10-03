package com.jcalm;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Cheetah extends Animal {
    private byte foodComaCounter = 0;

    public Cheetah() { // default konstruktor
        super(true, Board.MAX_CHEETAH_VELOCITY);
    } // Cheetah:Cheetah

    public Cheetah(byte velocity) { // skapar en konstruktor med en byte som inparameter, sätter boolean predator till true och skickar dem till Animal
        super(true, velocity);
    } // Cheetah:Cheetah

    private void moveToClosest(Map<Animal, Double> distances) {
        // Sortera inläggen på värdet så att vi enkelt kan hämta ut djuret närmast/längst bort.
        Map<Animal, Double> sorted = distances
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        // Slut på bytesdjur? Hoppa ut i så fall. Eftersom vi säter en flagga att ett djur dött, kan de ta slut under pågående körning
        if (distances.isEmpty())
            return;

        Animal closestZebra = sorted.keySet().iterator().next();
        double distanceToClosest = sorted.get(closestZebra);

        // Hamnar geparden utanför strike zone? I så fall gå så långt som möjligt mot zebran
        if ( distanceToClosest>= velocity) {
            super.moveToClosest(closestZebra, distanceToClosest);
            starve();
      } // if sorted...
        else {
            System.out.printf("\tI %s, försöker äta zebra: %s: %f%n",
                    Board.pimpString("Cheetah.moveToClosest", Board.LEVEL_NORMAL),
                    closestZebra,
                    distanceToClosest);
            if (!hadSnack(closestZebra)) {
                starve();
            } // if !hadSnack...
        } // else
    } // moveToClosest

    @Override
    public Rectangle getBounds() {
        Coordinate coNy = new Coordinate(coord.getX() - velocity / 2, coord.getY() - velocity / 2);
        return new Rectangle(coNy.getX(), coNy.getY(), velocity, velocity);
    } // getBounds

    private boolean hadSnack(Animal a) {
        // Kollar ifall det är en kollision och om så är fallet, om geparden fick sig en liten mumsbit
        boolean didEat = false;

        // Är geparden inom räckhåll för att slå en zebra?
        if (collide(a)) {
            // Lyckas geparden slå bytet?
            if (eat(a)) {
                coord.setX(a.coord.getX());
                coord.setY(a.coord.getY());
                didEat = true;
                System.out.printf("\t\tI %s - åt %s%n", Board.pimpString("Cheetah.hadSnack", Board.LEVEL_BOLD), a);
            } // if eat...
            else
            // Djuren står nära varandra, men geparden slog inte bytet -> bytet panikhoppar iväg
            {
                System.out.printf("\t\tI %s - lyckades %s äta %s%n", Board.pimpString("Cheetah.hadSnack", Board.LEVEL_BOLD), Board.pimpString("inte", Board.LEVEL_STRESSED), a);
                a.moveRandomly();
            } // else
        } // if collide...

        return didEat;
    } // hadSnack

    public void move() {
        ArrayList<Animal> animals = BoardFactory.getBoard().getAnimals();
        Map<Animal, Double> distances = new HashMap<>();
        boolean hasEaten = false;
        boolean movedRandomly = false;

        // Om geparden redan är mätt, minska mättnaden och hoppa ut direkt
        if (foodComaCounter > 0) {
            foodComaCounter--;
            System.out.printf("\t%s%n\tI %s - denna predators foodComaCounter: %s%n", "-".repeat(40),
                    Board.pimpString("Cheetah.move", Board.LEVEL_NORMAL),
                    Board.pimpString(foodComaCounter, Board.LEVEL_INFO));
            return;
        } // if full...

        System.out.printf("\t%s%n\tI %s - detta djur: %s%n", "-".repeat(40), Board.pimpString("Cheetah.move", Board.LEVEL_NORMAL), this);

        // Kolla om geparden ska flytta sig slumpartat
        if (getRandomPercentage() < Board.CHEETAH_RANDOM_MOVE_RATIO) {
            moveRandomly();
            movedRandomly = true;
        } // if getRandomPercentage...

        for (Animal a : animals) {
            // Kolla bara alla andra djur, inte det egna. Dessutom, kontrollera bara zebror (ändra detta om geparder ska kunna para sig)
            // Lägg upp avstånden i en map så att vi sedan kan sortera den på värdena och hämta ut djuret som är närmast/längst bort
            if (a != this && !a.isPredator() && !a.isDead()) {
                // Börja med att kontrollera om geparden kan komma åt en zebra i nuvarande position. Kan bli så om en zerba har flyttats slumpartat
                if (hadSnack(a)) {
                    hasEaten = true;
                    break;
                } // if hadSnack...
                else if (!movedRandomly) { // Lägg upp avstånden i en hashmap ifall geparden inte redan har förflyttat sig slumpartat
                    distances.put(a, calculateDistance(a));
                } // else
            } // if a...
        } // for a...

        // Skippa resten om geparden redan ätit i steg ett, eller flyttat sig slumpartat
        if (!hasEaten && !movedRandomly) {
            moveToClosest(distances);
        } // if !hasEaten...
    } // move

    @Override
    public void render(Graphics g) {
        int squareWidth = BoardFactory.getBoard().getMapSquareWidth();
        int squareHeight = BoardFactory.getBoard().getMapSquareHeight();

        int x = coord.getX() * squareWidth;
        int y = coord.getY() * squareHeight;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawOval(x - velocity * squareWidth + squareWidth / 2, y - velocity * squareHeight + squareHeight / 2, velocity * squareWidth * 2, velocity * squareHeight * 2);

        BufferedImage bufferedImage = new BufferedImage(squareWidth, squareHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImg = bufferedImage.createGraphics();

        gImg.setColor(new Color(211, 211, 211, 50));
        gImg.fillRect(0, 0, squareWidth, squareHeight * 2);
        gImg.setColor(new Color(250, 250, 250, 75));
        gImg.drawLine(0, 0, squareWidth, squareHeight); // \
        gImg.drawLine(0, squareHeight, squareWidth, 0); // /
        Rectangle2D rect = new Rectangle2D.Double(0, 0, squareWidth, squareHeight);

        g2d.setPaint(new TexturePaint(bufferedImage, rect));
        g2d.fill(new Ellipse2D.Double(x - velocity * squareWidth + squareWidth / 2, y - velocity * squareHeight + squareHeight / 2, velocity * squareWidth * 2, velocity * squareHeight * 2));

        g2d.setColor(foodComaCounter > 0 ? Color.RED : Color.BLACK);
        g2d.fillRoundRect(x, y, squareWidth, squareHeight, 2, 2);

        // Slumpa ut gula prickar
        g2d.setColor(Color.YELLOW);
        for (int i = 0; i < 15; i++) {
            int x1 = (int)(Math.random() * squareWidth + 0.5);
            int y1 = (int)(Math.random() * squareHeight + 0.5);
            g2d.drawLine(x + x1, y + y1, x + x1, y + y1);
        } // for i...
    } // render

    private void starve() {
        full -= Board.CHEETAH_STARVATION_RATE;
        if (full < 0)
            setDead(true);
    } // starve

    @Override
    public boolean eat(Animal dinner) {
        // Lyckas geparden slå bytet?
        boolean killed = getRandomPercentage() < HIT_RATE;

        System.out.printf("\t\tI %s - %s vill äta %s, lyckades döda: %b%n", Board.pimpString("Cheetah.eat", Board.LEVEL_BOLD), this, dinner, killed);
        if (killed) {
            full = 100;
            foodComaCounter = Board.MAX_COMA_COUNTER;
            BoardFactory.getBoard().killAnimal(dinner);
        } // if killed...
        return killed;
    } // eat
} // class Cheetah