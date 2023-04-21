package com.mygdx.game.text;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.LinkedList;

public class Printer {
    //Variables
    // the list the will print when called
    private LinkedList<Printout> toPrint;
    // the font we'll write in
    private BitmapFont font;
    // the batch we'll draw to
    private SpriteBatch batch;

    //Constructor
    public Printer (BitmapFont f, SpriteBatch b) {
        font = f;
        batch = b;
        toPrint = new LinkedList<Printout>();

        // Set the scale to 1, does nothing, but its there for reference
        font.getData().setScale(3);
        // Tell the font to scale the text linearly in both the x and y dimensions
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    // Creates and new printout and adds it to the list
    public void addText(String text, float xCoord, float yCoord){
        Printout p = new Printout(text, xCoord, yCoord);
        toPrint.add(p);
    }

    public void addPrintout(Printout p){
        toPrint.add(p);
    }

    public void removePrintout(Printout p) {
        toPrint.remove(p);
    }

    // removes any and all printouts with the given text
    public void removeText(String text) {
        for (Printout p: toPrint) {
            if (p.text == text) {
                toPrint.remove(p);
            }
        }
    }

    public void draw(){
        for (Printout p : toPrint) {
            font.draw(batch, p.text, p.xCoord, p.yCoord);
        }
    }
}
