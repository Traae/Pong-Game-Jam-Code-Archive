package com.mygdx.game;

import com.mygdx.game.Bodies.Ball;
import com.mygdx.game.Bodies.Paddle;
import com.mygdx.game.screens.GameplayScreen;

public class PongPhysicsEngine {
    // VARIABLES
    // The parent game
    GameplayScreen game;
    // The paddles and ball
    private Paddle left, right;
    private Ball ball;

    // Constructor
    public PongPhysicsEngine(GameplayScreen theGame, Ball theBall, Paddle leftPaddle, Paddle rightPaddle){
        game = theGame;
        ball = theBall;
        left = leftPaddle;
        right = rightPaddle;
    }

    // Update function, should be called each render
    public void update(){
        // first, update all of our active bodies
        ball.update();
        left.update();
        right.update();

        // check for a ball bounce on a wall
        if (checkWalls()){
            // tell game to play the appropriate sound effect
            game.playHitWall();
        }
        // check for collision with a paddle
        if (collision()) {
            game.playHitPaddle();
        }
        // if not the paddles, then check the goals
        else if (checkGoal()) {
            game.playScore();
            // tell game to tally the score
            game.pointScored(ball.getXcoord());
        }

    }

    private boolean checkWalls() {
        if ((ball.getYcoord() <= 0) || (ball.getHcoord() >= PongGame.SCREEN_HEIGHT)) {
            // if it hits, call the bounce function
            wallBounce();
            return true;
        }
        return false;
    }

    public boolean checkGoal() {
        if ((ball.getXcoord() <= 0) || (ball.getWcoord() >= PongGame.SCREEN_WIDTH)) {
            return true;
        }
        return false;
    }

    private boolean collision(){
        // Check for overlap of the sprites by using the sprites rectangle and its overlap function
        if (left.getRectangle().overlaps(ball.getRectangle())){
            // teleport the ball so its not over lapping the paddle on the next frame
            ball.setXCoord(left.getWcoord()+1);
            // call the bounce function
            paddleBounce(left);
            return true;
        }
        // repeat with the other paddle
        else if (right.getRectangle().overlaps(ball.getRectangle())){
            ball.setWCoord(right.getXcoord()-1);
            paddleBounce(right);
            return true;
        }
        return false;
    }

    protected void paddleBounce(Paddle p){
        // The ball's angle is determine by the ratio of its x and y vectors,
        // being treated and the lengths of the sides of a right triangle
        // the hypotenuse, ie the ball's true vector, will always be equal to ball.SPEED

        // first, create our adjusted yVector
        // This version use the speed of the ball and paddle,
        // and the contact point to adjust.
        float yVector = (ball.getyVector() + p.getyVector())
                + (ball.getBallCenter() - p.getPaddleCenter())/100;

        // Then we ensure that the yVector isn't equal to the maximum speed
        // so that it cannot travel straight up and down, locking the game
        if (Math.abs(yVector) >= ball.SPEED) {
            if (yVector > 0) {
                yVector = ball.SPEED - 1;
            }
            else {
                yVector = (ball.SPEED - 1) * -1;
            }
        }

        // Now we create the new xVector by via the pythagorean theorem as x^2 = hypotenuse^2 - y^2;
        float xVector = (float) Math.sqrt((Ball.SPEED*Ball.SPEED) - (yVector*yVector));
        // Then since it hit a paddle, we ensure it travels in the opposite direction
        if (ball.getxVector() > 0) {
            xVector*=-1;
        }
        // set the vectors
        ball.setyVector(yVector);
        ball.setxVector(xVector);
    }

    protected void wallBounce() {
        // just reverse the y vector
        ball.setyVector(ball.getyVector()*-1);
    }

}
