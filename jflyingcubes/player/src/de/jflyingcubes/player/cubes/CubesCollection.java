/*
 * ElementCollection.java
 *
 * Created on 22. April 2005, 22:52
 */

package de.jflyingcubes.player.cubes;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.NullPointerException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dm
 */
public class CubesCollection implements PropertyChangeListener {

    public static String PROPERTY_MOVE_ELEMENT = "MOVE_ELEMENT";
    
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    private List<Cubes> elements = new LinkedList<Cubes>();
    
    public CubesCollection() {
    }
    
    public void addElement(int index, Cubes el) {
        if (el == null)
            throw new NullPointerException("Element could not null!");
        el.addPropertyChangeListener(this);
        elements.add(index, el);
        
    }

    public void addElement(Cubes el) {
        addElement(elements.size(), el);
    }
    
    public Cubes getElement(int index) {
        return elements.get(index);
    }
    
    public void moveElementToPos(Cubes e, int newIndex) {
        if (!elements.contains(e))
            return;
        int oldIndex = elements.indexOf(e);
        if (oldIndex == newIndex)
            return;
        elements.set(newIndex, e);
        firePropertyChange(PROPERTY_MOVE_ELEMENT, oldIndex, newIndex);
    }
    
    public List<Cubes> getElements() {
        return elements;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public void firePropertyChange(String propName, int oldValue, int newValue) {
        support.firePropertyChange(propName, oldValue, newValue); 
    }

    public void propertyChange(PropertyChangeEvent evt) {
        support.firePropertyChange(evt);
    }
    
}
