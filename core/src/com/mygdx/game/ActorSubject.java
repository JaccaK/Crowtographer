package com.mygdx.game;

/**
 * Allows an actor to be a subject.
 */
public interface ActorSubject {
    void addObserver(ActorObserver theObserver);
    void removeObserver(ActorObserver theObserver);
    void notifyObservers();
}
