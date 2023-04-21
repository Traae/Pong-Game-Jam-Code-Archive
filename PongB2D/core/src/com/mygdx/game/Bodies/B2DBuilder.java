package com.mygdx.game.Bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class B2DBuilder {
    public static Body createPhysicsBox(World world, Vector2 size, float gravScale, boolean canRotate,
                                        boolean isStatic, boolean isSensor, Object data){
        // ref https://www.youtube.com/watch?v=IIZ7XI6L7IA&t=389s

        // physics properties
        BodyDef bd = new BodyDef();
        bd.fixedRotation = !canRotate;
        bd.linearDamping = 0;
        if (isStatic)
            bd.type = BodyDef.BodyType.StaticBody;
        else
            bd.type = BodyDef.BodyType.DynamicBody;

        bd.gravityScale = gravScale;

        // collision bounding area
        PolygonShape box = new PolygonShape();
        box.setAsBox(size.x / 2, size.y / 2);

        // collision properties
        FixtureDef fd = new FixtureDef();
        fd.shape = box;
        fd.isSensor = isSensor;
        fd.density = 1;
        fd.friction = 0;
        // fd.filter.categoryBits = cBits; // what this is
        // fd.filter.maskBits = mBits; // what this collides with

        Body b = world.createBody(bd);
        Fixture f = b.createFixture(fd);
        f.setUserData(data);

        return b;
    }
}
