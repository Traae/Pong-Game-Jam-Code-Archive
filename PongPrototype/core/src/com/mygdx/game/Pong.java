package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pong extends ApplicationAdapter {
	// VARIABLES

	// a sprite batch for drawing
	SpriteBatch batch;
	// then we need a ball and 2 paddles
	Ball ball;
	Paddle leftPaddle, rightPaddle;

	// This is similar to a constructor, it /creates/ all of our instances
	@Override
	public void create () {
		batch = new SpriteBatch();
		ball = new Ball();
		leftPaddle = new Paddle(50);
		rightPaddle = new Paddle(900);
	}

	// This is the actual frame by frame render of our game, and where most of our code will go
	@Override
	public void render () {

		// serve the ball
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) { ball.serveBall(); }

		// left paddle control
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) { rightPaddle.moveUp(); }
		else if (Gdx. input.isKeyPressed(Input.Keys.DOWN)) {rightPaddle.moveDown(); }

		// right paddle control
		if (Gdx.input.isKeyPressed(Input.Keys.W)) { leftPaddle.moveUp(); }
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) { leftPaddle.moveDown(); }

		// Run updates for our objects
		ball.update(leftPaddle, rightPaddle);
		leftPaddle.update();
		rightPaddle.update();

		// All the drawing

		// background color, using RGB 0-255 values.
		// currently set to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin the batch's drawing
		batch.begin();
		// Add all of the sprites to the batch
		ball.draw(batch);
		leftPaddle.draw(batch);
		rightPaddle.draw(batch);

		// Draw the batch
		batch.end();
	}
	
	@Override
	public void dispose () {
		ball.dispose();
		batch.dispose();
	}
}
