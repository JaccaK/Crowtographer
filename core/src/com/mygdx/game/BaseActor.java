package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Basic actor with texture.
 */
public class BaseActor extends Actor {
    /**
     * The texture.
     */
    private TextureRegion myTextureRegion;

    public BaseActor() {
        super();
        myTextureRegion = new TextureRegion();
    }

    /**
     * Sets the actor's texture to a specific texture.
     * @param theTexture the specific texture.
     */
    public void setTexture(final Texture theTexture) {
        myTextureRegion.setRegion(theTexture);
        setSize(theTexture.getWidth(), theTexture.getHeight());
    }

    /**
     * Sets the actor's texture to a texture region.
     * @param theTexture the texture region.
     */
    public void setTexture(TextureRegion theTexture) {
        myTextureRegion = theTexture;
        setSize(theTexture.getRegionWidth(), theTexture.getRegionHeight());
    }

    @Override
    public void draw(final Batch theBatch, final float theParentAlpha) {
        super.draw(theBatch, theParentAlpha);

        Color color = getColor();

        theBatch.setColor(color.r, color.g, color.b, color.a);

        if (isVisible()) {
            theBatch.draw(myTextureRegion, getX(), getY(),
                    getOriginX(), getOriginY(), getWidth(),
                    getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
