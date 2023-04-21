package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Ball {

    // instance variables
    private float xSpeed, ySpeed;     // Ball's speed on the X and Y axis respectively
    private Sprite sprite;            // The sprite
    private Sound serviceSound;       // the sound effects
    private Sound scoreSound;
    private Sound hitWall;
    private Sound hitPaddle;

    public Ball(){
        // create the sprite and set it's coordinates
        sprite = new Sprite(new Texture(Gdx.files.internal("ballPlain.png")));
        sprite.setX(400);
        sprite.setY(250);

        // create the sound effects
        serviceSound = Gdx.audio.newSound(Gdx.files.internal("serve.wav"));
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("score.wav"));
        hitWall = Gdx.audio.newSound(Gdx.files.internal("hitWall.wav"));
        hitPaddle = Gdx.audio.newSound(Gdx.files.internal("hitPaddle.wav"));
    }

    public void serveBall(){
        // give the ball speed
        xSpeed = 7;
        ySpeed = 7;
        // randomly decide it's direction
        if (Math.random() < .5) {
            xSpeed *= -1;
        }
        if (Math.random() < .5) {
            ySpeed *= -1;
        }
    }

    public void reset(){
        // reset the ball for the next serve
        xSpeed = 0;
        ySpeed = 0;
        sprite.setX(400);
        sprite.setY(250);
    }

    private boolean isHitPaddle(Paddle p){
        // We'll check collision via corners

        // first, see if Ball's X is in between the Paddles' x and width
        if ((sprite.getX() <= p.getX()+p.getWidth()) && (sprite.getX() >= p.getX())){
            // the see if weather the y or th height is in between the paddles y and height
            if ((sprite.getY() >= p.getY()) && (sprite.getY() <= p.getY()+p.getHeight())){
                return true;
            }
            else if ((sprite.getY()+sprite.getHeight() >= p.getY()) && (sprite.getY()+sprite.getHeight() <= p.getY()+p.getHeight())){
                return true;
            }
        }
        // else check the same way for the Ball's width
        else if ((sprite.getX()+sprite.getWidth() >= p.getX()) && ((sprite.getX()+sprite.getWidth() <= p.getX()+p.getWidth()))){
            if ((sprite.getY() >= p.getY()) && (sprite.getY() <= p.getY()+p.getHeight())){
                return true;
            }
            else if ((sprite.getY()+sprite.getHeight() >= p.getY()) && (sprite.getY()+sprite.getHeight() <= p.getY()+p.getHeight())){
                return true;
            }
        }
        return false;
    }


    public void update(Paddle p1, Paddle p2) {
        // update the coordinates by adding the speed
        sprite.setX(sprite.getX()+xSpeed);
        sprite.setY(sprite.getY()+ySpeed);

        // check for hit on either paddle
        if ((isHitPaddle(p1)) || ((isHitPaddle(p2)))) {
            xSpeed*= -1;            // flip its x direction
            hitPaddle.play();       // play the sound effect
        }
        // other wise check for contact with walls
        else if ((sprite.getY() < 0) || (sprite.getY() + sprite.getHeight() > 600)) {
            ySpeed *= -1;           // flip its y direction
            hitWall.play();         // play the sound effect
        }
        // other wise check for score
        else if ((sprite.getX() < 0) || (sprite.getX()+sprite.getWidth() > 1000)) {
            reset();                // reset the ball
            scoreSound.play();      // play the sound effect
        }
    }

    public void draw(SpriteBatch b) {
        // add our sprite to the sprite batch
        sprite.draw(b);
    }

    public void dispose() {
        // dispose of all our sound assets
        scoreSound.dispose();
        serviceSound.dispose();
        hitPaddle.dispose();
        hitWall.dispose();
    }
}
