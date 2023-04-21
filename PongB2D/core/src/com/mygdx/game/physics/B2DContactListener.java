package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.physics.CollisionHandler;

public class B2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) a.getUserData()).beginContact(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) b.getUserData()).beginContact(a.getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) a.getUserData()).endContact(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) b.getUserData()).endContact(a.getUserData());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) a.getUserData()).preSolve(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) b.getUserData()).preSolve(a.getUserData());
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null)
            return;

        if (a.getUserData() != null && a.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) a.getUserData()).postSolve(b.getUserData());
        }
        if (b.getUserData() != null && b.getUserData() instanceof CollisionHandler){
            ((CollisionHandler) b.getUserData()).postSolve(a.getUserData());
        }
    }
}
