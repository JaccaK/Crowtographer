package com.mygdx.game;


import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.ArrayList;

/**
 * A search bar.
 */
public class SearchBar extends TextField implements ActorSubject {
    /**
     * A list of observers which to update.
     */
    private ArrayList<ActorObserver> myObservers = new ArrayList<>();

    /**
     * Create a search bar with preset text, and a skin.
     * @param text preset text.
     * @param skin The skin to use.
     */
    public SearchBar(final String text, final Skin skin) {
        super(text, skin);
    }

    /**
     * Add an actor to observe.
     * @param theObserver an actor.
     */
    @Override
    public void addObserver(final ActorObserver theObserver) {
        myObservers.add(theObserver);
    }

    /**
     * Remove an actor to observe.
     * @param theObserver an actor.
     */
    @Override
    public void removeObserver(final ActorObserver theObserver) {
        myObservers.remove(theObserver);
    }

    /**
     * Notifies all observers of an update.
     */
    @Override
    public void notifyObservers() {
        for (ActorObserver observer : myObservers) {
            observer.update();
        }
    }

    /**
     * Notifies the observers.
     * @param delta Delta Time.
     */
    @Override
    public void act(final float delta) {
        super.act(delta);
        //We're just going to notify every frame.
        notifyObservers();
    }
}
