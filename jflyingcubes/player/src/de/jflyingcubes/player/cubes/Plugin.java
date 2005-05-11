/*
 * AnimatedPlugin.java
 *
 * Created on 23. April 2005, 00:46
 */

package de.jflyingcubes.player.cubes;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import de.jflyingcubes.player.cubes.Cubes;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author dm
 */
public abstract class Plugin implements ActionListener {
    
    private Cubes element;
    private Timer timer = null;
    private int ms = 0;

    private List<ActionListener> actions = new LinkedList<ActionListener>();
    /** Creates a new instance of AnimatedPlugin */
    public Plugin(Cubes element) {
        setElement(element);
    }
    
    public Plugin() {
    }
    
    public void setElement(Cubes element) {
        this.element = element;
    }
    
    public Cubes getElement() {
        return element;
    }
    
    public void start() {
        if (timer != null)
            timer.start();
    }
    
    public void restart() {
        start();
    }
    
    public void stop() {
        if (timer != null)
            timer.stop();
    }

    public void setDelay(int ms) {
        this.ms = ms;
        timer = new Timer(ms, this);
        timer.setInitialDelay(0);
    }

    
    public void actionPerformed(ActionEvent evt) {
        for (ActionListener listener : actions)
            listener.actionPerformed(evt);
        timerActionPerformed(evt);
    }
    
    public void paintBackground(Graphics g) {
        
    }
    
    public void paintBorder(Graphics g) {
        
    }
    
    public void addActionListener(ActionListener listener) {
        actions.add(listener);
    }
    
    public void removeActionListener(ActionListener listener) {
        actions.remove(listener);
    }
    
    public abstract void timerActionPerformed(ActionEvent evt);
    
    public abstract void setProperties(String name, String value);
}
