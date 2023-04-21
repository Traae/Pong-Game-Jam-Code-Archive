package com.mygdx.game.controllers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class SimpleKeyboard implements InputProcessor {

	// These Keys represent the left paddle's up key and down key
	// The right paddle's keys, and the escape key
	public boolean leftUp, leftDown, rightUp, rightDown, escape;


	public SimpleKeyboard(){
		leftUp = false;
		leftDown = false;
		rightUp = false;
		rightDown = false;
		escape = false;
	}

	public void reset(){
		escape = false;
	}

	// As the input processor this function will be called when evey a key is pressed
	@Override
	public boolean keyDown(int keycode) {
		// this boolean will will return weather a key we wanted, was pressed
		boolean keyProcessed = false;
		switch (keycode)
        {
			case Keys.W:				// if the key pressed is W,
	            leftUp = true;			// set our Left paddle's up key to true
	            keyProcessed = true;	// set keyProcessed to true
	            break;
	        case Keys.S:				// repeat for the rest of our keys
	            leftDown = true;
	            keyProcessed = true;
	            break;
	        case Keys.UP:
	            rightUp = true;
	            keyProcessed = true;
	            break;
	        case Keys.DOWN:
	            rightDown = true;
	            keyProcessed = true;
				break;
			case Keys.ESCAPE:
				escape = true;
				keyProcessed = true;
				break;
        }
		return keyProcessed;	//  return our keyProcessed flag
	}
	// Repeat, but setting the key to false when is is released.
	@Override
	public boolean keyUp(int keycode) {
		boolean keyProcessed = false;
		switch (keycode) // switch code base on the variable keycode
        {
	        case Keys.W:
	            leftUp = false;
	            keyProcessed = true;
	            break;
	        case Keys.S:
	            leftDown = false;
	            keyProcessed = true;
	            break;
	        case Keys.UP:
	            rightUp = false;
	            keyProcessed = true;
	            break;
	        case Keys.DOWN:
	            rightDown = false;
	            keyProcessed = true;
				break;
        }
		return keyProcessed;
	}

	// This controller is only using the keyboard inputs
	// the rest of this input types will remain blank.
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


}
