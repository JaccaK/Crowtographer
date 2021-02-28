package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

/**
 * A text area that constantly stores data to a UIData object.
 */
public class NotesArea extends TextArea implements ActorObserver {
    /**
     * The UI data to use for updating.
     */
    private UIData myData;

    /**
     * Create an area for notes.
     * @param text Default text.
     * @param skin Skin to use.
     */
    public NotesArea(final String text, final Skin skin) {
        super(text, skin);
    }

    /**
     * Set's the UI data used by this NotesArea.
     * @param theData
     */
    public void setUIData(final UIData theData) {
        myData = theData;
    }

    /**
     * When observing, saves the current data to the UIData.
     */
    @Override
    public void update() {
        myData.getCurrentCoordinates().
                put(myData.getCurrentCoordinate(), getText());
    }
}
