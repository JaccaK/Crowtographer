package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;

/**
 * A cursor actor that updates based off of a given coordinate (A1 through m39).
 */
public class Cursor extends BaseActor implements ActorObserver {
    /**
     * Used to update position.
     */
    private UIData myData;

    /**
     * Creates a cursor, that when updated changes position.
     * @param theUIData the data used to change position.
     */
    public Cursor(final UIData theUIData) {
        myData = theUIData;
        setTexture(new Texture("cursor.png"));
    }

    /**
     * Moves the cursor to a specific coordinate.
     * @param theCoord the specific coordinate. (A1-m39)
     */
    private void moveToCoord(final String theCoord) {
        setX((Arrays.asList(CoordinateButton.COORD_ALPHABET).
                indexOf(theCoord.substring(0, 1)) + 1) * 16f);
        setY((Integer.parseInt(theCoord.substring(1)) - 1) * 16f);
    }

    /**
     * When observing, updates position based on the UIData's coordinate.
     */
    @Override
    public void update() {
        moveToCoord(myData.getCurrentCoordinate());
    }
}
