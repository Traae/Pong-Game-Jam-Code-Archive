package com.mygdx.game.Bodies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PongGame;


public class Ball extends PongBody {
    public final static float SPEED = 7;

    // This boolean flags whether the ball is moving or not
    private boolean inplay;

    public Ball(Texture spriteTexture, SpriteBatch b) {
        super(spriteTexture, b);
        xVector = 0;
        yVector = 0;
        resetPosition();
    }
    public void resetPosition(){
        // when the position is reset, mark it as not in play
        inplay = false;
        // set its position to the center of the screen
        sprite.setX(PongGame.SCREEN_WIDTH/2 - sprite.getWidth()/2);
        sprite.setY(PongGame.SCREEN_HEIGHT/2 - sprite.getHeight()/2);
        // and reset the yVector, the serve will be horizontal
        yVector = 0;
    }
    public void serveRandom(){
        // randomly decide it's direction
        double direction = Math.random();

        if (direction <= .5) {
            xVector = SPEED;
        }
        else if (direction > .5) {
            xVector = SPEED * -1;
        }
        // mark it as inplay.
        inplay = true;
    }
    public void serveRight(){
        xVector = SPEED;
        inplay = true;
    }
    public void serverLeft(){
        xVector = SPEED * -1;
        inplay = true;
    }

    public boolean isInplay() {
        return inplay;
    }


    public void setXCoord(float x){
        sprite.setX(x);
    }
    // this function does the same thing as the above function, but in reference to the sprites right side
    public void setWCoord(float w) { sprite.setX(w-sprite.getWidth());}

    public void setxVector(float xVector) { this.xVector = xVector; }
    public void setyVector(float yVector) { this.yVector = yVector; }

    public float getBallCenter(){
        return (sprite.getY() + sprite.getHeight()) /2;
    }

    @Override
    public void update() {
        // update the sprites position by incrementing it with the respective vectors
        sprite.setX(sprite.getX() + xVector);
        sprite.setY(sprite.getY() + yVector);
    }
}
