package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Bodies.Ball;
import com.mygdx.game.Bodies.Paddle;
import com.mygdx.game.Bodies.PongBody;
import com.mygdx.game.Player;
import com.mygdx.game.PongPhysicsEngine;
import com.mygdx.game.PongGame;
import com.mygdx.game.controllers.SimpleKeyboard;
import com.mygdx.game.text.Printer;
import com.mygdx.game.text.Printout;

import java.util.LinkedList;

public class GameplayScreen implements Screen {
    // Constants
    final static int POINTS_TO_WIN = 5;

    // Game State variables
    boolean isOver;

    // parent
    private PongGame parent;

    // ENGINE VARIABLES
    private PongPhysicsEngine physics;
    private SimpleKeyboard controller;
    private OrthographicCamera camera;
    private StretchViewport viewport;
    private SpriteBatch batch;
    private LinkedList<PongBody> toDrawList;
    //Bodies
    private Ball ball;
    private Paddle leftPaddle, rightPaddle;
    private Player leftPlayer, rightPlayer;

    // preferences
    private float soundFxVol, musicVol;

    // ASSETS
    //music
    private Music bgMusic;
    // sound effects
    private Sound hitPaddle;
    private Sound hitWall;
    private Sound serve;
    private Sound score;
    // images
    private Texture paddleTexture;
    private Texture ballTexture;
    // font
    private BitmapFont font;
    private Printer printer;





    public GameplayScreen(PongGame pongGame) {
        // The games hasn't even started
        isOver = false;

        // set parent
        parent = pongGame;
        // set up the camera
        camera = new OrthographicCamera();
        // Stretch veiwport will maintain our size ratio regarless of how the window has changes
        viewport = new StretchViewport(PongGame.SCREEN_WIDTH, PongGame.SCREEN_HEIGHT, camera);
        viewport.apply();
        // Set camera to the center
        camera.position.set(PongGame.SCREEN_WIDTH / 2, PongGame.SCREEN_HEIGHT / 2, 0);
        camera.update();

        //Create our batch and controller
        batch = new SpriteBatch();
        controller = new SimpleKeyboard();

        // load the assets
        parent.assets.loadMusic();
        parent.assets.loadSounds();
        parent.assets.loadImages();
        parent.assets.loadFonts();
        parent.assets.manager.finishLoading();
        // assign the assets to their variables
        bgMusic = parent.assets.manager.get(parent.assets.bgMusic);
        hitPaddle = parent.assets.manager.get(parent.assets.hitPaddle);
        hitWall = parent.assets.manager.get(parent.assets.hitWall);
        serve = parent.assets.manager.get(parent.assets.serve);
        score = parent.assets.manager.get(parent.assets.score);
        paddleTexture = parent.assets.manager.get(parent.assets.paddle);
        ballTexture = parent.assets.manager.get(parent.assets.ball);
        font = parent.assets.manager.get(parent.assets.text);

        // Create the text printer and give it the font
        printer = new Printer(font, batch);

        // Create the players, give them a name and the xCoordinate it will be displayed at.
        leftPlayer = new Player("Player 1", 100);
        rightPlayer = new Player("Player 2", PongGame.SCREEN_WIDTH / 2 + 100);

        // Add our players score info to the printer
        printer.addPrintout(leftPlayer.getScoreText());
        printer.addPrintout(rightPlayer.getScoreText());

        // Create the our bodies for playing the game
        ball = new Ball(ballTexture, batch);
        leftPaddle = new Paddle(paddleTexture, batch, 50);
        rightPaddle = new Paddle(paddleTexture, batch, PongGame.SCREEN_WIDTH - 50 - paddleTexture.getWidth());

        // Set up our list of things to draw and add all bodies to it
        toDrawList = new LinkedList<>();
        toDrawList.add(ball);
        toDrawList.add(leftPaddle);
        toDrawList.add(rightPaddle);

        // Dont forget our physics engine
        physics = new PongPhysicsEngine(this, ball, leftPaddle, rightPaddle);

    }

    //Called when the screen is switch to this
    @Override
    public void show() {
        // makes sure the controller is ready for new inputs
        controller.reset();
        // tell the game to take input data via our controller
        Gdx.input.setInputProcessor(controller);
        //  update the preferences
        parent.updateActivePreferences();
        // set the volume to our new preference
        bgMusic.setVolume(musicVol);
        bgMusic.play();
    }


    @Override
    public void render(float delta) {
        // set the background to black, the clear the screen of everything that's been drawn
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // if the ball ain't moving serve it
        if (!ball.isInplay()){
           ball.serveRandom();
           playServe();
        }
        // Hey keyboard, did the player want to do anything?
        processInputs();
        // if the game isn't over, check the update the physics
        if (!isOver){ physics.update(); }

        // DRAWING
        // tell the batch to do camera magic
        batch.setProjectionMatrix(camera.combined);
        // Begin the process of rendering
        batch.begin();
        // Tell our printer to print
        printer.draw();
        // Cycle through the list the draw everything
        for (PongBody p : toDrawList) {
            p.draw();
        }
        // End, and actually draw everything to the screen
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    // called when the screen switches
    @Override
    public void hide() {
        bgMusic.pause();
    }

    @Override
    public void dispose() {
        bgMusic.stop();
        // unload our assets
        parent.assets.unloadImages();
        parent.assets.unloadMusic();
        parent.assets.unloadSounds();
        parent.assets.unloadFonts();
        // dispose of our batch
        batch.dispose();
    }

    private void processInputs() {
        // Pause menu on Escape
        if (controller.escape) {
            parent.changeScreen(parent.PAUSE, parent.GAME_PLAY);
        }

        // Ask the controller if the left paddle has been told to do anything
        if (controller.leftUp) { leftPaddle.moveUp(); }
        else if (controller.leftDown) { leftPaddle.moveDown(); }
        else { leftPaddle.stayPut(); }
        // same for the right
        if (controller.rightUp) { rightPaddle.moveUp(); }
        else if (controller.rightDown) { rightPaddle.moveDown(); }
        else { rightPaddle.stayPut(); }

    }

    public void updatePreferences(float musicVol, boolean musicEnabled, float soundVol, boolean soundEnabled) {
        // if the music or sound is enables, set the volume,
        // otherwise, set it to 0.
        if (musicEnabled) { this.musicVol = musicVol; }
        else { this.musicVol = 0; }
        if (soundEnabled) { this.soundFxVol = soundVol; }
        else { soundFxVol = 0; }
    }

    // PLAY THE SOUND EFFECTS with the current volume
    public void playHitWall() {
        hitWall.play(soundFxVol);
    }
    public void playHitPaddle() {
        hitPaddle.play(soundFxVol);
    }
    public void playServe() {
        serve.play(soundFxVol);
    }
    public void playScore() {
        score.play(soundFxVol);
    }

    public void pointScored(float x) {
        // reset the ball
        ball.resetPosition();
        // if the ball scored on the left paddle
        if (x <= 0) {
            // give rightPlayer a point
            rightPlayer.scoredPoint();
            //Check for victory
            if (rightPlayer.getScore() == POINTS_TO_WIN){
                victory(rightPlayer);
            }
            // Serve the ball to the left
            ball.serverLeft();
            // play the sound
            playServe();
        }
        // otherwise it scored on rightPlayer
        else {
            leftPlayer.scoredPoint();
            if (leftPlayer.getScore() == POINTS_TO_WIN){
                victory(leftPlayer);
            }
            ball.serveRight();
            playServe();
        }

    }

    private void victory(Player p){
        // Create a new printout for the victory text
        printer.addPrintout(new Printout(p.getPlayerName() + " Wins!",
                PongGame.SCREEN_WIDTH/2, PongGame.SCREEN_HEIGHT/2));
        // remove the ball from the drawing list
        toDrawList.remove(ball);
        // its over.
        isOver = true;
    }


}


