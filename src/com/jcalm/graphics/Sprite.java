package com.jcalm.graphics;

import com.jcalm.Coordinate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Sprite {

    private BufferedImage SPRITESHEET;
    private BufferedImage[][] spriteArray;
    private int TILE_SIZE = 32;
    private int w;
    private int h;
    private int wSprite;
    private int hSprite;



    public Sprite(String file){
        w = TILE_SIZE;
        h = TILE_SIZE;

        System.out.println("Loading image: " + file);
        SPRITESHEET = loadSprite(file);
    }

    public Sprite(String file, int w, int h){
        this.w = w;
        this.h = h;

        System.out.println("Loading image: " + file);
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / w;
        hSprite = SPRITESHEET.getHeight() / h ;
        loadSpriteArray();


    }

    public BufferedImage loadSprite(String file){

        try {
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));

        }catch (Exception e){
            System.out.println("Error reading: " + file );
        }

        return null;
    }


    public void loadSpriteArray(){
        spriteArray = new BufferedImage[wSprite][hSprite];
        for(int i=0;i<wSprite;i++){
            for(int j=0;j<hSprite;j++){
                spriteArray[i][j] = SPRITESHEET.getSubimage(i*w, j*h, w,h);
            }
        }
    }

    public BufferedImage[] getSpriteArray(int i){
        return spriteArray[i];
    }

    public static void drawArray(Graphics2D g2d, ArrayList<BufferedImage> img, int w , int h, int offsetX, int offsetY, Coordinate coord){

        for (int i=0;i<img.size();i++){
            g2d.drawImage(img.get(i),coord.getX(),coord.getY(),w,h,null);
        }
    }

    public int getSpriteSize() {
        return TILE_SIZE;
    }

    public void setSpriteSize(int spriteSize) {
        this.TILE_SIZE = spriteSize;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

}
