package com.assignment1.observer;

/**
 * Subject role in the Observer pattern.
 */
public interface Subject {

    /**
     * Adds an observer to the subject.
     * @param observer The observer to add.
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the subject.
     * @param observer The observer to remove.
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all observers of the subject.
     */
    void notifyObservers();

}

