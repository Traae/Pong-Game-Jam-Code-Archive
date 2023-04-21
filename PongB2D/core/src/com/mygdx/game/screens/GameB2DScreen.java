package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Bodies.*;
import com.mygdx.game.Player;
import com.mygdx.game.PongB2D;
import com.mygdx.game.controllers.SimpleKeyboard;
import com.mygdx.game.physics.B2DContactListener;
import com.mygdx.game.text.Printer;
import com.mygdx.game.text.Printout;

import java.util.LinkedList;

public class GameB2DScreen implements Screen {
    // Constants
    final static int POINTS_TO_WIN = 5;
    public final Vector2 pSize = new Vector2(16, 9); // meters

    // Game State variables
    boolean isOver;

    // parent
    private PongB2D parent;

    // ENGINE VARIABLES
    private SimpleKeyboard controller;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private LinkedList<PongBody> toDrawList;
    private Box2DDebugRenderer b2dr;
    private World world;
    //Bodies
//    private Ball ball;
//    private Paddle leftPaddle, rightPaddle;
    private Player leftPlayer, rightPlayer;

    private pbPaddle lp;
    private pbPaddle rp;
    private pbBall bl;
    private b2dBody[] walls;

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





    public GameB2DScreen(PongB2D pongGame) {
        // The games hasn't even started
        isOver = false;

        // set parent
        parent = pongGame;
        // set up the camera
        camera = new OrthographicCamera();
        // Set camera to the center
        //camera.position.set(PongB2D.SCREEN_WIDTH / 2, PongB2D.SCREEN_HEIGHT / 2, 0);
        //camera.position.set(0, 0, 0);
        camera.viewportHeight = pSize.y;
        camera.viewportWidth = pSize.x;
        camera.position.set(0, 0, 0);
        camera.update();

        // setup box2d world
        world = new World(new Vector2(0, -9.8f), false);
        world.setContactListener(new B2DContactListener());
        b2dr = new Box2DDebugRenderer();


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
        rightPlayer = new Player("Player 2", PongB2D.SCREEN_WIDTH / 2 + 100);

        // Add our players score info to the printer
        printer.addPrintout(leftPlayer.getScoreText());
        printer.addPrintout(rightPlayer.getScoreText());

        // Create the our bodies for playing the game
//        ball = new Ball(ballTexture, batch);
//        leftPaddle = new Paddle(paddleTexture, batch, 50);
//        rightPaddle = new Paddle(paddleTexture, batch, PongB2D.SCREEN_WIDTH - 50 - paddleTexture.getWidth());
//
//        // Set up our list of things to draw and add all bodies to it
//        toDrawList = new LinkedList<>();
//        toDrawList.add(ball);
//        toDrawList.add(leftPaddle);
//        toDrawList.add(rightPaddle);

        // NEW STUFF
        Vector2 paddleSize = new Vector2(.3f, 2f);
        lp = new pbPaddle(world, "left paddle", paddleSize, Color.GOLDENROD);
        rp = new pbPaddle(world, "right paddle", paddleSize, Color.TEAL);

        lp.setBody(B2DBuilder.createPhysicsBox(world, lp.size, 0, false, false, false, lp));
        rp.setBody(B2DBuilder.createPhysicsBox(world, rp.size, 0, false, false, false, rp));

        float wallOffset = 1f; // m
        lp.setPosition(new Vector2((-pSize.x / 2) + wallOffset, 0));
        rp.setPosition(new Vector2((pSize.x / 2) - wallOffset, 0));

        // setup ball
        Vector2 ballSize = new Vector2(.2f, .2f);
        bl = new pbBall(world, "ball", ballSize, Color.WHITE);
        bl.setBody(B2DBuilder.createPhysicsBox(world, bl.size, 0, false, false, false, bl));
        bl.setPosition(Vector2.Zero);

        // make bouncy
        Fixture ballFixture  = bl.body.getFixtureList().get(0);
        ballFixture.setRestitution(1);

        // setup walls
        buildWalls();

        bl.randomServe();


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

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        lp.updatePosition();
        rp.updatePosition();
        bl.updatePosition();


        if (!bl.isInplay()){
           bl.randomServe();
           playServe();
        }

        processInputs();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        printer.draw();

        lp.draw(batch);
        rp.draw(batch);
        bl.draw(batch);

        for (b2dBody pb : walls){
            pb.draw(batch);
        }




//        for (PongBody p : toDrawList) {
//            p.draw();
//        }



        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        if (controller.escape) { parent.changeScreen(parent.PAUSE, parent.BOX2D); }

        // Ask the controller if the left paddle has been told to do anything
        if (controller.leftUp) { lp.moveUp(); }
        else if (controller.leftDown) { lp.moveDown(); }
        else { lp.stayPut(); }
        // same for the right
        if (controller.rightUp) { rp.moveUp(); }
        else if (controller.rightDown) { rp.moveDown(); }
        else { rp.stayPut(); }

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

//    public void pointScored(float x) {
//        // reset the ball
//        ball.resetPosition();
//        // if the ball scored on the left paddle
//        if (x <= 0) {
//            // give rightPlayer a point
//            rightPlayer.scoredPoint();
//            //Check for victory
//            if (rightPlayer.getScore() == POINTS_TO_WIN){
//                victory(rightPlayer);
//            }
//            // Serve the ball to the left
//            ball.serverLeft();
//            // play the sound
//            playServe();
//        }
//        // otherwise it scored on rightPlayer
//        else {
//            leftPlayer.scoredPoint();
//            if (leftPlayer.getScore() == POINTS_TO_WIN){
//                victory(leftPlayer);
//            }
//            ball.serveRight();
//            playServe();
//        }
//
//    }

//    private void victory(Player p){
//        // Create a new printout for the victory text
//        printer.addPrintout(new Printout(p.getPlayerName() + " Wins!",
//                PongB2D.SCREEN_WIDTH/2, PongB2D.SCREEN_HEIGHT/2));
//        // remove the ball from the drawing list
//        toDrawList.remove(ball);
//        // its over.
//        isOver = true;
//    }
    private b2dBody[] buildWalls() {
        walls = new b2dBody[4];
        Vector2 yBoundarySize = new Vector2(pSize.x, 1);
        Vector2 xBoundarySize = new Vector2(1, pSize.y);
        Vector2 ceilingPosition = new Vector2(0, pSize.y / 2);
        Vector2 floorPosition = new Vector2(0, -pSize.y / 2);
        Vector2 leftWallPosition = new Vector2(-pSize.x / 2, 0);
        Vector2 rightWallPosition = new Vector2(pSize.x / 2, 0);
        Color wallColor = Color.DARK_GRAY;

        b2dBody left = new b2dBody(world, "left wall", xBoundarySize, wallColor);
        left.setBody(B2DBuilder.createPhysicsBox(world, left.size, 0, false,
                true, false, left));
        left.setPosition(leftWallPosition);
        walls[0] = left;

        b2dBody right = new b2dBody(world, "right wall", xBoundarySize, wallColor);
        right.setBody(B2DBuilder.createPhysicsBox(world, right.size, 0, false,
                true, false, right));
        right.setPosition(rightWallPosition);
        walls[1] = right;

        b2dBody ceiling = new b2dBody(world, "ceiling", yBoundarySize, wallColor);
        ceiling.setBody(B2DBuilder.createPhysicsBox(world, ceiling.size, 0, false,
                true, false, ceiling));
        ceiling.setPosition(ceilingPosition);
        walls[2] = ceiling;

        b2dBody floor = new b2dBody(world, "floor", yBoundarySize, wallColor);
        floor.setBody(B2DBuilder.createPhysicsBox(world, floor.size, 0, false,
                true, false, floor));
        floor.setPosition(floorPosition);
        walls[3] = floor;

        return walls;
    }

}


