package com.mygdx.game.Bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PongGame;

public class Paddle extends PongBody {
    final static int MAX_SPEED = 5;



    public Paddle(Texture spriteTexture, SpriteBatch b, float xCoord) {
        super(spriteTexture, b);
        xVector = 0;
        yVector = 0;
        sprite.setX(xCoord);
        sprite.setY(50);


    }
    public float getPaddleCenter(){
        return (sprite.getY() + sprite.getHeight()) / 2;
    }
    public void moveUp() {
        // if the paddle isn't at the edge of the screen, it can move
        if (sprite.getY()+sprite.getHeight() < PongGame.SCREEN_HEIGHT) {
            yVector = MAX_SPEED;
        }
        else {
            // if it is at the edge stop it
            yVector = 0;
        }
    }
    public void moveDown(){
        if (sprite.getY() > 0) {
            yVector = MAX_SPEED * -1;
        }
        else {
            yVector = 0;
        }
    }
    public void stayPut() {
        yVector = 0;
    }

    @Override
    public void update() {
        // the paddle only move vertical
        sprite.setY(sprite.getY() + yVector);
    }


}

