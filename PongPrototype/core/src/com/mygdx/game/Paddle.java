package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle {
    // class constants
    private static final float speed = 15;

    //instance variables
    private Sprite sprite;

    public Paddle(float xPosition){
        sprite = new Sprite(new Texture(Gdx.files.internal("paddlePlain.png")));
        sprite.setY(150);
        sprite.setX(xPosition);
    }

    // getters and setters
    public float getX() { return sprite.getX(); }

    public float getY() {
        return sprite.getY();
    }

    public float getWidth() {
        return sprite.getWidth();
    }

    public float getHeight() { return sprite.getHeight(); }

    // other methods
    public void moveUp() {
        sprite.setY(sprite.getY() + speed);
    }

    public void moveDown(){
        sprite.setY(sprite.getY() - speed);
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public void update() {
        if (sprite.getY() < 0) {
            sprite.setY(0);
        }
        else if (sprite.getY() + sprite.getHeight() > 600){
            sprite.setY(600 - sprite.getHeight());
        }
    }

}
