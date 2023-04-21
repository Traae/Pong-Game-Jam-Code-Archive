package com.mygdx.game.Bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.assetManagment.TextureBuilder;

public class b2dBody {
    public Vector2 size;
    public String name;

    protected Texture texture;
    protected Sprite sprite;
    protected World world;

    public Vector2 position = Vector2.Zero;

    public Body body;
    public Rectangle rect;

    public b2dBody(World _world, String _name, Vector2 _size, Color _color) {
        size = _size;
        name = _name;
        world = _world;


        texture = TextureBuilder.createTexture(1, 1, _color);
        sprite = new Sprite();
        sprite.setTexture(texture);
        sprite.setSize(size.x, size.y);
        sprite.setOriginCenter();
        position = Vector2.Zero;

        createBody();
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void createBody() {
        setBody(B2DBuilder.createPhysicsBox(world, size, 0, true, false, false, this));
    }

    public void setBody(Body b) {
        if (body != null) {
            world.destroyBody(body);
        }
        body = b;
    }

    public void updatePosition() {
        position = body.getPosition();
        sprite.setPosition(position.x - size.x / 2, position.y - size.y / 2);
    }

    public void setPosition(Vector2 pos) {
        body.setTransform(pos, 0);
        updatePosition();
    }
}
