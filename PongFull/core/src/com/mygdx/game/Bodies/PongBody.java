package com.mygdx.game.Bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public abstract class PongBody {
    // instance variables
    protected float xVector, yVector;     // Ball's speed on the X and Y axis respectively
    protected Sprite sprite;            // The sprite
    private SpriteBatch batch;

    public PongBody(Texture spriteTexture, SpriteBatch b){
        sprite = new Sprite(spriteTexture);
        batch = b;
    }

    //  General use Getters & Setters
    public float getxVector() { return xVector; }
    public float getyVector() { return yVector; }
    public float getXcoord() { return sprite.getX(); }
    public float getYcoord() { return sprite.getY(); }
    public float getWcoord() { return sprite.getX() + (sprite.getWidth()); }
    public float getHcoord() { return sprite.getY() + (sprite.getWidth()); }
    public Rectangle getRectangle() { return sprite.getBoundingRectangle(); }

    public abstract void update();

    public void draw() {
    // add our sprite to the sprite batch
    sprite.draw(batch);
    }
}
