package com.mygdx.game.text;

public class Printout {
    // This is a struct meant to be used with the Printer class
    // Simply holds a String and the coordinates of where to draw it.

    // variables
    public String text;
    public float xCoord, yCoord;


    public Printout(String text, float xCoord, float yCoord) {
        this.text = text;
        this.xCoord = xCoord;
        this.yCoord =  yCoord;
    }

}
